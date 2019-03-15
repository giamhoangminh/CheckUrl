package threadpool;

import data.io.UrlDatabaseFactory;
import data.model.UrlModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import connect.httpconnection.HttpConnection;
import connect.httpconnection.WarningMessage;

public class ThreadPool implements Runnable {
	public static final Logger LOG = LogManager.getLogger(ThreadPool.class);
	UrlModel url;
	UrlDatabaseFactory urlFactory;
	public void run() {
		try {
			HttpConnection httpConnection = new HttpConnection();
			httpConnection.connect(url);
			LOG.debug("connect to url");
			System.out.println(url);
			urlFactory.saveUrl(url);
			LOG.debug("Save data");
		} catch (Exception e) {
			LOG.error(e);
			WarningMessage.sendMessage(e.getMessage());
			}
	}
	public ThreadPool(UrlModel url, UrlDatabaseFactory urlFactory)
	{
		this.url = url;
		this.urlFactory = urlFactory;
	}
}

