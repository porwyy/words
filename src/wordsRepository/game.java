package wordsRepository;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.imageio.plugins.jpeg.JPEGHuffmanTable;
import javax.swing.*;

import org.omg.CORBA.PUBLIC_MEMBER;

import service.connect;


public abstract class game extends JFrame implements ItemListener{
	
	//一个多选框，选择要背的单词的范围，日期为单位
	//一行展示英文
	//下面四个方框展示单词的选项

	JComboBox choosDate;
	static String user;
	static int num;
	static JButton opt1, opt2, opt3, opt4, theWord;
	static JButton next, last;
	static JPanel jp1, jp2, jp3;
	static JLabel showInfo;
	//展示可选的日期
	public game(String user){
		super();
		this.user = user;
		setTitle("我的单词库");
        setBounds(100,100,600,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        
	}

	static void getJp1(game e, int num){
		showDate(e);
		e.choosDate.addItemListener(e);
		if(jp1 != null){
			e.remove(jp1);
		}
		String sh = "还剩余单词" + num + "个";
		showInfo = new JLabel(sh);
		jp1 = new JPanel();
		jp1.add(showInfo);
		jp1.add(e.choosDate);
		
		e.add(jp1,BorderLayout.NORTH);
	}
	
	
	static void getJp2(game e, String[] s, final String answer, final String word){
		if(jp2 != null){
			e.remove(jp2);
		}
		
		opt1 = new JButton(s[1]);
		opt2 = new JButton(s[2]);
		opt3 = new JButton(s[3]);
		opt4 = new JButton(s[0]);
		theWord = new JButton(word);
		
		//监听选项的点击事件，并更新数据库remember数据
		ActionListener listenAnswer = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 进行逻辑处理即可
				String ch = e.getActionCommand();
				String sqlUp1 = "update `words` set remember=remember+1 where word='" + word + "'";
				String sqlUp2 = "update `words` set remember=remember-1 where word='" + word + "'";
				String sqlSel = "SELECT remember FROM `words` where word='" + word + "'";
				
				try{
		        	Connection conn= connect.getConnection();
		            Statement stmt = null;
		            stmt = conn.createStatement();
		            ResultSet rs = stmt.executeQuery(sqlSel);
		            int rem = 0;
		            if(rs.next()){
			        	System.out.println(word);
			        	rem = Integer.parseInt(rs.getString(1));
				        if(ch.equals(answer)){
				        	JOptionPane.showMessageDialog(null, "正确！");
				        	if(rem > 0) stmt.executeUpdate(sqlUp2);
						}else{
							JOptionPane.showMessageDialog(null, "错误！");
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
		};
		
		opt1.addActionListener(listenAnswer);
		opt2.addActionListener(listenAnswer);
		opt3.addActionListener(listenAnswer);
		opt4.addActionListener(listenAnswer);
		
		Dimension preferredSize = new Dimension(300,50);//设置尺寸
		theWord.setPreferredSize(preferredSize );
		opt1.setPreferredSize(preferredSize );
		opt2.setPreferredSize(preferredSize );
		opt3.setPreferredSize(preferredSize );
		opt4.setPreferredSize(preferredSize );
		jp2 = new JPanel();
		
		jp2.add(theWord);
		jp2.add(opt1);
		jp2.add(opt2);
		jp2.add(opt3);
		jp2.add(opt4);
		e.add(jp2,BorderLayout.CENTER);
	}
	static void getJp3(game e,ActionListener listen1, ActionListener listen2 ){
		next = new JButton("下一个");
		last = new JButton("上一个");
		
		next.addActionListener(listen2);
		last.addActionListener(listen1);
		jp3 = new JPanel();
		jp3.add(last);
		jp3.add(next);
		e.add(jp3,BorderLayout.SOUTH);
	}
	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		
              
	}
	//更新现在显示的单词， 以及该日期下剩余的单词个数
	public abstract void changeWord();
	
	//更新四个选项
	public abstract void upChoose();
	
	//构造日期选择复选框
	static void showDate(game e){
		e.choosDate = new JComboBox();
		e.choosDate.addItem("前一天");
		e.choosDate.addItem("三天内");
		e.choosDate.addItem("一周内");
		e.choosDate.addItem("一个月内");
		e.choosDate.setSize(50, 30);
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}



}
