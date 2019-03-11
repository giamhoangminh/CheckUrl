package connect.httpconnection;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import data.model.UrlModel;

public class HttpConnect {
	public static void connect(UrlModel u)
	{
		try
		{
			long t1, t2;
			String url = u.getUrl();
			URL obj = new URL(url);
			t1 = System.currentTimeMillis();
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();				con.setRequestMethod("GET");
			u.setResponseCode(con.getResponseCode());
			t2 = System.currentTimeMillis();
			Date date = new Date(t1);
			u.setTime(date.toString());
			u.setTimeResponse(t2-t1);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
