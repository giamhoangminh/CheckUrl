package connect.mysqlconnection;

import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import properties.MyProperties;

public class MysqlConnection {
	private static Connection connection;
	private static String user;
	private static String password;
	private static String url;
	
	public static final Logger LOG = LogManager.getLogger(MysqlConnection.class);

	public static Connection getConnection() {
		
		connection = null;
		
		url = MyProperties.getProperty("url");
		user = MyProperties.getProperty("user");
		password = MyProperties.getProperty("password");
		
		try {
			//driver register
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
			
			//get connection
			connection = (Connection) DriverManager.getConnection(url, user, password);
			LOG.debug("connect to mysql");
		}catch (Exception e) {
			LOG.error(e);
		}
		return connection;
	}
	
	//close connection
	public static void freeConnection() {
		try {
			if(connection != null && !connection.isClosed())
			connection.close();
			LOG.debug("connecetion is closed");
		}catch (Exception e) {
			LOG.error(e);
		}
	}
}
