package properties;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class MyProperties {

	public static void writeProperties() {
		// TODO Auto-generated method stub
		Properties properties = new Properties();
		try {
		FileOutputStream fileOutputStream = new FileOutputStream("config.properties");
		
		properties.put("numThread", "4"); // số thread, số url trong 1 lần chạy
		properties.put("timeOut", "20");  // thời gian sống của 1 thread đơn vị giây
		properties.put("url", "jdbc:mysql://localhost:3306/ListUrl");//kết nối mysql
		properties.put("user", "giamhm"); 
		properties.put("password", "giamhm1997");
		properties.put("inputTable", "listUrlInput");// bảng lưu dữ liệu đầu vào
		properties.put("outputTable", "ListUrl");// bảng lưu kết quả
		//properties.put("numConnection", "4");
		properties.store(fileOutputStream, "");
		
		fileOutputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
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
			fileOutputStream.close();
		}catch(Exception e ) {
			e.printStackTrace();
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
		}catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}
	
	public static void main(String[] args) {
		writeProperties();
	}
}
