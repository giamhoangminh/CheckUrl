package connect.mysqlconnection;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class MysqlConnection {
	private static Connection connection;
	private static String user;
	private static String password;
	private static String url;
	
	public static Connection getConnection() {
		connection = null;
		Properties properties = new Properties();
		
		try {
			FileInputStream fileInputStream = new FileInputStream("config.properties");
			properties.load(fileInputStream);
			
			url = properties.getProperty("url");
			user = properties.getProperty("user");
			password = properties.getProperty("password");
			
			//driver register
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
			
			//get connection
			connection = (Connection) DriverManager.getConnection(url, user, password);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	//close connection
	public static void freeConnection() {
		try {
			if(connection != null && !connection.isClosed())
			connection.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
