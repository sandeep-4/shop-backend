package spring.java.io.shop.notification;

import java.util.List;

public class NotificationMessage {

	private int code;
	private Object body;
	private String clientId;
	private String projectId=null;
	private List<String> notifyUserIds=null;
	

	
	public NotificationMessage(int code, Object body, String clientId, String projectId) {
		super();
		this.code = code;
		this.body = body;
		this.clientId = clientId;
		this.projectId = projectId;
	}
	
	
	
	
	public NotificationMessage(int code, Object body, String clientId, String projectId, List<String> notifyUserIds) {
		super();
		this.code = code;
		this.body = body;
		this.clientId = clientId;
		this.projectId = projectId;
		this.notifyUserIds = notifyUserIds;
	}




	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public Object getBody() {
		return body;
	}
	public void setBody(Object body) {
		this.body = body;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public List<String> getNotifyUserIds() {
		return notifyUserIds;
	}
	public void setNotifyUserIds(List<String> notifyUserIds) {
		this.notifyUserIds = notifyUserIds;
	}
	
	
	
	
}
