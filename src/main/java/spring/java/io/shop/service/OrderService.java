package spring.java.io.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import spring.java.io.shop.api.request.model.OrdersRequestModel;
import spring.java.io.shop.database.model.Orders;
import spring.java.io.shop.repository.OrdersRepository;

@Service
public class OrderService {

	@Autowired
	private OrdersRepository ordersRepository;
	
	public Orders save(Orders orders) {
		return ordersRepository.save(orders);
	}
	
	public Page<Orders> findAllByCompanyId(Long companyId,int pageNumber,int pageSize){
		return ordersRepository.findAllByCompanyId(companyId,PageRequest.of(pageNumber,pageSize));
	}


}
