package spring.java.io.shop.service.category;

import java.util.List;

import org.springframework.data.domain.Page;

import spring.java.io.shop.database.model.Category;

public interface CategoryService {

	Category saveOrUpdate(Category category);
	
	List<Category> saveOrUpdate(List<Category> categories);
	
	Category getActiveById(long categoryId);
	
	List<Category> getAllActiveByIdsAndCompanyId(List<Long> categoryIds,long companyId);
	
	Page<Category> getAllActiveWithFilterSerachSort(long companyId,String keyword,int pagenumber,int paseSize,int sortCase,boolean ascSort);
	
	
}
