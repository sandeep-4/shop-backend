package spring.java.io.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import spring.java.io.shop.database.model.Category;

@Repository
public interface CategoryRepository extends PagingAndSortingRepository<Category, Long>,JpaSpecificationExecutor<Category>{

	Category findByCategoryId(@Param("categoryId")Long categoryId);
	Iterable<Category> findByCompanyId(@Param("companyId") Long companyId);
	Category findByCategoryIdAndStatus(long categoryId,int status);
	List<Category> findAllByCategoryIdInAndCompanyIdAndStatus(List<Long>categoryIds,long companyId,int status);
	Category findByParentId(long parentId);
}
