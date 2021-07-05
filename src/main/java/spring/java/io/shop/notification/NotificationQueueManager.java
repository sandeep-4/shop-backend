package spring.java.io.shop.notification;

import java.util.LinkedList;
import java.util.Queue;

import org.springframework.stereotype.Service;

@Service
public class NotificationQueueManager {

	private static NotificationQueueManager instance;
	
	public static synchronized NotificationQueueManager  getInstance() {
		if(instance==null) {
			instance=new NotificationQueueManager();
		}
		return instance;
	}
	
	private static Queue<NotificationMessage> messages;
	private static Queue<NotificationMessage> messageSystemNotify;
	
	private NotificationQueueManager() {
		messages=new LinkedList<NotificationMessage>();
		messageSystemNotify=new LinkedList<NotificationMessage>();
	}
	
	public synchronized NotificationMessage getMessage(){
		return messages.remove();
	}
	
	public synchronized NotificationMessage getMessageSystemNotify() {
		return messageSystemNotify.remove();
	}
	
	public synchronized void addMessage(NotificationMessage queueMessage) {
		messages.add(queueMessage);
	}
	
	public synchronized void addMessageSystemNotify(NotificationMessage queueMessage) {
		messageSystemNotify.add(queueMessage);
	}
	
	public synchronized Boolean IsQueueEmpty() {
		if(messages.size()==0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public synchronized Boolean IsQueueSystemNotifyEmpty() {
		if(messageSystemNotify.size()==0) {
			return true;
		}
		else {
			return false;
		}
	}
	
}
