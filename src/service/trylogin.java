package service;
import java.sql.*;
import project.login;

public class trylogin extends connect{
	public boolean loginService (String name, String pass){
		Connection conn= connect.getConnection();
		Statement stmt;
		boolean result = false;
		try {
			stmt = conn.createStatement();
			String sql;
	        sql = "SELECT pass FROM user where user = '" + name + "'";
	        ResultSet rs = stmt.executeQuery(sql);
	        if(rs.next()){
	        	pass = rs.getString(1);
	        }
	        if (pass.equals(pass)){
	        	result = true;     	
	        }
	        // 关闭链接
	        rs.close();
	        stmt.close();
	        conn.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
		
	}
	public trylogin(){
		// TODO Auto-generated constructor stub
		
	}
}
