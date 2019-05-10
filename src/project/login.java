package project;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;
import javax.swing.text.ChangedCharSetException;

import service.trylogin;


public class login extends JFrame{
	JPanel jp1, jp2, jp3;
	JLabel userNameInfo, passwordInfo;
	static JTextField userNameInput, passwordInput;
	JButton signButton, loginButton;
	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		login show = new login();
	}
	public static void addLinstenerSign (JButton b, final login show){
		b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	show.setVisible(false);
            	new sign();
            }
        });
    	
    } 
	
	public static void addLinstenerLogin (JButton b, final login show){
		b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	trylogin trylogin = new trylogin();
            	String name = userNameInput.getText();
            	String pass = passwordInput.getText();
            	if(name.length() == 0){
            		JOptionPane.showMessageDialog(null, "用户名不能为空");
            	}else if(pass.length() == 0){
            		JOptionPane.showMessageDialog(null, "密码不能为空");
            	}else if(trylogin.loginService(name, pass)){
            		JOptionPane.showMessageDialog(null, "登录成功");
            		show.setVisible(false);
            	}else{
            		JOptionPane.showMessageDialog(null, "登录失败");
            	}
            }
        });
    	
    } 
	
	
	public login() {
		// TODO Auto-generated method stub
		
		jp1 = new JPanel();
		jp2 = new JPanel();
		jp3 = new JPanel();
		this.setLayout(new GridLayout(3, 1));
		
		
		userNameInfo = new JLabel(" 用户名  ");
		userNameInput = new JTextField(10);
		passwordInfo = new JLabel(" 密    码   ");
		passwordInput = new JPasswordField(10);
		signButton = new JButton("注册");
		loginButton = new JButton("登录");
		
		addLinstenerLogin(loginButton, this);
		addLinstenerSign(signButton, this);
		jp1.add(userNameInfo);
		jp1.add(userNameInput);
		jp2.add(passwordInfo);
		jp2.add(passwordInput);
		jp3.add(loginButton);
		jp3.add(signButton);
		
		this.add(jp1);
        this.add(jp2);
        this.add(jp3);
        
		this.setSize(300, 200);
        this.setTitle("用户登录");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
		
	}

}
