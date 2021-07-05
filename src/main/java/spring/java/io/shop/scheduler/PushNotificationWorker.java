package spring.java.io.shop.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import spring.java.io.shop.notification.NotificationQueueManager;

@Component
public class PushNotificationWorker extends JobWorker{
	
	@Autowired
	private SimpMessagingTemplate template;
	
    private static final String WEBSOCKET_TOPIC = "/topic/notification";
    private static final String WEBSOCKET_SYSTEM_NOTIFICATION = "/topic/systemnotify";
    
    private String jobName="PushNotificationWorker";

	@Override
	public JobType getJobType() {
		return JobType.MULTIPLE;
	}

	@Override
	public String getJobName() {
		return this.jobName;
	}

	@Override
	public void setJobName(String name) {
		this.jobName=name;
		
	}

	@Override
	public Boolean isQueueEmpty() {
		return false;
	}

	@Async
	@Override
	public void doWork() {

		if(!NotificationQueueManager.getInstance().IsQueueEmpty()) {
			Object message=NotificationQueueManager.getInstance().getMessage();
			template.convertAndSend(WEBSOCKET_TOPIC,message);
		}
		
		if(!NotificationQueueManager.getInstance().IsQueueSystemNotifyEmpty()) {
			Object messObject=NotificationQueueManager.getInstance().getMessageSystemNotify();
			template.convertAndSend(WEBSOCKET_SYSTEM_NOTIFICATION,messObject);
		}
		
	}

}
