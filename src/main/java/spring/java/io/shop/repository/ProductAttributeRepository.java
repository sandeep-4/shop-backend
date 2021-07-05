package spring.java.io.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import spring.java.io.shop.database.model.ProductAttribute;

@Repository
public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, Long>{
	
    @Query("SELECT pad FROM ProductAttribute pad WHERE pad.attributeId = :attributeId")
	Iterable<ProductAttribute> findAllProductByAttribute(@Param("attributeId")long attributeId);
}
