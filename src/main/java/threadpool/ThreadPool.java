package threadpool;

import data.io.UrlFactory;
//import data.io.URLFactory;
import data.model.UrlModel;
import connect.httpconnection.HttpConnect;

public class ThreadPool implements Runnable {
	UrlModel url;
	public void run() {
		try {
			HttpConnect.connect(url);
			System.out.println(url);
			UrlFactory.saveUrl(url);
		} catch (Exception e) {
			e.printStackTrace();
			}
	}
	public ThreadPool(UrlModel url)
	{
		this.url = url;
	}
}

