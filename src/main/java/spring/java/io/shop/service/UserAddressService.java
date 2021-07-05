package spring.java.io.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.java.io.shop.database.model.UserAddress;
import spring.java.io.shop.repository.UserAddressRepository;

@Service
public class UserAddressService {

	@Autowired
	private UserAddressRepository userAddressRepository;
	
	public UserAddress save(UserAddress userAddress) {
		return userAddressRepository.save(userAddress);
	}
	
	public UserAddress getAddressByUSerIdANdStatus(String userId,int status) {
		return userAddressRepository.findByUserIdAndStatus(userId, status);
	}
}
