package spring.java.io.shop.api;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import spring.java.io.shop.api.response.util.APIStatus;
import spring.java.io.shop.auth.AuthUser;
import spring.java.io.shop.auth.service.CustomUserAuthService;
import spring.java.io.shop.configs.AppConfig;
import spring.java.io.shop.database.model.User;
import spring.java.io.shop.exception.ApplicationException;
import spring.java.io.shop.tracelogged.EventLogManager;
import spring.java.io.shop.util.Constant;

public abstract class AbstractBaseAPI {

	@Autowired
	private CustomUserAuthService userDetailService;

	Gson gson = new GsonBuilder().serializeNulls().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
			.setDateFormat(Constant.API_FORMAT_DATE).create();
	
	public final static ObjectMapper mapper =new ObjectMapper();
	
	
	static {
		mapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES)
		.setSerializationInclusion(JsonInclude.Include.ALWAYS)
		.setDateFormat(new SimpleDateFormat(Constant.API_FORMAT_DATE));
	}
	
	@Autowired
	AppConfig appConfig;
	
	public final static EventLogManager logger=EventLogManager.getInstance();
	
	
	protected String writeObjectToJson(Object obj) {
		try {
			return mapper.writeValueAsString(obj);
		} catch (Exception e) {
		throw new ApplicationException(e.getCause());
		}
	}
	
	protected String writeObjectToJsonRemoveNullProperty(Object obj) throws ApplicationException{
		try {
			mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
			String result=mapper.writeValueAsString(obj);
			mapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
			return result;
		} catch (Exception e) {
			throw new ApplicationException(e.getCause());
		}
	}
	
//	public StatusResonse statusResponse=null;
	
//get current auth user
	
//	public User getCurrentUser() {
//		HttpServletRequest request=((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
//		String authToken=request.getParameter("auth_token");
//		User currentUser=userSessionDao.getUSerBySessionID(authToken);
//		if(currentUser==null) {
//			throw new ApplicationException(APIStatus.INVALID_PARAMETER);
//		}
//		return currentUser;
//	
//	}
	
	public AuthUser getAuthUserFromSession(HttpServletRequest request) {
		String authToken=request.getHeader(Constant.HEADER_TOKEN);
		AuthUser user=userDetailService.loadUserByAcessToken(authToken);
		return user;
	}
	
	
	
	
	
	
	
	
	
	
	

}
