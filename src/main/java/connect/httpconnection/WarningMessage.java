package connect.httpconnection;

import properties.MyProperties;

public class WarningMessage {
	// send message warning
		public static void sendMessage(String message) {
			MyProperties myProperties = new MyProperties();
			
			String token = myProperties.getProperty("bot_token");
			String id = myProperties.getProperty("id_chat");
			
			String url = "https://api.telegram.org/bot" + token + "/sendMessage?chat_id=" + id + "&text=" + message;
			
			HttpConnection httpConnection = new HttpConnection();
			httpConnection.connect(url);
		}
}
