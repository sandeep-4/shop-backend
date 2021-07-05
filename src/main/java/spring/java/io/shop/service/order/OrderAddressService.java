package spring.java.io.shop.service.order;

import spring.java.io.shop.database.model.OrderAddress;

public interface OrderAddressService {

	public OrderAddress saveOrUpdate(OrderAddress aorderAddress);
	public OrderAddress getOrderAddressByOrderId(Long orderId);
}
