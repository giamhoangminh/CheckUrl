package threadpool;

import data.io.UrlFactory;
//import data.io.URLFactory;
import data.model.UrlModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import connect.httpconnection.HttpConnect;
import connect.mysqlconnection.ConnectionPoolManager;

public class ThreadPool implements Runnable {
	public static final Logger LOG = LogManager.getLogger(ThreadPool.class);
	UrlModel url;
	ConnectionPoolManager pool;
	public void run() {
		try {
			HttpConnect.connect(url);
			LOG.debug("connect to url");
			System.out.println(url);
			UrlFactory.saveUrl(url,pool);
			LOG.debug("Save data");
		} catch (Exception e) {
			e.printStackTrace();
			}
	}
	public ThreadPool(UrlModel url, ConnectionPoolManager pool)
	{
		this.url = url;
		this.pool = pool;
	}
}

