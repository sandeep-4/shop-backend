package spring.java.io.shop.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import spring.java.io.shop.database.model.Product;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Long>,JpaSpecificationExecutor<Product>{

	Page<Product> findByCompanyId(@Param("companyId") long companyId,Pageable pageable);
	
	Product findByProductId(long productId);
	

//  @Query("SELECT p FROM Product p, ProductCategory pc WHERE p.companyId = :companyId AND pc.categoryId = :categoryId AND pc.productId = p.productId")
//  Page<Product> findByCategoryId(@Param("companyId") long companyId, @Param("categoryId") long categoryId, Pageable pageable);

	@Query("SELECT p FROM Product p WHERE p.companyId=:companyId AND p.productId IN (:productIds)")
	Iterable<Product> findByProductIds(@Param("companyId")long companyId ,@Param("productIds") List<Long> productsIds);

	  
//  @Query("SELECT p FROM ProductCategory p WHERE p.productId = :productId")
//  Iterable<ProductCategory> findByProductId(@Param("productId") long productId);
//  
//  @Query("DELETE FROM ProductCategory p WHERE p.productId = :productId")
//  void deleteProductCategory(@Param("productId") long productId);
//  
//  @Query(value = "SELECT `category_id` FROM `product_categories` WHERE `product_id` = :productId", nativeQuery = true)
//  List<Object[]> findByProductId(@Param("productId") long productId);


}

