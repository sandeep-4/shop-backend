package spring.java.io.shop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class RestControllerAspect {

	@Before("execution(public * spring.java.io.shop.api.rest.*Controller.*(..))")
	public void logBeforeRestCall(JoinPoint pjp) throws Throwable{
		System.out.println("AOP before Rest::"+pjp);
	}
}
