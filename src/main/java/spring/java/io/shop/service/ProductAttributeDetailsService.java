package spring.java.io.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.java.io.shop.database.model.ProductAttributeDetail;
import spring.java.io.shop.repository.ProductAttributeDetailRepository;

@Service
public class ProductAttributeDetailsService {

	@Autowired
	private ProductAttributeDetailRepository productAttributeDetailRepository;
	
	public Iterable<ProductAttributeDetail> findAllByProductId(long productId){
		return productAttributeDetailRepository.findAllByProductId(productId);
	}
	
	public ProductAttributeDetail findByProductIdAndAttributeId(long productId,long attributeId) {
		return productAttributeDetailRepository.findByProductIdAndAttributeId(productId, attributeId);
	}
}
