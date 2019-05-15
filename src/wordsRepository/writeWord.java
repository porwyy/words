package wordsRepository;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import project.words;

public class writeWord extends JFrame implements ItemListener{
	static JPanel jp1,jp2,jp3;
	static JLabel word,restW;
	static JButton subWord,next,last;
	static JTextField  inputWord;
	static words myWords;
	static JComboBox choosDate;
	static String user;
	static String[] oneWord;
	static int num,now;
	public writeWord(String user){
		super();
		this.user = user;
		setJp1(this);
		this.setTitle("单词默写");
		this.setLayout(new GridLayout(3,1));
        setBounds(100,100,600,400);
        setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        this.setVisible(false);
        this.setVisible(true);
	}
	
	static void setJp3(writeWord e){
		if(jp3 != null){
			e.remove(jp3);
		}
		
		last = new JButton("上一个");
		next = new JButton("下一个");
		next.addActionListener(nextWord(e));
		last.addActionListener(lastWord(e));
		jp3 = new JPanel();
		jp3.add(next);
		jp3.add(last);
		e.add(jp3);
	}
	
	static void setJp2(writeWord e){
		if(jp2 != null){
			e.remove(jp2);
		}
		jp2 = new JPanel();
		oneWord = myWords.getWord("learn");
		word = new JLabel(oneWord[1]);
		word.setSize(400,80);
		inputWord = new JTextField(15);
		subWord = new JButton("提交");
		addLinstenerSub(subWord);
		jp2.add(word);
		jp2.add(inputWord);
		jp2.add(subWord);
		e.add(jp2);	
	}
	
	public static ActionListener nextWord(final writeWord e){
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent em) {
				if(myWords.restWords() == 1){
					JOptionPane.showMessageDialog(null, "已经是最后一个单词aaa！");
					return ;
				}else{
					oneWord = myWords.nextWord("learn");
					setJp1(e);
					setJp2(e);
					setJp3(e);
					
					e.setVisible(false);
					e.setVisible(true);
				}
				
			}
		};
	}
	//监听上一个单词按钮
	public static  ActionListener lastWord(final writeWord e){
			return new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent em) {
					if(myWords.restWords() == num){
						JOptionPane.showMessageDialog(null, "已经是第一个单词！");
					}else{
						oneWord = myWords.lastWord("learn");
						setJp1(e);
						setJp2(e);
						setJp3(e);
						
						e.setVisible(false);
						e.setVisible(true);
					}
				}
			};
		}
		
	//监听单词提交
	public static void addLinstenerSub (JButton b){
			b.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String toDo = e.getActionCommand();
					String word = inputWord.getText();
					if(word.length() == 0){
						JOptionPane.showMessageDialog(null, "提交不能为空哦！");
					}else if(word.equals(oneWord[0])){
						JOptionPane.showMessageDialog(null, "正确！");
						myWords.upWord(1);
					}else {
						JOptionPane.showMessageDialog(null, "错误！");
						myWords.upWord(0);
					}
					inputWord.setText("");
					
	        	}
			});
		}
	

	//构建单词日期选择下拉框
	static void showDate(writeWord e){
		choosDate = new JComboBox();
		choosDate.addItem("前一天");
		choosDate.addItem("三天内");
		choosDate.addItem("一周内");
		choosDate.addItem("一个月内");
		choosDate.setSize(50, 30);
		choosDate.addItemListener(e);
	}
	
	static void setJp1(writeWord e){
		if(jp1 != null){
			e.remove(jp1);
			now = myWords.restWords();
		}
		
		jp1 = new JPanel();
		String restInfoString = "还剩余 " + now + " 个单词";
		restW = new JLabel(restInfoString);
		showDate(e);
		jp1.add(restW);
		jp1.add(choosDate);
		e.add(jp1);
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		writeWord ssWord = new writeWord("wyy");
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		if(e.getStateChange() == ItemEvent.SELECTED) {
			String choosDay = choosDate.getSelectedItem().toString();
			myWords = new words(user, choosDay);
			
			oneWord = myWords.nextWord("learn");
			num = myWords.restWords();
			now = num;
			setJp1(this);
			setJp2(this);
			setJp3(this);
			this.setVisible(false);
			this.setVisible(true);
        }
	}

}
