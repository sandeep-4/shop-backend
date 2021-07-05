package spring.java.io.shop.notification.email.transport;

import java.io.InputStream;
import java.util.Properties;

public class EmailTransportConfiguration {

	private static final String PROPERTIES_FILE = "fluent-mail-api.properties";
	private static final String KEY_SMTP_SERVER = "smtp.server";
	private static final String KEY_AUTH_REQUIRED = "auth.required";
	private static final String KEY_USE_SECURE_SMTP = "use.secure.smtp";
	private static final String KEY_USERNAME = "smtp.username";
	private static final String KEY_PASSWORD = "smtp.password";
	
	private static String smtpServer="";
	private static int smtpPort=25;
	private static boolean authenticationRequired=false;
	private static boolean useSecureSmtp=false;
	private static String username=null;
	private static String password=null;
 
	
	static{
		Properties properties=new Properties();
		
		String smtpServer=properties.getProperty(KEY_SMTP_SERVER);
		boolean authenticationRequired=Boolean.parseBoolean(KEY_AUTH_REQUIRED);
		boolean useSecureSmtp=Boolean.parseBoolean(KEY_USE_SECURE_SMTP);
		String username=properties.getProperty(KEY_USERNAME);
		String password=properties.getProperty(KEY_PASSWORD);
		
		configure(smtpServer,25,authenticationRequired,useSecureSmtp,username,password);
	}
	
	private static Properties loadProperties() {
		Properties properties=new Properties();
		
		InputStream inputStream=EmailTransportConfiguration.class.getResourceAsStream(PROPERTIES_FILE);
		
		if(inputStream==null) {
			inputStream=EmailTransportConfiguration.class.getResourceAsStream("/"+PROPERTIES_FILE);
		}
		try {
			properties.load(inputStream);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return properties;
		
	}
	
	public static void configure(String smtpServer) {
		configure(smtpServer,25,false,false,null,null);
	}
	
	
	public static void configure(String smtpServer,int smtpPort,boolean authenticationRequired,boolean useSecureSmtp,String username,String password) {
		EmailTransportConfiguration.smtpServer=smtpServer;
		EmailTransportConfiguration.smtpPort=smtpPort;
		EmailTransportConfiguration.authenticationRequired=authenticationRequired;
		EmailTransportConfiguration.useSecureSmtp=useSecureSmtp;
		EmailTransportConfiguration.username=username;
		EmailTransportConfiguration.password=password;

	}
	
	
	public static void configure(String smtpServer,int smtpPort,boolean useSecureSmtp) {
		EmailTransportConfiguration.smtpServer=smtpServer;
		EmailTransportConfiguration.smtpPort=smtpPort;
		EmailTransportConfiguration.useSecureSmtp=useSecureSmtp;
	}

	public static String getSmtpServer() {
		return smtpServer;
	}

	public static void setSmtpServer(String smtpServer) {
		EmailTransportConfiguration.smtpServer = smtpServer;
	}

	public static int getSmtpPort() {
		return smtpPort;
	}

	public static void setSmtpPort(int smtpPort) {
		EmailTransportConfiguration.smtpPort = smtpPort;
	}

	public static boolean isAuthenticationRequired() {
		return authenticationRequired;
	}

	public static void setAuthenticationRequired(boolean authenticationRequired) {
		EmailTransportConfiguration.authenticationRequired = authenticationRequired;
	}

	public static boolean isUseSecureSmtp() {
		return useSecureSmtp;
	}

	public static void setUseSecureSmtp(boolean useSecureSmtp) {
		EmailTransportConfiguration.useSecureSmtp = useSecureSmtp;
	}

	public static String getUsername() {
		return username;
	}

	public static void setUsername(String username) {
		EmailTransportConfiguration.username = username;
	}

	public static String getPassword() {
		return password;
	}

	public static void setPassword(String password) {
		EmailTransportConfiguration.password = password;
	}

	public static String getPropertiesFile() {
		return PROPERTIES_FILE;
	}

	public static String getKeySmtpServer() {
		return KEY_SMTP_SERVER;
	}

	public static String getKeyAuthRequired() {
		return KEY_AUTH_REQUIRED;
	}

	public static String getKeyUseSecureSmtp() {
		return KEY_USE_SECURE_SMTP;
	}

	public static String getKeyUsername() {
		return KEY_USERNAME;
	}

	public static String getKeyPassword() {
		return KEY_PASSWORD;
	}
	
	
	
	
	
}
