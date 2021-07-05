package spring.java.io.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import spring.java.io.shop.database.model.User;
import spring.java.io.shop.database.model.UserToken;
import spring.java.io.shop.repository.UserRepositoy;
import spring.java.io.shop.repository.UserTokenRepository;
import spring.java.io.shop.repository.specification.UserSpecification;

@Service
public class UserService {

	@Autowired
	private UserRepositoy userRepository;
	
	@Autowired
	private UserTokenRepository userTokenRepository;
	
	public User getUserByEmail(String email,long companyId,int status) {
		return userRepository.findByUserIdAndCompanyIdAndStatus(email, companyId, status);
	}
	
	public User save(User users) {
		return userRepository.save(users);
	}
	
	public User getUserByUserIdAndComIdAndStatus(String userId,Long companyId,int status) {
		return userRepository.findByUserIdAndCompanyIdAndStatus(userId, companyId, status);
	}
	
	public User getUserByActivationCode(String token) {
		UserToken userToken=userTokenRepository.findByToken(token);
		if(userToken!=null) {
//			return userRepository.findOne(userToken.getUserId());
			return userRepository.findByUserId(userToken.getUserId());
		}
		else {
			return null;
		}
	}
	
	public Page<User> doFilterSearchSortPaginUser(String userId,long companyId,String serachKey,int sortKey,boolean isAscSort,int pSize,int pNumber){
		return userRepository.findAll(new UserSpecification(userId,companyId, serachKey, sortKey, isAscSort),PageRequest.of(pNumber, pSize));
	}
	
}
