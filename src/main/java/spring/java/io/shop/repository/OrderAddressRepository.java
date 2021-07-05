package spring.java.io.shop.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import spring.java.io.shop.database.model.OrderAddress;

@Repository
public interface OrderAddressRepository extends PagingAndSortingRepository<OrderAddress, Long>{

	OrderAddress findOneByOrderId(Long orderId);
}
