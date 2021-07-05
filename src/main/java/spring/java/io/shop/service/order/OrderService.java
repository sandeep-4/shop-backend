package spring.java.io.shop.service.order;

import org.springframework.data.domain.Page;

import spring.java.io.shop.api.request.model.OrdersRequestModel;
import spring.java.io.shop.database.model.Orders;

public interface OrderService {

	public Page<Orders> doPagingOrders(OrdersRequestModel oordersRequestModel,Long companyId);
	

	public Orders getOrderByOrderIdAndCompanyID(Long orderId,Long companyId);
	
	public void updateStatusOrder(Orders order);
	
	
}
