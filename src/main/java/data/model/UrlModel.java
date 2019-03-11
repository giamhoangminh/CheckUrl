package data.model;

public class UrlModel {
	private String url;
	private String time;
	private Long timeResponse;
	private int responseCode;
	public UrlModel() {
		super();
	}
	public UrlModel(String url, String time, Long timeResponse, int responseCode) {
		super();
		this.url = url;
		this.time = time;
		this.timeResponse = timeResponse;
		this.responseCode = responseCode;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Long getTimeResponse() {
		return timeResponse;
	}
	public void setTimeResponse(Long timeResponse) {
		this.timeResponse = timeResponse;
	}
	public int getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}
	
	@Override
	public String toString() {
		return this.url + "\t\t" + this.time + "\t\t" + this.timeResponse + "\t\t" + this.responseCode;
	}
}
