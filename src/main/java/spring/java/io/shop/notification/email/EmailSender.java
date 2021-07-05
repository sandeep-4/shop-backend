package spring.java.io.shop.notification.email;

import org.springframework.stereotype.Component;

import spring.java.io.shop.notification.email.transport.EmailTransportConfiguration;
import spring.java.io.shop.tracelogged.EventLogManager;

@Component
public class EmailSender {

	private String smtpHost="";
	private int smtpPort=25;
	private Boolean isSmtps=false;
	
	public boolean SendEmail(String mailto,String Subject,String Body) {
		boolean status=true;
		try {
			EmailTransportConfiguration.configure(smtpHost,smtpPort,isSmtps);
			new EmailMessage()
			.from("no-reply@shop.com")
			.to(mailto)
			.withSubject(Subject)
			.withBody(Body)
			.send();
		} catch (Exception e) {
			EventLogManager.getInstance().error("Error sending email");
			status=false;
		}
		return status;
	}
}
