package service;
import java.sql.*;

public class trySign {
	public boolean signService (String name, String pass){
		Connection conn= connect.getConnection();
		Statement stmt;
		boolean result = false;
		try {
			stmt = conn.createStatement();
			String sql = "insert into user values(?,?)";
			PreparedStatement pst = conn.prepareStatement(sql);//用来执行SQL语句查询，对sql语句进行预编译处理
     		pst.setString(1, name);
     		pst.setString(2, pass);
     		pst.executeUpdate();
	        result = true;
	        // 关闭链接
	        stmt.close();
	        conn.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
		
	}
}
