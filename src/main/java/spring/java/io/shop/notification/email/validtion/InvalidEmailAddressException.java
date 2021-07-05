package spring.java.io.shop.notification.email.validtion;

public class InvalidEmailAddressException extends RuntimeException{

	
	private static final long serialVersionUID = 1L;

	public InvalidEmailAddressException(String message) {
		super(message);
	}
}

