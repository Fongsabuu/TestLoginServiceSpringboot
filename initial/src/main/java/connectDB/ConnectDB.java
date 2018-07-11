package connectDB;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

import Model.Login;


public class ConnectDB {
	public static Connection getDBConnection() throws SQLException, ClassNotFoundException {

		String dbUrl = "jdbc:mysql://localhost/testloginservice";
		String dbClass = "com.mysql.jdbc.Driver";
		String userName = "root", password = "";
		Class.forName(dbClass);
		Connection connect = (Connection) DriverManager.getConnection(dbUrl, userName, password);
		return connect;
	}
	
	public String getAllUser(String user, String pass) {
		String sql = "SELECT * FROM user Where username='" + user + "'" + "&& password='" + pass + "'" ;
		String status="0";
		try {
			Connection con = getDBConnection();
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) status = "1";
            con.close();
		} catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
        	e.printStackTrace();
		} finally {
        	return status;
        }
	} 
}
