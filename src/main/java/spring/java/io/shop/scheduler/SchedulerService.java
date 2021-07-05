package spring.java.io.shop.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import spring.java.io.shop.configs.AppConfig;
import spring.java.io.shop.tracelogged.EventLogManager;

/*
 * Copyright 2021 @sandeep.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@Service
public class SchedulerService {

	@Autowired
	private ApplicationContext appContext;
	
	private static ThreadPoolTaskExecutor taskExecutor;
	
	@Autowired
	private EmailNotificationWorker emailNotificationWorker;
	
	@Autowired
	private PushNotificationWorker notificationWorker;
	
	@Autowired
	AppConfig appConfig;
	
	@Scheduled(fixedDelay = 1000)
	public void doJobSendEmailNotification() {
		doJob(emailNotificationWorker);
	}
	
	private void doJob(JobWorker jobWorker) {
		try {
			if(taskExecutor==null) {
				taskExecutor=(ThreadPoolTaskExecutor) appContext.getBean("executorWithPoolSizeRange");
				
			}
			int corePoolSize=taskExecutor.getCorePoolSize();
			if(!jobWorker.isQueueEmpty()) {
				if(taskExecutor.getActiveCount()<corePoolSize) {
					if(jobWorker.getJobType().equals(JobWorker.JobType.SINGLE)) {
						jobWorker.doWork();
					}else {
						for(int i=0;i<Math.min(corePoolSize-taskExecutor.getActiveCount(),corePoolSize);i++) {
							jobWorker.doWork();
						}
					}
				}
			}
		} catch (Exception e) {
			EventLogManager.getInstance().error(e);
			
		}
	}
	
}
