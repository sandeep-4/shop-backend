package spring.java.io.shop.auth;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import spring.java.io.shop.auth.service.CustomUserAuthService;
import spring.java.io.shop.exception.ApplicationException;
import spring.java.io.shop.util.Constant;

@Component
public class AuthTokenFilter extends OncePerRequestFilter{

	private final Log LOGGER=LogFactory.getLog(this.getClass());
	
	@Autowired
	private CustomUserAuthService userAuthService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		
		String authToken=request.getHeader(Constant.HEADER_TOKEN);
		if(authToken !=null) {
			try {
				AuthUser user=userAuthService.loadUserByAcessToken(authToken);
				if(user!=null) {
					UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(user, null,user.getAuthorities());
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authentication);
					
				}
			} catch (ApplicationException ex) {
				LOGGER.debug("doFilterInternal",ex);
			}
		}
		
		 // Allow cross domain 
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
        response.setHeader("Access-Control-Max-Age", "3600");
        // Allow set custom header token
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, x-requested-with, X-Custom-Header, X-Access-Token");

        chain.doFilter(request, response);
	}

}
