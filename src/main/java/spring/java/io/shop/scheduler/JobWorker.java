package spring.java.io.shop.scheduler;

import org.springframework.stereotype.Component;

@Component
public abstract class JobWorker {

	public abstract JobType getJobType();
	
	public abstract String getJobName();
	
	public abstract void setJobName(String name);
	
	public abstract Boolean isQueueEmpty();
	
	public abstract void doWork();
	
	
	
	public static enum JobType{
		SINGLE,
		MULTIPLE
	}
	
}
