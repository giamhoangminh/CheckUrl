package properties;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MyProperties {
	public static final Logger LOG = LogManager.getLogger(MyProperties.class);
	
	public static void writeProperties() {
		Properties properties = new Properties();
		try {
		FileOutputStream fileOutputStream = new FileOutputStream("config.properties");
		
		properties.put("threadPoolSize", "4"); // number of Thread in thread Pool
		properties.put("timeOut", "20");  // number of second a Thread live
		properties.put("url", "jdbc:mysql://localhost:3306/ListUrl");//url connect to mysql
		properties.put("user", "giamhm"); // user to connect mysql
		properties.put("password", "giamhm1997"); // password of user
		properties.put("inputTable", "listUrlInput");// input Table
		properties.put("outputTable", "ListUrl");// Output Table
		properties.put("connectionPoolSize", "4");// number of connection initialized
	//	properties.put("expiredTime","3000");
		properties.put("bot_token", "610226108:AAENjU0imx9e-5uya0Myw-PtHHjFHDe7mc0"); //token of telegram bot
		properties.put("id_chat", "-398773035"); //id of chat to send warning
		
		properties.store(fileOutputStream, "");
		LOG.info("Lưu properties");
		fileOutputStream.close();
		} catch (Exception e) {
			LOG.error(e);
		}
	}
	
	public void writeProperty(String key, String value) {
		Properties properties = new Properties();
		try {
			FileInputStream fileInputStream = new FileInputStream("config.properties");
			FileOutputStream fileOutputStream = new FileOutputStream("config.properties");
			properties.load(fileInputStream);
			properties.put(key, value);
			properties.store(fileOutputStream, "");
			LOG.info("add property " + key + " : " + value);
			fileOutputStream.close();
		}catch(Exception e ) {
				LOG.error(e);
		}
	}
	
	public String getProperty(String key) {
		String value = "";
		
		Properties properties = new Properties();
		try {
			FileInputStream fileInputStream = new FileInputStream("config.properties");
			properties.load(fileInputStream);
			
			value = properties.getProperty(key);
			fileInputStream.close();
			LOG.info("lấy property : " + key);
		}catch (Exception e) {
			LOG.error(e);
		}
		return value;
	}
	
	public static void main(String[] args) {
		writeProperties();
	}
}
