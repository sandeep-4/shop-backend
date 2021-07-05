package spring.java.io.shop.service.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import spring.java.io.shop.database.model.OrderDetail;
import spring.java.io.shop.database.model.OrderPayment;
import spring.java.io.shop.repository.OrderDetailRepository;
import spring.java.io.shop.repository.OrderPaymentRepository;
import spring.java.io.shop.service.AbstractBaseService;
import spring.java.io.shop.util.Constant;

@Component
public class OrderPaymentImpl extends AbstractBaseService implements OrderPaymentService {

	@Autowired
	OrderPaymentRepository orderPaymentRepository;

	@Override
	public OrderPayment getOrderPaymentByOrderId(Long orderId) {
		try {
			return orderPaymentRepository.findOneByOrderIdAndStatus(orderId, Constant.STATUS.ACTIVE_STATUS.getValue());
		} catch (Exception e) {
			return null;
		}
	}

}
