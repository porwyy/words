package wordsRepository;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Date;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import service.connect;
import project.*;
public class gameCE extends JFrame implements ItemListener{
	public static String[][] words;
	static JButton opt1, opt2, opt3, opt4, theWord;
	static JButton next, last;
	static int num, now;
	static String user,style,showInfoString;
	static words myWords;
	static String[] opt;
	static JPanel jp1,jp2,jp3;
	static JComboBox choosDate;
	
	public gameCE(String user, String style) {
		super();
		this.style = style;
		getJp1(this);
		if(style.equals("CE")){
			this.setTitle("中英选择");
		}else{
			this.setTitle("英中选择");
		}
		
        this.setBounds(100,100,600,400);
        this.setVisible(true);
	}
	//顶部单词剩余提示和下拉框
	static void getJp1(gameCE e){
		if(jp1 != null){
			e.remove(jp1);
		}
		jp1 = new JPanel();
		showDate(e);
		gameCE.choosDate.addItemListener(e);
		showInfoString = "还剩余单词 " + now + " 个";
		JLabel showInfo = new JLabel(showInfoString);
		jp1.add(showInfo); //剩余单词提示信息
		jp1.add(e.choosDate); //日期区间选择下拉框
		e.add(jp1,BorderLayout.NORTH);
	}
	
	//构建下拉框
	static void showDate(gameCE e){
		e.choosDate = new JComboBox();
		e.choosDate.addItem("前一天");
		e.choosDate.addItem("三天内");
		e.choosDate.addItem("一周内");
		e.choosDate.addItem("一个月内");
		e.choosDate.setSize(50, 30);
	}
	
	//中间单词选择模块
	static void getJp2(gameCE e){
		if(jp2 != null){
			e.remove(jp2);
		}
		opt1 = new JButton(opt[1]);
		opt2 = new JButton(opt[2]);
		opt3 = new JButton(opt[3]);
		opt4 = new JButton(opt[4]);
		theWord = new JButton(opt[0]);
		
		//监听选项的点击事件，并更新数据库remember数据
		ActionListener listenAnswer = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String[] re = myWords.getWord(style);
				String ch = e.getActionCommand();
				System.out.println(re[0]);
				System.out.println(ch);
					if(ch.equals(re[0])){
						JOptionPane.showMessageDialog(null, "正确！");
						myWords.upWord(1);
					}else {
						JOptionPane.showMessageDialog(null, "错误！");
						myWords.upWord(0);
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
	
	//上下单词切换
	static void getJp3(gameCE e){
		next = new JButton("下一个");
		last = new JButton("上一个");
		next.addActionListener(nextWord(e));
		last.addActionListener(lastWord(e));
		jp3 = new JPanel();
		jp3.add(last);
		jp3.add(next);
		e.add(jp3,BorderLayout.SOUTH);
	}
		
	//监听复习区间选择
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		if(e.getStateChange() == ItemEvent.SELECTED) {
			String choosDay = choosDate.getSelectedItem().toString();
			myWords = new words(user, choosDay);
			myWords.nextWord(style);
			upChoose(this);
        }
		
	}
	
	//监听下一个单词按钮
	public static ActionListener nextWord(final gameCE e){
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent em) {
				if(myWords.restWords() == 0){
					JOptionPane.showMessageDialog(null, "已经是最后一个单词！");
				}else{
					opt = myWords.nextWord(style);
					upChoose(e);
				}
			}
		};
	}
	
	//监听上一个单词按钮
	public static  ActionListener lastWord(final gameCE e){
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent em) {
				if(myWords.restWords() == num){
					JOptionPane.showMessageDialog(null, "已经是第一个单词！");
				}else{
					opt = myWords.lastWord(style);
					upChoose(e);
				}
			}
		};
	}
	
	//更新四个选项
	public static void upChoose(gameCE e) {
		// TODO Auto-generated method stub
		opt = myWords.gameWord(style);
		now = myWords.restWords();
		getJp1(e);
		getJp2(e);
		getJp3(e);
		e.setVisible(false);
		e.setVisible(true);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		gameCE aCe = new gameCE("wyy","CE");
	}

}
