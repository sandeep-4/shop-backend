package spring.java.io.shop.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.java.io.shop.database.model.User;
import spring.java.io.shop.repository.RoleRepository;
import spring.java.io.shop.util.Constant;

@Service
public class AuthUserFactoryImpl implements AuthUserFactory{
	
	@Autowired
	RoleRepository roleRepository;

	@Override
	public AuthUser createAuthUser(User user) {
		return new AuthUser(
				user.getUserId(),
				user.getEmail(),
				user.getPasswordHash(),
				getUserRoleString(user.getRoleId()),
				user.getFirstName(),
				user.getLastName(),
				user.getStatus()==Constant.STATUS.ACTIVE_STATUS.getValue()
				);
	}

	
	private String getUserRoleString(int roleId) {
		try {
		return roleRepository.findByRoleId(roleId).getName();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
