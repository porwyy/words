package wordsRepository;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.util.Date;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import service.connect;

public class gameCE extends game{
	public static String[][] words;
	static JButton opt1, opt2, opt3, opt4;
	static int num, now = 0;
	static String user, sql;
	public gameCE(String user) {
		super(user);
		// TODO Auto-generated constructor stub
		
		getJp1(this, num);
		setTitle("我的单词库");
        setBounds(100,100,600,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        this.setLayout(new FlowLayout());
        this.setVisible(true);
	}
	
	//获得四个选项
	public String[] getChoose() {
		// TODO Auto-generated method stub
		String[] re = new String[4];
		for(int i = 0; i < 4; i++){
			if(now-i-1 >= 0){
				System.out.println(now-i-1);
				re[i] = words[now-i-1][1];
			}else if(now == 0){
				re[i] = words[now][1];
			}else if(now == 1){
				re[i] = words[now-1][1];
			}else{
				System.out.println(now + num -i-1);
				re[i] = words[(now + num -i-1)][1];
			}
			
		}
		return re;
	}

	
	//按日期查询要复习的单词
	@Override
	public void changeWord() {
		// TODO Auto-generated method stub
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
           words = new String[params.length][2];
           num = params.length;
           now = num;
           for (int i = 0; i < params.length; i++) {
        	   words[i] = params[i].split("\\*"); 
           }
           conn.close();
           upChoose();
        }catch(SQLException se){
        	// 处理 JDBC 错误
            se.printStackTrace();
        }catch(Exception e1){
        	// 处理 Class.forName 错误
            e1.printStackTrace();
        }
		
	}
	@Override
	
	//监听复习区间选择
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		if(e.getStateChange() == ItemEvent.SELECTED) {
			String choosDay = choosDate.getSelectedItem().toString();
			String dateTime1 = "", dateTime2 = "";
            SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
            Calendar date = Calendar.getInstance();
            date.setTime(new Date());
            dateTime1 = sdf.format(date.getTime());
            if(choosDay == "前一天"){
            	date.set(Calendar.DATE, date.get(Calendar.DATE) - 1);
            	
            }else if(choosDay == "三天内"){
            	date.set(Calendar.DATE, date.get(Calendar.DATE) - 3);
            }else if(choosDay == "一周内"){
            	date.set(Calendar.DATE, date.get(Calendar.DATE) - 7);
            }else if(choosDay == "一个月内"){
            	date.set(Calendar.DATE, date.get(Calendar.DATE) - 30);
            }
            dateTime2 = sdf.format(date.getTime());
            sql = "SELECT word, meaning FROM `words` where remember!=0 AND addDate between '" + dateTime2 + "' and '" + dateTime1+ "'";
            System.out.println(sql);
            changeWord();
        }
              
	}
	
	public ActionListener nextWord(){
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent em) {
				// 进行逻辑处理即可
				if(now == 0){
					JOptionPane.showMessageDialog(null, "已经是最后一个单词！");
				}else{
					now--;
					System.out.print("1111下一个" + now);
					upChoose();
				}
			}
		};
	}
	public  ActionListener lastWord(){
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent em) {
				// 进行逻辑处理即可
				if(now == num){
					JOptionPane.showMessageDialog(null, "已经是第一个单词！");
				}else{
					now++;
					upChoose();
					System.out.print("2222上一个" + now);
				}
			}
		};
	}
	
	//更新四个选项
	@Override
	public void upChoose() {
		// TODO Auto-generated method stub
		String[] opt = getChoose();
		ActionListener  listen1 = lastWord();
		ActionListener  listen2 = nextWord();
		getJp1(this,now);
		getJp2(this,opt,words[now-1][1],words[now-1][0]);
		getJp3(this, listen1, listen2);
		this.setVisible(false);
		this.setVisible(true);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		gameCE aCe = new gameCE("wyy");
	}

}
