package test;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import connect.mysqlconnection.ConnectionPoolManager;
import data.io.UrlFactory;
import data.model.UrlModel;
import properties.MyProperties;
import threadpool.ThreadPool;

public class Test {
	public static final Logger LOG = LogManager.getLogger(Test.class);
	
	public static void main(String[] args) {
		// get properties
		int numThread = 0, time = 0, poolSize = 0;
		numThread = Integer.parseInt(MyProperties.getProperty("threadPoolSize"));
		time = Integer.parseInt(MyProperties.getProperty("timeOut"));
		poolSize = Integer.parseInt(MyProperties.getProperty("connectionPoolSize"));
		
		String url, user, password;
		
		url = MyProperties.getProperty("url");
		user = MyProperties.getProperty("user");
		password = MyProperties.getProperty("password");
		// create a connectionPool
		ConnectionPoolManager pool = new ConnectionPoolManager(url, user, password, poolSize);

		ArrayList<UrlModel> arr = new ArrayList<UrlModel>();
		arr = UrlFactory.getDataInDatabase(pool);
		
		ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(100);
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(numThread, numThread, time, TimeUnit.SECONDS, queue);
		
		int i =0 ;
		while(i < 10000) {
			i++;
			for(UrlModel u : arr)
			{
				threadPoolExecutor.execute(new ThreadPool(u,pool));
			}
			try {
				Thread.sleep(10000);
			} catch(Exception e){
				LOG.error(e);
			}
		}
	}
}
