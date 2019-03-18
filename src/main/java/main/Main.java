package main;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import connect.httpconnection.WarningMessage;
import connect.mysqlconnection.ConnectionPoolManager;
import data.io.UrlDatabaseFactory;
import data.model.UrlModel;
import properties.MyProperties;
import threadpool.ThreadPool;

public class Main {
	public static final Logger LOG = LogManager.getLogger(Main.class);
	
	public static void main(String[] args) throws InterruptedException {
		long t1, t2;
		t1 = System.currentTimeMillis();
		// get properties
		MyProperties myProperties = new MyProperties();
		
		int numThread = 0, time = 0, poolSize = 0;
		numThread = Integer.parseInt(myProperties.getProperty("threadPoolSize"));
		time = Integer.parseInt(myProperties.getProperty("timeOut"));
		poolSize = Integer.parseInt(myProperties.getProperty("connectionPoolSize"));
		
		String url, user, password;
		
		url = myProperties.getProperty("url");
		user = myProperties.getProperty("user");
		password = myProperties.getProperty("password");
		
		// create a connectionPool
		ConnectionPoolManager pool = new ConnectionPoolManager(url, user, password, poolSize);
		UrlDatabaseFactory urlFactory = new UrlDatabaseFactory(pool);
		
		ArrayList<UrlModel> arr = new ArrayList<UrlModel>();
		arr = urlFactory.readData();
		
		ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(100);
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(numThread, numThread, time, TimeUnit.SECONDS, queue);
		
		int i =0 ;
		WarningMessage.sendMessage("start");
		while(i < 10) {
			i++;
			for(UrlModel u : arr)
			{
				threadPoolExecutor.execute(new ThreadPool(u, urlFactory));
			}
			try {
				Thread.sleep(10000);
			} catch(Exception e){
				LOG.error(e);
			}
		}
		pool.freeConnectionPool();
		//send message "end"
		t2 = System.currentTimeMillis() - t1;
		System.out.println("done " + t2);
		WarningMessage.sendMessage("end" + t2/100);

	}
}