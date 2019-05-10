package project;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;
import javax.swing.text.ChangedCharSetException;

import service.trySign;
import service.trylogin;


public class sign extends JFrame{
	JPanel jp1, jp2, jp3, jp4;
	JLabel userNameInfo, passwordInfo, passwordSureInfo;
	static JTextField userNameInput, passwordInput, passwordSure;
	JButton signButton, loginButton;
	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		sign show = new sign();
	}

	
	public static void addLinstenerSign (JButton b, final sign show){
		b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	String name = userNameInput.getText();
            	String pass = passwordInput.getText();
            	System.out.println(name);
            	System.out.println(pass);
            	if(name.length() == 0){
            		JOptionPane.showMessageDialog(null, "用户名不能为空！");
            		return;
            	}else if(pass.length() == 0 || passwordSure.getText().length() == 0){
            		JOptionPane.showMessageDialog(null, "密码不能为空！");
            		return;
            	}else if(! passwordInput.getText().equals(passwordSure.getText())){
            		JOptionPane.showMessageDialog(null, "密码不一致！");
            		return;
            	}else{
            	
            		trySign trySign = new trySign();
            		
            		if(trySign.signService(name, pass)){
            			JOptionPane.showMessageDialog(null, "注册成功！");
            			show.setVisible(false);
                        new login();
            		}else{
            			JOptionPane.showMessageDialog(null, "用户名已存在！");
            		}
            	}
            }
        });
    	
    } 
	
	
	public sign() {
		// TODO Auto-generated method stub
		//将界面分成三部分
		jp1 = new JPanel();
		jp2 = new JPanel();
		jp3 = new JPanel();
		jp4 = new JPanel();
		this.setLayout(new GridLayout(4, 1));
		
		
		userNameInfo = new JLabel(" 用户名  ");
		userNameInput = new JTextField(10);
		passwordInfo = new JLabel(" 密     码    ");
		passwordInput = new JPasswordField(10);
		passwordSureInfo = new JLabel("确认密码");
		passwordSure = new JPasswordField(10);
		signButton = new JButton("注册");
		
		addLinstenerSign(signButton, this);
		jp1.add(userNameInfo);
		jp1.add(userNameInput);
		jp2.add(passwordInfo);
		jp2.add(passwordInput);
		jp3.add(passwordSureInfo);
		jp3.add(passwordSure);
		jp4.add(signButton);
		
		this.add(jp1);
        this.add(jp2);
        this.add(jp3);
        this.add(jp4);
        
		this.setSize(350, 250);
        this.setTitle("注册");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
		
	}

}
