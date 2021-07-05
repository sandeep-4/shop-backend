package spring.java.io.shop.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import spring.java.io.shop.database.model.Orders;

@Repository
public interface OrdersRepository extends PagingAndSortingRepository<Orders, Long>,JpaSpecificationExecutor<Orders>{

	@Query("SELECT pad from Orders pad WHERE pad.companyId=:companyId")
	Page<Orders> findAllByCompanyId(@Param("companyId")long companyId,Pageable pageable);
	
	Orders findOneByIdAndCompanyId(Long orderId,Long companyId);
}
