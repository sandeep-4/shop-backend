package spring.java.io.shop.service.auth;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.java.io.shop.api.response.util.APIStatus;
import spring.java.io.shop.auth.AuthUser;
import spring.java.io.shop.auth.AuthUserFactory;
import spring.java.io.shop.database.model.User;
import spring.java.io.shop.database.model.UserToken;
import spring.java.io.shop.exception.ApplicationException;
import spring.java.io.shop.repository.UserRepositoy;
import spring.java.io.shop.repository.UserTokenRepository;
import spring.java.io.shop.service.AbstractBaseService;
import spring.java.io.shop.util.Constant;
import spring.java.io.shop.util.DateUtil;
import spring.java.io.shop.util.UniqueID;

@Service
public class AuthServiceImpl extends AbstractBaseService implements AuthService{
	
	@Autowired
	UserRepositoy userRepository;
	
	@Autowired
	private UserTokenRepository userTokenRepository;
	
	
	@Autowired
	private AuthUserFactory authUserFactory;

	@Override
	public User getUserByEmailAndCompanyIdAndStatus(String email, Long companyId, int status) {
	return userRepository.findByEmailAndCompanyIdAndStatus(email, companyId, Constant.USER_STATUS.ACTIVE.getStatus());
	}

	@Override
	public UserToken createUserToken(User userLogin, boolean keepMeLogin) {
		try {
			UserToken userToken=new UserToken();
			userToken.setToken(UniqueID.getUUID());
			userToken.setComapnyId(userLogin.getCompanyId());
			userToken.setUserId(userLogin.getUserId());
			Date currentDate=new Date();
			userToken.setLoginDate(DateUtil.convertToUTC(currentDate));
			Date expirationDate=keepMeLogin?new Date(currentDate.getTime()+Constant.DEFAULT_REMEMBER_LOGIN_MILISECONDS):new Date(currentDate.getTime()+Constant.DEFAULT_SESSION_TIME_OUT);
			userToken.setExpirationDate(DateUtil.convertToUTC(expirationDate));
			AuthUser authUser=authUserFactory.createAuthUser(userLogin);
			
			userToken.setSessionData(gson.toJson(authUser));
			userTokenRepository.save(userToken);
			return userToken;
			
		} catch (Exception e) {
			LOGGER.error("Error create user token",e);
			throw new ApplicationException(APIStatus.SQL_ERROR); 
		}
	}

	@Override
	public User getUserByUserIdAndCompanyIdANdStatus(String userId, long companyId, int status) {
		return userRepository.findByUserIdAndCompanyIdAndStatus(userId, companyId, Constant.USER_STATUS.ACTIVE.getStatus());
	}

	@Override
	public UserToken getUserTokenById(String id) {
		return userTokenRepository.getOne(id);
	}

	@Override
	public void delteUserToken(UserToken userToken) {
     userTokenRepository.delete(userToken);		
	}

}
