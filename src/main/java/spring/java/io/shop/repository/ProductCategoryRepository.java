package spring.java.io.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import spring.java.io.shop.database.model.ProductCategory;
import spring.java.io.shop.database.model.ProductCategoryId;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, ProductCategoryId>{

	Iterable<ProductCategory> getById(ProductCategoryId productId);
	
	@Query("SELECT p FROM ProductCategory p WHERE p.id.productId=:productId")
	List<ProductCategory> getProCateByProductId(@Param("productId")long productId); 
}
