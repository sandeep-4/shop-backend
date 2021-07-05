package spring.java.io.shop.notification.email.transport;

public class EmailTransportException extends RuntimeException{

	
	private static final long serialVersionUID = 1L;

	public EmailTransportException(String message,Exception cause) {
		super(message,cause);
	}
}
