package spring.java.io.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import spring.java.io.shop.database.model.UserAddress;

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress, Long>{
UserAddress findByUserIdAndStatus(String userId,int status);
UserAddress findByAddressIdAndStatus(long addressId,int status);
}
