package wordsRepository;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import service.connect;

public class showWords extends JFrame {

	static Object[][] val = null;
	static String sql;
	
	public showWords(String name, String way){
		super();
		
		
		
		String[] columnNames = {"单词", "中文意思", "添加时间", "是否掌握"};   
		getSql(way);
		getWords();
        JTable table = new JTable(val,columnNames);
        
        JScrollPane scrollpanel = new JScrollPane(table);
        getContentPane().add(scrollpanel, BorderLayout.CENTER);
        
		this.setTitle("我的单词库");
		this.setBounds(200,100,600,400);
		this.setLayout(new GridLayout(1,1));
		this.setVisible(true);
	}
	
	public static void getSql(String way){
		if(way == "按时间升序") {
            sql = "SELECT * FROM `words` where word != '' GROUP BY addDate asc, word asc;";
        }else if(way == "按时间降序"){
        	sql = "SELECT * FROM `words` where word != '' GROUP BY addDate desc, word desc;";
        }else if(way == "按字母升序"){
        	sql = "SELECT * FROM `words` where word != '' GROUP BY word asc;";
        }else if(way == "按字母降序"){
        	sql = "SELECT * FROM `words` where word != '' GROUP BY word desc;";
        }
	}
	
	public static void getWords(){
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
           val = new String[params.length][4];
           
           for (int i = 0; i < params.length; i++) {
        	   val[i] = params[i].split("\\*"); 
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
	
	public static void main(String[] args){
//		showWords a = new showWords("22","按时间升序");
	
	}
}
