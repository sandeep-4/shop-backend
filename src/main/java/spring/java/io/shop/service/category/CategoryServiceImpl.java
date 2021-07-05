package spring.java.io.shop.service.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import spring.java.io.shop.database.model.Category;
import spring.java.io.shop.repository.CategoryRepository;
import spring.java.io.shop.repository.specification.CategorySpecifications;
import spring.java.io.shop.service.AbstractBaseService;

@Service
public class CategoryServiceImpl extends AbstractBaseService implements CategoryService{

	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	private CategorySpecifications categorySpecifications;
	
	
	@Override
	public Category saveOrUpdate(Category category) {
		return categoryRepository.save(category);
	}

	@Override
	public List<Category> saveOrUpdate(List<Category> categories) {
		return (List<Category>) categoryRepository.saveAll(categories);
	}

	@Override
	public Category getActiveById(long categoryId) {
		return categoryRepository.findByCategoryIdAndStatus(categoryId, 1);
	}

	@Override
	public List<Category> getAllActiveByIdsAndCompanyId(List<Long> categoryIds, long companyId) {
		return categoryRepository.findAllByCategoryIdInAndCompanyIdAndStatus(categoryIds, companyId, 1);
	}

	@Override
	public Page<Category> getAllActiveWithFilterSerachSort(long companyId, String keyword, int pagenumber, int paseSize,
			int sortCase, boolean ascSort) {
		Pageable pageable=PageRequest.of(pagenumber-1, paseSize);
		
		Specification spec=categorySpecifications.doFilterSearch(companyId, keyword, sortCase, ascSort);
		return categoryRepository.findAll(pageable);
		
	}

}
