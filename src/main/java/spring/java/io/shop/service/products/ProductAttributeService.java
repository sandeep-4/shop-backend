package spring.java.io.shop.service.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.java.io.shop.database.model.ProductAttribute;
import spring.java.io.shop.repository.ProductAttributeRepository;


public class ProductAttributeService {

	@Autowired
	private ProductAttributeRepository productAttributeRepository;
	
	public Iterable<ProductAttribute> findAll(){
		return productAttributeRepository.findAll();
	}
}
