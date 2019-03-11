package test;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import data.io.UrlFactory;
import data.model.UrlModel;
import properties.MyProperties;
import threadpool.ThreadPool;

public class Test {

	public static void main(String[] args) {
		
		int numThread = 0, time = 0;
		numThread = Integer.parseInt(MyProperties.getProperty("numThread"));
		time = Integer.parseInt(MyProperties.getProperty("timeOut"));
		
		ArrayList<UrlModel> arr = new ArrayList<UrlModel>();
		arr = UrlFactory.getDataInDatabase();
		
		ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(100);
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(numThread, numThread, time, TimeUnit.SECONDS, queue);
		
		for(UrlModel u : arr)
		{
			threadPoolExecutor.execute(new ThreadPool(u));
		}
	}
}
