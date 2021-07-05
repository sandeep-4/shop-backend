package spring.java.io.shop.notification.email.validtion;

public class IncompleteEmailException extends RuntimeException{

	
	private static final long serialVersionUID = 1L;

	public IncompleteEmailException(String message) {
		super(message);
	}
}
