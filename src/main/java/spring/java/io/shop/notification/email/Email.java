package spring.java.io.shop.notification.email;

import java.util.Set;

public interface Email {

	String getFromAddress();
	
	Set<String> getToAddress();
	
	Set<String> getCcAddress();
	
	Set<String> getBccAddress();
	
	Set<String> getAttachments();
	
	String getSubject();
	
	String getBody();
	
}
