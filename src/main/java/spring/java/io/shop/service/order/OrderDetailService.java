package spring.java.io.shop.service.order;

import java.util.List;

import spring.java.io.shop.database.model.OrderDetail;

public interface OrderDetailService {

	public OrderDetail saveOrUpdate(OrderDetail orderDetail);
	public List<OrderDetail> getListOrderDetail(Long orderId);
	
}
