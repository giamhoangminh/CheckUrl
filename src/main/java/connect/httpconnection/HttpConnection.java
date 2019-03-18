package connect.httpconnection;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import data.model.UrlModel;

public class HttpConnection {
	public static final Logger LOG = LogManager.getLogger(HttpConnection.class);

	public void connect(UrlModel u)
	{
		try
		{
			long t1, t2;
			
			String url = u.getUrl();
			URL obj = new URL(url);
			
			// time send request
			t1 = System.currentTimeMillis();
			
			//send request
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			u.setResponseCode(con.getResponseCode());
			
			//time get response
			t2 = System.currentTimeMillis();
			// set time for url model
			Date date = new Date(t1);
			u.setTime(date.toString());
			u.setTimeResponse(t2-t1);
		}
		catch(Exception e)
		{
			LOG.error(e);
		}
	}
	
	//connect to an url (use for send message)
	public void connect(String url) {
		try {
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("POST");
			LOG.info(con.getResponseCode());
		}catch(Exception e) {
			LOG.error(e);
		}
	}
	
}
