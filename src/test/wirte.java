package test;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import project.words;

public class wirte extends JFrame implements ItemListener{
	static JPanel jp1,jp2;
	static JLabel word;
	static JButton subWord;
	static JTextField  inputWord;
	static words myWords;
	static JComboBox choosDate;
	static String user;
	
	public wirte(String user){
		super();
		this.user = user;
		setJp1(this);
		this.setTitle("单词默写");
        setBounds(100,100,600,400);
        setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        this.setVisible(true);
	}
	
	//构建单词日期选择下拉框
	static void showDate(wirte e){
		choosDate = new JComboBox();
		choosDate.addItem("前一天");
		choosDate.addItem("三天内");
		choosDate.addItem("一周内");
		choosDate.addItem("一个月内");
		choosDate.setSize(50, 30);
		choosDate.addItemListener(e);
	}
	//监听日期区间选择
	public void setJp1(wirte e){
		jp1 = new JPanel();
		showDate(e);
		jp1.add(choosDate);
		e.add(jp1,BorderLayout.NORTH);
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		if(e.getStateChange() == ItemEvent.SELECTED) {
			String choosDay = choosDate.getSelectedItem().toString();
			myWords = new words(user, choosDay);
        }
	}

}
