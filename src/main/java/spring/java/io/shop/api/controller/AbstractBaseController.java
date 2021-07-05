package spring.java.io.shop.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import spring.java.io.shop.api.response.util.ResponseUtil;
import spring.java.io.shop.auth.AuthUser;
import spring.java.io.shop.auth.service.CustomUserAuthService;
import spring.java.io.shop.configs.AppConfig;
import spring.java.io.shop.util.Constant;

public class AbstractBaseController {

	@Autowired
	private CustomUserAuthService userDetailService;
	
	@Autowired
	AppConfig appConfig;
	
	@Autowired
	protected ResponseUtil responseUtil;
	
	public AuthUser getAuthUserFromSession(HttpServletRequest request) {
		String authToken=request.getHeader(Constant.HEADER_TOKEN);
		
		AuthUser user=userDetailService.loadUserByAcessToken(authToken);
		return user;
	}
	
}
