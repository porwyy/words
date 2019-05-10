package service;
import java.sql.*;

public abstract class connect {
	private static String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	private static String DB_URL = "jdbc:mysql://localhost:3306/words?useUnicode=true&characterEncoding=utf8";
	private  static String USER = "wyy";
	private static String PASS = "19990122";
	public static Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static void closeConn(Connection conn,Statement stm , ResultSet rs ) {

        if(stm!=null){
            try {
                stm.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(rs!=null){
            try{
                rs.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }
	public connect() {
		// TODO Auto-generated constructor stub
		
	}
}
