package spring.java.io.shop.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import spring.java.io.shop.api.response.util.APIStatus;
import spring.java.io.shop.auth.AuthUser;
import spring.java.io.shop.database.model.UserToken;
import spring.java.io.shop.exception.ApplicationException;
import spring.java.io.shop.repository.UserTokenRepository;

@Service
public class AuthUserDetailsServiceImpl implements CustomUserAuthService{

	Gson gson=new Gson();
	
	@Autowired
	UserTokenRepository userTokenRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// dont implement
		return null;
	}

	@Override
	public AuthUser loadUserByAcessToken(String token) {
//		UserToken session=userTokenRepository.findOne(token);
		UserToken session=userTokenRepository.findByToken(token);

		if(session!=null) {
			if(session.getSessionData() !=null && !"".equals(session.getSessionData())) {
				AuthUser authUser=gson.fromJson(session.getSessionData(), AuthUser.class);
				return authUser;
			}else {
				throw new ApplicationException(APIStatus.ERR_SESSION_DATA_INVALID);
			}
		}else {
			throw new ApplicationException(APIStatus.ERR_SESSION_NOT_FOUND);
		}
	}

	
}
