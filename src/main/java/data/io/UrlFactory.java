package data.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import connect.httpconnection.HttpConnect;
import connect.mysqlconnection.ConnectionPoolManager;
import connect.mysqlconnection.MysqlConnection;
import data.model.UrlModel;
import properties.MyProperties;

public class UrlFactory {
	public static final Logger LOG = LogManager.getLogger(HttpConnect.class);
	//ghi lần lượt dữ liệu ra file
	public static void writeOutPutToFile2(UrlModel url, String path)
	{
		try
		{
			File file = new File(path);
			if(!file.exists())
			{
				file.createNewFile();
			}
			
			FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			
			bw.write(url.toString());
			bw.newLine();
			
			bw.close();
			fw.close();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	// ghi dữ liệu ra file
	public static boolean writeOutPutToFile(ArrayList<UrlModel> arr, String path)
	{
		try
		{
			FileOutputStream fos = new FileOutputStream(path);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			BufferedWriter bw = new BufferedWriter(osw);
			
			for(UrlModel url : arr)
			{
				bw.write(url.toString());
				bw.newLine();
			}
			
			bw.close();
			osw.close();
			fos.close();	
			return true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return false;
	}
	//đọc dữ liệu từ file
	public static ArrayList<UrlModel> readDataFromFile(String path)
	{
		ArrayList<UrlModel> arr = new ArrayList<UrlModel>();
		try
		{
			FileInputStream fis = new FileInputStream(path);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader bfr = new BufferedReader(isr);
			
			String line = bfr.readLine();
			while( line != null)
			{
				UrlModel url = new UrlModel();
				url.setUrl(line);
				arr.add(url);
				line = bfr.readLine();
			}
			bfr.close();
			isr.close();
			fis.close();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		return arr;
	}
	//lay data tu database
	public static ArrayList<UrlModel> getDataInDatabase(ConnectionPoolManager pool) {
		ArrayList<UrlModel> arr = new ArrayList<UrlModel>();
		ResultSet resultSet = null;
		try {
			String inputTable = MyProperties.getProperty("inputTable");
			
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
		    //e.printStackTrace();
		}
		return arr;
	}
	
	//luu data vao database cả array
	public static void saveData(ArrayList<UrlModel> arr) {
		try {
			String outputTable = MyProperties.getProperty("outputTable");
			Statement statement = (Statement) MysqlConnection.getConnection().createStatement();
			String sql = "insert into " + outputTable +" values(";
			
			for( UrlModel u : arr) {
				//insert
				sql += u.getUrl() + "," + u.getTime() + "," + u.getTimeResponse().toString() + "," + u.getResponseCode() + ")";
				statement.executeUpdate(sql);
			}
			LOG.debug("connect to database to save data");
			
			statement.close();
			MysqlConnection.freeConnection();
			
			LOG.debug("closed connection");
		}catch(Exception e) {
			LOG.error(e);
			//e.printStackTrace();
		}
	}
	
	//luu data vao database tung url 
	public static void saveUrl(UrlModel u, ConnectionPoolManager pool) {
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
			e.printStackTrace();
		}
	}
	
	public static void insertUrl(String url) {
		try {
			Statement statement = (Statement) MysqlConnection.getConnection().createStatement();
			String sql = "insert ignore into listUrlInput(url) values( '" + url + "');";
			statement.executeUpdate(sql);
			statement.close();
			MysqlConnection.freeConnection();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteUrl(String url) {
		try {
			Statement statement = (Statement) MysqlConnection.getConnection().createStatement();
			statement.executeUpdate("delete from ListUrl where url = '" +url +"'");
			statement.executeUpdate("delete from listUrlInput where url = '" +url +"'");
			statement.close();
			MysqlConnection.freeConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public static void main(String[] args) {
		try {
			FileInputStream fileInputStream = new FileInputStream("data1.txt");
			InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			
			String line = bufferedReader.readLine();
			while(line != null) {
				insertUrl(line);
				line = bufferedReader.readLine();
			}
			bufferedReader.close();
			inputStreamReader.close();
			fileInputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
