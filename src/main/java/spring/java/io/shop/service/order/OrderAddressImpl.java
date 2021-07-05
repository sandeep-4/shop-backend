package spring.java.io.shop.service.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import spring.java.io.shop.database.model.OrderAddress;
import spring.java.io.shop.repository.OrderAddressRepository;
import spring.java.io.shop.service.AbstractBaseService;

@Component
public class OrderAddressImpl extends AbstractBaseService implements OrderAddressService{
	
	@Autowired
	OrderAddressRepository OrderAddressRepository;

	@Override
	public OrderAddress saveOrUpdate(OrderAddress orderAddress) {
	return OrderAddressRepository.save(orderAddress);
		
	}

	@Override
	public OrderAddress getOrderAddressByOrderId(Long orderId) {
		return OrderAddressRepository.findOneByOrderId(orderId);
	}

}
