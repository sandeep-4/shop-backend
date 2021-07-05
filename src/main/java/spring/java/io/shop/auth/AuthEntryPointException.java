package spring.java.io.shop.auth;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import spring.java.io.shop.api.response.model.StatusResponse;
import spring.java.io.shop.api.response.util.APIStatus;

@Component
public class AuthEntryPointException implements AuthenticationEntryPoint,Serializable{

	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	ObjectMapper objectMapper;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType("application/json");
		response.getWriter().write(objectMapper.writeValueAsString(new StatusResponse(APIStatus.ERR_UNAUTHORIZED)));
	}

}
