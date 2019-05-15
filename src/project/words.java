package project;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JOptionPane;

import service.connect;

public class words {
	static String[][] myWords;
	static int rest, num;
	static String sql,user;
	
	public words(String user){
		this.user = user;
	}
	public words(String user,String way){
		this.user = user;
		if(way == "learn" || way == "前一天" || way == "三天内" || way == "一周内" || way == "一个月内"){
			getMyWords(way);
		}else{
			getMyWordsRepository();
		}
	}
	//获得mysql查询语句（单词库页面，记单词页面，单词游戏页面）
	public static void getSql(String way){
		if(way == "learn"){
			sql = "SELECT word, meaning FROM `words` where remember!=0";
		}else if(way == "按时间升序") {
            sql = "SELECT * FROM `words` where word != '' GROUP BY addDate asc, word asc;";
        }else if(way == "按时间降序"){
        	sql = "SELECT * FROM `words` where word != '' GROUP BY addDate desc, word desc;";
        }else if(way == "按字母升序"){
        	sql = "SELECT * FROM `words` where word != '' GROUP BY word asc;";
        }else if(way == "按字母降序"){
        	sql = "SELECT * FROM `words` where word != '' GROUP BY word desc;";
        }else{
			String dateTime1 = "", dateTime2 = "";
	        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
	        Calendar date = Calendar.getInstance();
	        date.setTime(new Date());
	        dateTime1 = sdf.format(date.getTime()); //当天日期
			switch (way) {
			case "前一天":
				date.set(Calendar.DATE, date.get(Calendar.DATE) - 1);
				break;
			case "三天内":
				date.set(Calendar.DATE, date.get(Calendar.DATE) - 3);		
				break;
			case "一周内":
				date.set(Calendar.DATE, date.get(Calendar.DATE) - 7);
				break;
			case "一个月内":
				date.set(Calendar.DATE, date.get(Calendar.DATE) - 30);
				break;
			default:
				break;
			}
			dateTime2 = sdf.format(date.getTime());
			sql = "SELECT word, meaning FROM `words` where remember!=0 AND addDate between '" + dateTime2 + "' and '" + dateTime1+ "'";
		}
	}
	
	//获取记单词页面，单词游戏页面的所有要展示的单词
	public static void getMyWords(String way) {
		getSql(way);
		StringBuffer tableValues = new StringBuffer();
		try{
        	Connection conn= connect.getConnection();
            Statement stmt = null;
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
        	while(rs.next()){
            	tableValues.append(rs.getString(1));
            	tableValues.append("*");
            	tableValues.append(rs.getString(2));
            	tableValues.append("*");
            	tableValues.append("%"); // 在每条数据后面做标记，便于拆分
            }
           String str = tableValues.toString(); // 将数据由StringBuffer类型转化成String类型
            // 将总数据以指定字符分割成数组，每条数据为数组的一项
           String[] params = str.split("%");
           //将每条数据再拆分，则param数据保存的是一条数据的每一项数据
           myWords = new String[params.length][2];
           num = params.length;
           rest = num;
           for (int i = 0; i < params.length; i++) {
        	   myWords[i] = params[i].split("\\*"); 
           }
           conn.close();
        }catch(SQLException se){
        	// 处理 JDBC 错误
            se.printStackTrace();
        }catch(Exception e1){
        	// 处理 Class.forName 错误
            e1.printStackTrace();
        }
	}
	
