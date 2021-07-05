package spring.java.io.shop.notification.email;

import java.util.HashSet;
import java.util.Set;

import spring.java.io.shop.notification.email.transport.EmailTransportException;
import spring.java.io.shop.notification.email.transport.PostalService;
import spring.java.io.shop.notification.email.validtion.EmailAddressValidator;
import spring.java.io.shop.notification.email.validtion.IncompleteEmailException;
import spring.java.io.shop.notification.email.validtion.InvalidEmailAddressException;

public class EmailMessage implements EmailBuilder, Email {

	private static EmailAddressValidator emailAddressValidator = new EmailAddressValidator();
	private static PostalService postalService = new PostalService();

	private String fromAddress;
	private Set<String> toAddresses = new HashSet<String>();
	private Set<String> ccAddresses = new HashSet<String>();
	private Set<String> bccAddresses = new HashSet<String>();
	private Set<String> attachments = new HashSet<String>();
	private String subject;
	private String body;

	@Override
	public void send() {
		validateRequiredInfo();
		validateAddresses();
		sendMessage();
	}

	protected void validateRequiredInfo() {
		if (fromAddress == null) {
			throw new IncompleteEmailException("from adress cant be null");
		}
		if (toAddresses.isEmpty()) {
			throw new IncompleteEmailException("to adress cant be null");
		}
		if (subject == null) {
			throw new IncompleteEmailException("subject cant be null");
		}
		if (body == null) {
			throw new IncompleteEmailException("body adress cant be null");
		}
	}

	protected void sendMessage() {
		try {
			postalService.send(this);
		} catch (Exception e) {
			throw new EmailTransportException("Email could not be sent: " + e.getMessage(), e);
		}
	}

	protected EmailBuilder validateAddresses() {
		if (!emailAddressValidator.validate(fromAddress)) {
			throw new InvalidEmailAddressException("from : " + fromAddress);
		}
		for (String email : toAddresses) {
			if (!emailAddressValidator.validate(email)) {
				throw new InvalidEmailAddressException("to : " + toAddresses);
			}
		}
		return this;
	}

	@Override
	public String getFromAddress() {
		return fromAddress;
	}

	@Override
	public Set<String> getToAddress() {
		return toAddresses;

	}

	@Override
	public Set<String> getCcAddress() {
		return ccAddresses;

	}

	@Override
	public Set<String> getBccAddress() {
		return bccAddresses;

	}

	@Override
	public Set<String> getAttachments() {
		return attachments;

	}

	@Override
	public String getSubject() {
		return subject;

	}

	@Override
	public String getBody() {
		return body;

	}

	@Override
	public EmailBuilder from(String address) {
		this.fromAddress = address;
		return this;
	}

	@Override
	public EmailBuilder to(String... addresses) {
		for (int i = 0; i < addresses.length; i++) {
			this.toAddresses.add(addresses[i]);
		}
		return this;

	}

	@Override
	public EmailBuilder cc(String... addresses) {
		for (int i = 0; i < addresses.length; i++) {
			this.ccAddresses.add(addresses[i]);
		}
		return this;
	}

	@Override
	public EmailBuilder bcc(String... addresses) {
		for (int i = 0; i < addresses.length; i++) {
			this.bccAddresses.add(addresses[i]);
		}
		return this;
	}

	@Override
	public EmailBuilder withSubject(String subject) {
		this.subject = subject;
		return this;
	}

	@Override
	public EmailBuilder withBody(String body) {
		this.body = body;
		return this;
	}

	@Override
	public EmailBuilder withAttachment(String... attachmeant) {
		for (int i = 0; i < attachmeant.length; i++) {
			this.attachments.add(attachmeant[i]);
		}
		return this;
	}

	public static void setEailAddressValidator(EmailAddressValidator emailAddressValidator) {
		EmailMessage.emailAddressValidator = emailAddressValidator;
	}

	public static void setPostalService(PostalService postalService) {
		EmailMessage.postalService = postalService;
	}

}
