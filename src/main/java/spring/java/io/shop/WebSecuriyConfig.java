package spring.java.io.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import spring.java.io.shop.auth.AuthTokenFilter;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecuriyConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private AuthenticationEntryPoint unauthorizedHandler;
	
	@Bean
	public AuthTokenFilter authenticationTokenFilterBean() throws Exception{
		return new AuthTokenFilter();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
		.authorizeRequests()
		.antMatchers(
				HttpMethod.GET,
				"/",
				"/upload",
				"/*.html",
				"/**/*.css",
				"/**/*.js",
                "/**/*.png",
                "/**/*.gif",
                "/**/*.woff",
                "/**/*.woff2",
                "/**/*.tff",
                "/swagger-resources",
                "/configuration/ui",
                "/configuration/security"
				).permitAll()
		.antMatchers(HttpMethod.OPTIONS,"api/v1/**").permitAll()
		.antMatchers("api/v1/{companyId}/auth/admin/login").permitAll()
		.antMatchers("api/v1/1/products/**").permitAll()
		.antMatchers("api/v1/1/users/**").permitAll()
		.antMatchers("api/v1/1/categories/**").permitAll()
		.antMatchers("api/v1/1/orders/**").permitAll()
		.antMatchers("api/v1/1/auth/session/data").permitAll()
		.anyRequest().authenticated();
		
		http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
		http.headers().cacheControl();
	}}
