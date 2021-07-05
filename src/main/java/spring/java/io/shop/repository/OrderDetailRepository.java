package spring.java.io.shop.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import spring.java.io.shop.database.model.OrderDetail;

@Repository
public interface OrderDetailRepository extends PagingAndSortingRepository<OrderDetail, Long>{
	List<OrderDetail> findAllByOrderId(Long orderId);
}
