package wordsRepository;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.*;
import project.login;
import project.nav;
import project.words;
import service.connect;

public class wordsRepository extends nav implements ItemListener{
	static JPanel jp1;
	static JComboBox ways;
	static JButton subWord;
	static String user;
	static JTextField wordInput, meaningInput; 
	
	public wordsRepository(String user) {
        super(user);
        this.user = user;
        getWayBox();
        ways.addItemListener(this);
        getJp1();
        this.add(jp1);
        setTitle("我的单词库");
        setBounds(100,100,600,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridLayout(3,1));
        this.setPreferredSize(new Dimension(400,400));
        this.setVisible(true);
    }
	
	//监听提交单词按钮
	public static void addLinstenerSub (JButton b){
		b.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				words myWords = new words(user);
				String word = wordInput.getText();
				String meaning = meaningInput.getText();
				//判断输入的单词是否正确
				if(word.length() == 0){
					JOptionPane.showMessageDialog(null, "添加单词不能为空！");
					return;
				}
				if(meaning.length() == 0){
					JOptionPane.showMessageDialog(null, "单词意思不能为空！");
					return;
				}
				if(myWords.addWord(word, meaning)){
					JOptionPane.showMessageDialog(null, "添加成功！");

				}else{
					JOptionPane.showMessageDialog(null, "添加失败！该单词已存在");
				}
				wordInput.setText("");
                meaningInput.setText("");
        	}
		});
	}
	
    //创建添加单词面板
    static void getJp1(){
    	wordInput = new JTextField(8);
        meaningInput = new JTextField(25);
        subWord = new JButton("提交");
        addLinstenerSub(subWord);
    	jp1 = new JPanel();   
        jp1.add(wordInput);
        jp1.add(meaningInput);
        jp1.add(subWord);
        jp1.add(ways);
    }
    //创建排序方式复选框
    static void getWayBox(){
    	ways = new JComboBox();
        ways.addItem("随机排序");
        ways.addItem("按时间升序");
        ways.addItem("按时间降序");
        ways.addItem("按字母升序");
        ways.addItem("按字母降序");
        
    }

    //监听右边下拉框库里单词的展示方式选择
	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		if(e.getStateChange() == ItemEvent.SELECTED) {
			String sql = ways.getSelectedItem().toString();
			new showWords(user, sql);
        }
              
	}
	
	public static void main(String[] args) {
        wordsRepository frame = new wordsRepository("wyy");

    }
}