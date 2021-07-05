package spring.java.io.shop.service.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import spring.java.io.shop.database.model.OrderDetail;
import spring.java.io.shop.repository.OrderDetailRepository;
import spring.java.io.shop.service.AbstractBaseService;

@Component
public class OrderDetailImpl extends AbstractBaseService implements OrderDetailService{

	@Autowired
	OrderDetailRepository orderDetailRepository;
	
	@Override
	public OrderDetail saveOrUpdate(OrderDetail orderDetail) {
		return orderDetailRepository.save(orderDetail);
	}

	@Override
	public List<OrderDetail> getListOrderDetail(Long orderId) {
		return orderDetailRepository.findAllByOrderId(orderId);
	}

}
