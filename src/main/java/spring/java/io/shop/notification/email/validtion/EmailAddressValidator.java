package spring.java.io.shop.notification.email.validtion;

import javax.mail.internet.InternetAddress;

public class EmailAddressValidator {

	public boolean validate(String emailAddress) {
		if(emailAddress ==null) {
			return false;
		}
		try {
			new InternetAddress(emailAddress,true);
		} catch (Exception e) {
			return false;
			// TODO: handle exception
		}
		return true;
	}
}
