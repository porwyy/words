package project;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class nav extends JFrame{
	JPanel jpNav;
	JButton nav1, nav2, nav3, nav4;
	public String user = "wyy";
	
	

	public static void addLinstener (JButton b){
		b.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	/**
	 * @param args
	 */
	
	public nav (String name){
		this.user = name;
		jpNav = new JPanel();
		nav1 = new JButton("我的单词库");
		nav2 = new JButton("单词测试");
		nav3 = new JButton("单词游戏");
		nav4 = new JButton("我的数据");
		this.setLayout(new GridLayout(1, 1));
		jpNav.add(nav1);
		jpNav.add(nav2);
		jpNav.add(nav3);
		jpNav.add(nav4);
		this.add(jpNav);
		this.setSize(550, 700);
        this.setTitle("welcome ! " + name);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		nav a = new nav("wyy");
		
	}

}
