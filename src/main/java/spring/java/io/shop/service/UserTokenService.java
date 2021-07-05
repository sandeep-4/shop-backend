package spring.java.io.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.java.io.shop.database.model.UserToken;
import spring.java.io.shop.repository.UserTokenRepository;

@Service
public class UserTokenService {

	@Autowired
	private UserTokenRepository userTokenRepository;
	
	public UserToken save(UserToken userToken) {
		return userTokenRepository.save(userToken);
	}
	
	public UserToken getTokenById(String token) {
		return userTokenRepository.findByToken(token);
	}
	
	public void invalidateToken(UserToken userToken) {
		userTokenRepository.delete(userToken);
	}
}
 