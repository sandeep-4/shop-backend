package spring.java.io.shop.auth;

import spring.java.io.shop.database.model.User;

public interface AuthUserFactory {

	AuthUser createAuthUser(User user);
}
