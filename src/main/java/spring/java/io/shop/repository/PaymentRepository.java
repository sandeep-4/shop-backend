package spring.java.io.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import spring.java.io.shop.database.model.Payment;
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>{

	Payment findByPaymentId(Long paymentId);
}
