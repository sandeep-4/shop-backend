package spring.java.io.shop.core;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import org.springframework.stereotype.Service;

@Service
public class LocalQueueManager {

	private static LocalQueueManager instance;

	public static synchronized LocalQueueManager getInstance() {
		if(instance==null) {
			instance =new LocalQueueManager();
		}
		return instance;
	}
	
	public static Queue<Map<String,Object>> emailQueue;
	
	private LocalQueueManager() {
		emailQueue=new LinkedList<Map<String,Object>>();
		loadData();
	}
	
	public synchronized Boolean isMailQueueEmpty() {
		if(emailQueue.size()==0) {
			return true;
		}else {
			return false;
		}
	}
	
	public synchronized Map<String,Object> getMailQueue(){
		return emailQueue.remove();
	}
	
	public synchronized void addMailQueue(Map<String,Object> obj) {
		emailQueue.add(obj);
	}
	
	public void loadData() {
		//todo
	}
}
