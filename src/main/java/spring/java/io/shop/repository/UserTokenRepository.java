package spring.java.io.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import spring.java.io.shop.database.model.UserToken;

@Repository
public interface UserTokenRepository extends JpaRepository<UserToken, String>{
UserToken findByToken(String token);
}
