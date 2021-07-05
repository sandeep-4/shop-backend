package spring.java.io.shop.service.auth;

import spring.java.io.shop.database.model.User;
import spring.java.io.shop.database.model.UserToken;

public interface AuthService {

	public User getUserByEmailAndCompanyIdAndStatus(String email, Long companyId, int status);

	public UserToken createUserToken(User adminUser, boolean keepMeLogin);

	public User getUserByUserIdAndCompanyIdANdStatus(String userId, long companyId, int status);

	public UserToken getUserTokenById(String id);

	public void delteUserToken(UserToken userToken);
}
