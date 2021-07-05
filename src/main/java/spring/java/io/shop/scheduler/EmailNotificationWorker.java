package spring.java.io.shop.scheduler;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import spring.java.io.shop.core.LocalQueueManager;
import spring.java.io.shop.notification.email.EmailSender;
import spring.java.io.shop.tracelogged.EventLogManager;

@Component
public class EmailNotificationWorker extends JobWorker{
	
	@Autowired
	private EmailSender emailSender;
	
	private String jobName="EmailNotificationWorker";

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
		return LocalQueueManager.getInstance().isMailQueueEmpty();
	}

	@Async
	@Override
	public void doWork() {
		if(!LocalQueueManager.getInstance().isMailQueueEmpty()) {
			Map<String, Object> request=LocalQueueManager.getInstance().getMailQueue();
			String emailAddress=(String) request.get("mail_address");
			String subject=(String) request.get("subject");
			String body=(String) request.get("body");
			EventLogManager.getInstance().info("Email sent to "+emailAddress);
			emailSender.SendEmail(emailAddress, subject, body);
			
			
			
			
			
		}
	}

}
