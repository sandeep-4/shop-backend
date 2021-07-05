package spring.java.io.shop.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RestErrorInfo {

	public String detail;
	
	public String message;
	
	public RestErrorInfo(Exception ex,String detail) {
		this.message=ex.getLocalizedMessage();
		this.detail=detail;
	}
	
}
