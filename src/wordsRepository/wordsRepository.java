package wordsRepository;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.*;

import project.login;
import project.nav;
import service.connect;

public class wordsRepository extends nav implements ItemListener{
	JPanel jp1,jp2;
	JScrollPane jp3;
	JComboBox ways;
	JButton subWord;
	static String user;
	static JTextField wordInput, meaningInput; 
	String[][] param;
	
	//提交单词
	public static void addLinstenerSub (JButton b){
		b.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String word = wordInput.getText();
				String meaning = meaningInput.getText();
				
				
				//获取当天日期dataTime
                String dateTime = "";
                SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
                java.util.Date dd  = Calendar.getInstance().getTime();
                dateTime = sdf.format(dd);
				
                
                try{
                	Connection conn= connect.getConnection();
                	String sql = "insert into words values(?,?,?,?,?)";
                    PreparedStatement pst = conn.prepareStatement(sql);
            		pst.setString(1, word);
            		pst.setString(2, meaning);
            		pst.setString(3, dateTime);
            		pst.setInt(4, 0);
            		pst.setString(5, user);
            		pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "添加成功");
                    wordInput.setText("");
                    meaningInput.setText("");
                    
                    conn.close();
                }catch(SQLException se){
                    se.printStackTrace();
                    JOptionPane.showMessageDialog(null, "该单词已存在");
                    
                }catch(Exception e1){
                    e1.printStackTrace();
                }
        	}
		});
	}
	
	
    public static void main(String[] args) {
        wordsRepository frame = new wordsRepository("wyy");
        frame.setVisible(true);

    }
    
    
    
    public wordsRepository(String name) {
        super(name);
        this.user = name;
        System.out.println(this.user);
        wordInput = new JTextField(8);
        meaningInput = new JTextField(25);
        subWord = new JButton("提交");
        addLinstenerSub(subWord);
        
     
        ways = new JComboBox();
        ways.addItem("随机排序");
        ways.addItem("按时间升序");
        ways.addItem("按时间降序");
        ways.addItem("按字母升序");
        ways.addItem("按字母降序");
        ways.addItemListener(this);
        
        jp1 = new JPanel();   
        jp2 = new JPanel();
        jp1.add(wordInput);
        jp1.add(meaningInput);
        jp1.add(subWord);
        jp1.add(ways);
        this.add(jp1);
        this.add(jp2);
        setTitle("我的单词库");
        setBounds(100,100,600,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridLayout(3,1));
        this.setPreferredSize(new Dimension(400,400));

    }


	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		this.remove(jp2);
		if(e.getStateChange() == ItemEvent.SELECTED) {
			
			String sql="";
			StringBuffer tableValues = new StringBuffer();
            if(e.getSource() == "") {
            	sql = "SELECT * FROM `words` where word != '' GROUP BY addDate asc, word asc;";
            } else if(e.getSource() == "按时间升序") {
                sql = "SELECT * FROM `words` where word != '' GROUP BY addDate asc, word asc;";
            }else if(e.getSource() == "按时间降序"){
            	sql = "SELECT * FROM `words` where word != '' GROUP BY addDate desc, word desc;";
            }else if(e.getSource() == "按字母升序"){
            	sql = "SELECT * FROM `words` where word != '' GROUP BY word asc;";
            }else if(e.getSource() == "按字母降序"){
            	sql = "SELECT * FROM `words` where word != '' GROUP BY word desc;";
            }
            try{
            	Connection conn= connect.getConnection();
	            Statement stmt = null;
                stmt = conn.createStatement();
                sql = "SELECT * FROM `words` where word != '' GROUP BY word desc;";
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
               String datas;
                // 将总数据以指定字符分割成数组，每条数据为数组的一项
               System.out.println(str);
               String[] params = str.split("%");
               //将每条数据再拆分，则param数据保存的是一条数据的每一项数据
               param = new String[params.length][4];
               for (int i = 0; i < params.length; i++) {
            	   param[i] = params[i].split("\\*");            	   
               }
               String[] columnNames = {"单词", "中文意思", "添加时间", "是否掌握"};   
               
               JTable table = new JTable(param,columnNames);
               JScrollPane scrollPane = new JScrollPane(table);
               scrollPane.setSize(40, 30);
               jp2.add(scrollPane);
               this.add(jp2);
               //重新显示内容
               this.setVisible(false);
               this.setVisible(true);
               conn.close();
            }catch(SQLException se){
            	// 处理 JDBC 错误
                se.printStackTrace();
            }catch(Exception e1){
            	// 处理 Class.forName 错误
                e1.printStackTrace();
            }
            
        }
              
	}
     
}