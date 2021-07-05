package spring.java.io.shop.scheduler.model;

import java.util.Date;

public class ContentLogInfo {

	private String contentID;
	private String title;
	private String userIdOpen;
	private Date openTime;
	private String os;
	private String bookendVersion;
	private String ipAddress;
	
	
	
	public ContentLogInfo() {
	
	}
	public ContentLogInfo(String contentID, String title, String userIdOpen, Date openTime, String os,
			String bookendVersion, String ipAddress) {
		super();
		this.contentID = contentID;
		this.title = title;
		this.userIdOpen = userIdOpen;
		this.openTime = openTime;
		this.os = os;
		this.bookendVersion = bookendVersion;
		this.ipAddress = ipAddress;
	}
	public String getContentID() {
		return contentID;
	}
	public void setContentID(String contentID) {
		this.contentID = contentID;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUserIdOpen() {
		return userIdOpen;
	}
	public void setUserIdOpen(String userIdOpen) {
		this.userIdOpen = userIdOpen;
	}
	public Date getOpenTime() {
		return openTime;
	}
	public void setOpenTime(Date openTime) {
		this.openTime = openTime;
	}
	public String getOs() {
		return os;
	}
	public void setOs(String os) {
		this.os = os;
	}
	public String getBookendVersion() {
		return bookendVersion;
	}
	public void setBookendVersion(String bookendVersion) {
		this.bookendVersion = bookendVersion;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	
	
}
