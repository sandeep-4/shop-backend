package spring.java.io.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import spring.java.io.shop.database.model.ProductAttributeDetail;

@Repository
public interface ProductAttributeDetailRepository extends JpaRepository<ProductAttributeDetail, Long>{

	@Query("Select pad FROM ProductAttributeDetail pad WHERE pad.productId=:productId")
	Iterable<ProductAttributeDetail> findAllByProductId(@Param("productId") long productId);
	
    @Query("SELECT pad FROM ProductAttributeDetail pad WHERE pad.productId = :productId AND pad.attributeId = :attributeId")
	ProductAttributeDetail findByProductIdAndAttributeId(@Param("productId")long productId,@Param("attributeId")long attributeId);
}
