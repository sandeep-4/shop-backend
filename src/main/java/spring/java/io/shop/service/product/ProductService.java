package spring.java.io.shop.service.product;

import java.util.List;

import org.springframework.data.domain.Page;

import spring.java.io.shop.database.model.Product;
import spring.java.io.shop.database.model.ProductCategory;

public interface ProductService {

	Iterable<Product> findAllProduct();
	
	Product getProductById(long companyId,long productId);
	
	Page<Product> getByCompanyId(long copanyId,int pageNumber,int pazeSize);
	
	Page<Product> getByCompanyIdAndCategoryId(long companyId,long categoryId,int pagenumber,int pageSize);
	
	Page<Product> doFilterSearchSortPagingProduct(long comId,long catId,long attrId,String searchKey,double mnPrice,double mxPrice,int minRank,int maxRank,int sortKey,
			boolean isAscSort,int pSize,int pNumber);
	
	Iterable<Product> getProductsById(long companyId,List<Long> productIds);
	
	Product save(Product product);
	
	Product update(Product product);
	
	void saveProductCategory(ProductCategory product);
	
	void deleteProductCategory(ProductCategory product);
	
	
	
	
}
