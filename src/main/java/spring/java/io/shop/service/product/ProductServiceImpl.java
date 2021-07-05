package spring.java.io.shop.service.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import spring.java.io.shop.database.model.Product;
import spring.java.io.shop.database.model.ProductCategory;
import spring.java.io.shop.repository.ProductRepository;
import spring.java.io.shop.repository.specification.ProductSpecification;

@Service
public class ProductServiceImpl implements ProductService{
	
	@Autowired
	private ProductRepository productRepository;

	@Override
	public Iterable<Product> findAllProduct() {
		return productRepository.findAll();
	}

	@Override
	public Product getProductById(long companyId, long productId) {
		return productRepository.findByProductId(productId);
	}

	@Override
	public Page<Product> getByCompanyId(long copanyId, int pageNumber, int pazeSize) {
		return productRepository.findByCompanyId(copanyId, PageRequest.of(pageNumber, pazeSize));
	}

	@Override
	public Page<Product> getByCompanyIdAndCategoryId(long companyId, long categoryId, int pagenumber, int pageSize) {
//		return productRepository.findByCategoryId(companyId,categoryId,PageRequest.of(pagenumber, pageSize));
		return null;
	}

	@Override
	public Page<Product> doFilterSearchSortPagingProduct(long comId, long catId, long attrId, String searchKey,
			double mnPrice, double mxPrice, int minRank, int maxRank, int sortKey, boolean isAscSort, int pSize,
			int pNumber) {
	return productRepository.findAll(new ProductSpecification(comId, catId, attrId, searchKey, mnPrice, mxPrice, minRank, maxRank, sortKey, isAscSort),PageRequest.of(pNumber, pSize));
	
	}

	@Override
	public Iterable<Product> getProductsById(long companyId, List<Long> productIds) {
		return productRepository.findByProductIds(companyId, productIds);
	}

	@Override
	public Product save(Product product) {
		product.setProductId(null);
		return productRepository.save(product);
	}

	@Override
	public Product update(Product product) {
	return productRepository.save(product);
	}

	@Override
	public void saveProductCategory(ProductCategory product) {

//		productRepository.saveProductCategory(product.getProductId(),product.getCategoryId());
	}

	@Override
	public void deleteProductCategory(ProductCategory product) {
//		productRepository.deleteProductCategory(product.getProductId());
	}

}
