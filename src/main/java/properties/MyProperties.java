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
		
		properties.put("threadPoolSize", "4"); // số thread, số url trong 1 lần chạy
		properties.put("timeOut", "20");  // thời gian sống của 1 thread đơn vị giây
		properties.put("url", "jdbc:mysql://localhost:3306/ListUrl");//kết nối mysql
		properties.put("user", "giamhm"); 
		properties.put("password", "giamhm1997");
		properties.put("inputTable", "listUrlInput");// bảng lưu dữ liệu đầu vào
		properties.put("outputTable", "ListUrl");// bảng lưu kết quả
		properties.put("connectionPoolSize", "4");
		properties.put("expiredTime","3000");
		properties.store(fileOutputStream, "");
		LOG.info("Lưu properties");
		fileOutputStream.close();
		} catch (Exception e) {
			LOG.error(e);
		}
	}
	
	public static void writeProperty(String key, String value) {
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
	
	public static String getProperty(String key) {
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
