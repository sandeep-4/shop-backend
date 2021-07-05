package spring.java.io.shop.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import spring.java.io.shop.database.model.OrderPayment;

@Repository
public interface OrderPaymentRepository extends PagingAndSortingRepository<OrderPayment, Long>{
  OrderPayment findOneByOrderIdAndStatus(Long orderId, int status);

}
