package connect.mysqlconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Vector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import connect.httpconnection.WarningMessage;

public class ConnectionPoolManager {
	public static final Logger LOG = LogManager.getLogger(ConnectionPoolManager.class);
	
	private String url;
	private String user;
	private String password;
	private int poolSize ;
	
	public ConnectionPoolManager(String url, String user, String password, int poolSize) {
		this.url = url;
		this.user = user;
		this.password = password;
		this.poolSize = poolSize;
		initialize();
	}
	
	private static Vector<Connection> connectionPool = new Vector<Connection>();
	
	public ConnectionPoolManager() {
		// initialize connection pool
		initialize();
	}
	
	private void initialize() {
		while(!checkPoolIsFull()) {
			int i = 1;
			//create a new connection and add to Pool
			connectionPool.addElement(createANewConnection());
			LOG.debug("create connection " + i);
			i++;
		}
		LOG.info("create connection pool");
	}
	
	private synchronized boolean checkPoolIsFull() {
		if(this.poolSize > connectionPool.size()) {
			return false;
		}
		return true;
	}
	//create a new connection
	private Connection createANewConnection() {
		Connection con = null ;
		
		try {
			//driver register
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
			
			//get connection
			con = (Connection) DriverManager.getConnection(url, user, password);
			LOG.debug("connect to mysql");
		}catch (Exception e) {
			LOG.error(e);
			WarningMessage.sendMessage(e.getMessage());
		}
		
		return con;
	}
	// get connection from Pool
	public synchronized Connection getConnection() throws InterruptedException {
		Connection con = null;
		if(connectionPool.size() > 0) {
			con = (Connection) connectionPool.firstElement();
			connectionPool.removeElementAt(0);
		}
		while(con == null) {
			Thread.sleep(500);
			con = this.getConnection();
		}
		return con;
	}
	// return connection back to pool
	public synchronized void returnConnection(Connection con) {
		connectionPool.addElement(con);
	}
	
	// close every connection in Pool
	public void freeConnectionPool() {
		while(connectionPool.size() > 0) {
			int i = 0;
			Connection con = (Connection) connectionPool.firstElement();
			connectionPool.removeElementAt(0);
			try {
				if(con != null && !con.isClosed())
				con.close();
				LOG.debug("connecetion " + i + "is closed");
			}catch (Exception e) {
				LOG.error(e);
				WarningMessage.sendMessage(e.getMessage());
			}
			i++;
		}
		LOG.info("connectionPool is emty");
	}
}
