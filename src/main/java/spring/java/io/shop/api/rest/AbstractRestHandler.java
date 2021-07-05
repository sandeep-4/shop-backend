package spring.java.io.shop.api.rest;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import com.fasterxml.jackson.annotation.JsonInclude;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import spring.java.io.shop.exception.ApplicationException;
import spring.java.io.shop.exception.DataFormatException;
import spring.java.io.shop.exception.ResourceNotFoundException;
import spring.java.io.shop.model.RestErrorInfo;

public class AbstractRestHandler implements ApplicationEventPublisherAware{

	
	 protected final Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());
	    protected ApplicationEventPublisher eventPublisher;

	    protected static final String  DEFAULT_PAGE_SIZE = "100";
	    protected static final String DEFAULT_PAGE_NUM = "0";
	    
	    // Mapper object is used to convert object and etc...
	    public final static ObjectMapper mapper = new ObjectMapper();
	    // Set format
	    static {
	        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES)
	                // Set setting remove NULL property
	                .setSerializationInclusion(JsonInclude.Include.NON_NULL);
	                //.setSerializationInclusion(JsonInclude.Include.ALWAYS)
	                //.setDateFormat(new SimpleDateFormat(Constant.API_FORMAT_DATE));
	    }

	    @ResponseStatus(HttpStatus.BAD_REQUEST)
	    @ExceptionHandler(DataFormatException.class)
	    public
	    @ResponseBody
	    RestErrorInfo handleDataStoreException(DataFormatException ex, WebRequest request, HttpServletResponse response) {
	        log.info("Converting Data Store exception to RestResponse : " + ex.getMessage());

	        return new RestErrorInfo(ex, "You messed up.");
	    }

	    @ResponseStatus(HttpStatus.NOT_FOUND)
	    @ExceptionHandler(ResourceNotFoundException.class)
	    public
	    @ResponseBody
	    RestErrorInfo handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request, HttpServletResponse response) {
	        log.info("ResourceNotFoundException handler:" + ex.getMessage());

	        return new RestErrorInfo(ex, "Sorry I couldn't find it.");
	    }

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {

		this.eventPublisher=applicationEventPublisher;
	}
	
	public static <T> T checkResourceFound(final T resource) {
		if(resource==null) {
            throw new ResourceNotFoundException("resource not found");
		}
		return resource;
	}

	public String writeObjectToJson(Object obj) {
		try {
			return mapper.writeValueAsString(obj);
		} catch (Exception e) {
            throw new ApplicationException(e.getCause());

		}
	}
	
	
}
