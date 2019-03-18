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
	public void saveUrl(UrlModel u) throws InterruptedException {
		Connection con = pool.getConnection();
		try (Statement statement = (Statement) con.createStatement()){
			String sql = "insert ignore into ListUrl values( '" + u.getUrl() + "','" + u.getTime() + "','" + u.getTimeResponse().toString() + "','" + u.getResponseCode() + "')";
			statement.executeUpdate(sql);
			LOG.debug("connect to database to save data");
			LOG.debug("closed statement");
		}catch(Exception e) {
			LOG.error(e);
			WarningMessage.sendMessage(e.getMessage());
		}
		finally {
			pool.returnConnection(con);
			LOG.debug(" connection is returned to pool");
		}
	}
	// insert a url to database (input data)
	public void insertUrl(String url) throws InterruptedException {
		Connection con = pool.getConnection();
		try (Statement statement = (Statement) con.createStatement()){
			String sql = "insert ignore into listUrlInput(url) values( '" + url + "');";
			statement.executeUpdate(sql);
			LOG.debug("closed statement");
		}catch(Exception e) {
			LOG.error(e);
			WarningMessage.sendMessage(e.getMessage());
		}finally {
			pool.returnConnection(con);
			LOG.debug(" connection is returned to pool");
		}
	}
// get url from database
	public ArrayList<UrlModel> readData() throws InterruptedException {
		// TODO Auto-generated method stub
		ArrayList<UrlModel> arr = new ArrayList<UrlModel>();
		ResultSet resultSet = null;
		
		Connection con = pool.getConnection();
		try (Statement statement = (Statement) con.createStatement()){
			MyProperties myProperties = new MyProperties();
			String inputTable = myProperties.getProperty("inputTable");
			
			resultSet = statement.executeQuery("select * from " + inputTable);
			LOG.debug("connect to "+ inputTable + " to get data");
		    while(resultSet.next()){
		    	UrlModel url = new UrlModel();
		    	url.setUrl(resultSet.getString(1));
		    	arr.add(url);
		    }
		    resultSet.close();
			LOG.debug("closed statement");
		} catch (Exception e) {
			LOG.error(e);
		    WarningMessage.sendMessage(e.getMessage());
		}finally {
			pool.returnConnection(con);
			LOG.debug(" connection is returned to pool");
		}
		return arr;
	}
	// delete url in database
	public void deleteUrl(String url) throws InterruptedException {
			Connection con = pool.getConnection();
			try(Statement statement = (Statement) con.createStatement()) {
			statement.executeUpdate("delete from ListUrl where url = '" +url +"'");
			statement.executeUpdate("delete from listUrlInput where url = '" +url +"'");
			LOG.debug("closed statement");
		}catch(Exception e) {
			LOG.error(e);
			WarningMessage.sendMessage(e.getMessage());
		}
			finally {
				pool.returnConnection(con);
			}
	}

}
