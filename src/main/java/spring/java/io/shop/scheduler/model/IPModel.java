package spring.java.io.shop.scheduler.model;

public class IPModel {

	private String logitude;
	private String latitude;
	private String city;

	public IPModel(String logitude, String latitude, String city) {
		super();
		this.logitude = logitude;
		this.latitude = latitude;
		this.city = city;
	}

	public String getLogitude() {
		return logitude;
	}

	public void setLogitude(String logitude) {
		this.logitude = logitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

}
