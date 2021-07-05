package spring.java.io.shop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import spring.java.io.shop.database.model.Review;

@Repository
public interface ReviewRepository extends PagingAndSortingRepository<Review, Integer>{

	@Query("SELECT r FROM Review r WHERE r.productId=:productId")
	Page<Review> findByProdcutId(@Param("productId")long productId,Pageable pageable);
}
