package spring.java.io.shop.auth.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import spring.java.io.shop.auth.AuthUser;

public interface CustomUserAuthService extends UserDetailsService{

	AuthUser loadUserByAcessToken(String token);
}
