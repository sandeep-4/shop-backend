package spring.java.io.shop.service.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import spring.java.io.shop.api.request.model.OrdersRequestModel;
import spring.java.io.shop.database.model.Orders;
import spring.java.io.shop.repository.OrdersRepository;
import spring.java.io.shop.repository.specification.OrdersSpecification;
import spring.java.io.shop.service.AbstractBaseService;
@Component
public class OrderServiceImpl extends AbstractBaseService implements OrderService{


	@Autowired
	OrdersRepository ordersRepository;
	
	
	
	
	@Override
	public Orders getOrderByOrderIdAndCompanyID(Long orderId, Long companyId) {
		return ordersRepository.findOneByIdAndCompanyId(orderId, companyId);
	}

	@Override
	public void updateStatusOrder(Orders order) {
		ordersRepository.save(order);
	}

	@Override
	public Page<Orders> doPagingOrders(OrdersRequestModel ordersRequestModel, Long companyId) {
		Page<Orders> listOrders=(Page<Orders>) ordersRepository.findAll(new OrdersSpecification(companyId, ordersRequestModel.getSearchKey(), 0, false, 0));
		return listOrders;
	}



}
