package spring.java.io.shop.service.order;

import spring.java.io.shop.database.model.OrderPayment;

public interface OrderPaymentService {

	public OrderPayment getOrderPaymentByOrderId(Long orderId);
}
