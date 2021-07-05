package spring.java.io.shop.service.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import spring.java.io.shop.database.model.Review;
import spring.java.io.shop.repository.ReviewRepository;

public class ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;
	
	public Page<Review> findByProductId(long productId,int pageNumber,int pageSize){
		return reviewRepository.findByProdcutId(productId, PageRequest.of(pageNumber, pageSize));
	}
	
	public Review save(Review review) {
		return reviewRepository.save(review);
	}
}