	//获取单词库页面所有要展示的单词
	public static void getMyWordsRepository() {
		StringBuffer tableValues = new StringBuffer();
        try{
        	Connection conn= connect.getConnection();
            Statement stmt = null;
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
            	tableValues.append(rs.getString(1));
            	tableValues.append("*");
            	tableValues.append(rs.getString(2));
            	tableValues.append("*");
            	tableValues.append(rs.getDate(3));
            	tableValues.append("*");
            	tableValues.append(rs.getInt(4));
            	tableValues.append("*");
            	tableValues.append("%"); // 在每条数据后面做标记，便于拆分
            }
           String str = tableValues.toString(); // 将数据由StringBuffer类型转化成String类型
            // 将总数据以指定字符分割成数组，每条数据为数组的一项
           String[] params = str.split("%");
           //将每条数据再拆分，则param数据保存的是一条数据的每一项数据
           myWords = new String[params.length][4];
           
           for (int i = 0; i < params.length; i++) {
        	   myWords[i] = params[i].split("\\*"); 
           }
           conn.close();
        }catch(SQLException se){
        	// 处理 JDBC 错误
            se.printStackTrace();
        }catch(Exception e1){
        	// 处理 Class.forName 错误
            e1.printStackTrace();
        }
	}
	
	//更新当前单词的remember属性
	public void upWord(int up){
		String sqlUp1 = "update `words` set remember=remember+1 where word='" + myWords[rest][0] + "'";
		String sqlUp2 = "update `words` set remember=remember-1 where word='" + myWords[rest][0] + "'";
		String sqlSel = "SELECT remember FROM `words` where word='" + myWords[rest][0] + "'";
		try{
        	Connection conn= connect.getConnection();
            Statement stmt = null;
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlSel);
            int rem = 0;
            if(rs.next()){
	        	rem = Integer.parseInt(rs.getString(1));
		        if(up == 1){//remember-1
		        	if(rem > 0) stmt.executeUpdate(sqlUp2);
				}else{
					stmt.executeUpdate(sqlUp1);
				}
	        }
            
            conn.close();
        }catch(SQLException se){
        	// 处理 JDBC 错误
            se.printStackTrace();
        }catch(Exception e1){
        	// 处理 Class.forName 错误
            e1.printStackTrace();
        }
	}
	
	//获取游戏情况下一个单词的返回数组
	public static String[] gameWord(String way){
		String[] re = new String[5];
		if(way == "EC"){
			re[0] = myWords[rest][0];
			for(int i = 0; i < 4; i++){
				if(rest-i >= 0){
					re[i+1] = myWords[rest-i][1];
				}else {
					re[i+1] = myWords[(rest + num -i)][1];
				}
			}
		}else {
			re[0] = myWords[rest][1];
			for(int i = 0; i < 4; i++){
				if(rest-i >= 0){
					re[i+1] = myWords[rest-i][0];
				}else {
					re[i+1] = myWords[(rest + num -i)][0];
				}
			}
		}
		return re;
	}
	
	//返回剩下的单词
	public int restWords(){
		return rest;
	}
	
	//获得当前rest对应的单词
	public String[] getWord(String style){
		if(rest == 0){
			JOptionPane.showMessageDialog(null, "当前没有单词哦！");
		}else{
			String opt[];
			opt = new String[2];
			if(style.equals("CE")){
				opt[0] = new String(myWords[rest][0]);
				opt[1] = new String(myWords[rest][1]);
			}else {
				opt[0] = new String(myWords[rest][1]);
				opt[1] = new String(myWords[rest][0]);
			}
			
			
			return opt;
		}
		return null;
	}
	//获得下一个单词（游戏界面和背单词界面）
	public String[] nextWord(String way){
		if(rest == 0){
			return null;
		}else{
			rest--;
			String opt[];
			if(way == "learn"){
				opt = new String[2];
				opt[0] = new String(myWords[rest][0]);
				opt[1] = new String(myWords[rest][1]);
			}else{
				opt = gameWord(way);
			}
			return opt;
		}
		
	}
	
	//获得上一个单词
	public String[] lastWord(String way){
		if(rest+1 == num){
			return null;
		}else{
			rest++;
			String[] opt = null;
			if(way == "learn"){
				opt = new String[2];
				opt[0] = new String(myWords[rest][0]);
				opt[1] = new String(myWords[rest][1]);	
			}else{
				opt = gameWord(way);
			}
			return opt;
		}
		
	}
	
	//添加一个单词
	public boolean addWord(String wordInput, String meaningInput){
		//获取当天日期dataTime
        String dateTime = "";
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
        java.util.Date dd  = Calendar.getInstance().getTime();
        dateTime = sdf.format(dd);
        try{
        	Connection conn= connect.getConnection();
        	String sql = "insert into words values(?,?,?,?,?)";
            PreparedStatement pst = conn.prepareStatement(sql);
    		pst.setString(1, wordInput);
    		pst.setString(2, meaningInput);
    		pst.setString(3, dateTime);
    		pst.setInt(4, 1);
    		pst.setString(5, user);
    		pst.executeUpdate();
            conn.close();
            return true;
        }catch(SQLException se){
            se.printStackTrace();
            return false;
        }catch(Exception e1){
            e1.printStackTrace();
            return false;
        }
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
