package data.io;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import connect.httpconnection.WarningMessage;
import connect.mysqlconnection.ConnectionPoolManager;
import data.model.UrlModel;
import properties.MyProperties;

public class UrlDatabaseFactory implements UrlFactoryInterface {

	public static final Logger LOG = LogManager.getLogger(UrlDatabaseFactory.class);
	
	ConnectionPoolManager pool;
	
	public UrlDatabaseFactory(ConnectionPoolManager pool) {
		this.pool = pool;
	}
// save url checked to database
	public void saveUrl(UrlModel u) {
		// TODO Auto-generated method stub
		try {
			Connection con = pool.getConnection();
			Statement statement = (Statement) con.createStatement();
			String sql = "insert ignore into ListUrl values( '" + u.getUrl() + "','" + u.getTime() + "','" + u.getTimeResponse().toString() + "','" + u.getResponseCode() + "')";
			statement.executeUpdate(sql);
			LOG.debug("connect to database to save data");
			statement.close();
			pool.returnConnection(con);
			LOG.debug("closed connection");
		}catch(Exception e) {
			LOG.error(e);
			WarningMessage.sendMessage(e.getMessage());
		}
	}
	// insert a url to database (input data)
	public void insertUrl(String url) {
		try {
			Connection con = pool.getConnection();
			Statement statement = (Statement) con.createStatement();
			String sql = "insert ignore into listUrlInput(url) values( '" + url + "');";
			statement.executeUpdate(sql);
			statement.close();
			pool.returnConnection(con);
			LOG.debug("closed connection");
		}catch(Exception e) {
			LOG.error(e);
			WarningMessage.sendMessage(e.getMessage());
		}
	}
// get url from database
	public ArrayList<UrlModel> readData() {
		// TODO Auto-generated method stub
		ArrayList<UrlModel> arr = new ArrayList<UrlModel>();
		ResultSet resultSet = null;
		try {
			MyProperties myProperties = new MyProperties();
			String inputTable = myProperties.getProperty("inputTable");
			
			Connection con = pool.getConnection();
			Statement statement = (Statement) con.createStatement();
			resultSet = statement.executeQuery("select * from " + inputTable);
			LOG.debug("connect to "+ inputTable + " to get data");
		    while(resultSet.next()){
		    	UrlModel url = new UrlModel();
		    	url.setUrl(resultSet.getString(1));
		    	arr.add(url);
		    }
		    resultSet.close();
			pool.returnConnection(con);
			LOG.debug(" connection is returned to pool");
		} catch (Exception e) {
			LOG.error(e);
		    WarningMessage.sendMessage(e.getMessage());
		}
		return arr;
	}
	// delete url in database
	public void deleteUrl(String url) {
		try {
			Connection con = pool.getConnection();
			Statement statement = (Statement) con.createStatement();
			statement.executeUpdate("delete from ListUrl where url = '" +url +"'");
			statement.executeUpdate("delete from listUrlInput where url = '" +url +"'");
			statement.close();
			pool.returnConnection(con);
			LOG.debug("closed connection");
		}catch(Exception e) {
			LOG.error(e);
			WarningMessage.sendMessage(e.getMessage());
		}		
	}

}
