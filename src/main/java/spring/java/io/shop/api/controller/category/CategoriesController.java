package spring.java.io.shop.api.controller.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import spring.java.io.shop.api.APIName;
import spring.java.io.shop.api.controller.AbstractBaseController;
import spring.java.io.shop.api.request.model.CreateCategoryRequestModel;
import spring.java.io.shop.api.request.model.UpdateCategoryRequestModel;
import spring.java.io.shop.api.response.model.APIResponse;
import spring.java.io.shop.api.response.util.APIStatus;
import spring.java.io.shop.database.model.Category;
import spring.java.io.shop.database.model.Company;
import spring.java.io.shop.exception.ApplicationException;
import spring.java.io.shop.service.CompanyService;
import spring.java.io.shop.service.category.CategoryService;

@RestController
@RequestMapping(APIName.CATEGORIES_API)
public class CategoriesController extends AbstractBaseController {

	@Autowired
	CategoryService categoryService;

	@Autowired
	CompanyService companyService;

	@RequestMapping(path = APIName.CATEGORIES_ADD, method = RequestMethod.POST)
	public ResponseEntity<APIResponse> addCategory(@PathVariable(value = "company_id") long companyId,
			@RequestBody CreateCategoryRequestModel categoryModel) {

		Company company = companyService.findByCompanyId(companyId);
		if (company == null) {
			throw new ApplicationException(APIStatus.INVALID_PARAMETER);
		}
		Category category = new Category();
		category.setCompanyId(companyId);
		category.setParentId(categoryModel.getParentId());
		category.setName(categoryModel.getName());
		category.setStatus(1);
		category.setPosition(categoryModel.getPosition());
		category.setDescription(categoryModel.getDescription());

		categoryService.saveOrUpdate(category);
		return responseUtil.sucessResponse(category);
	}

	@RequestMapping(path = APIName.CATEGORIES_UPDATE, method = RequestMethod.POST)
	public ResponseEntity<APIResponse> updateCategory(@PathVariable(value = "company_id") long companyId,
			@RequestBody UpdateCategoryRequestModel categoryModel) {
		Company company = companyService.findByCompanyId(companyId);

		if (company == null) {
			throw new ApplicationException(APIStatus.INVALID_PARAMETER);
		}

		Category category = categoryService.getActiveById(categoryModel.getId());

		if (category == null || companyId != category.getCompanyId()) {
			throw new ApplicationException(APIStatus.INVALID_PARAMETER);
		}
		if (categoryModel.getParentId() != null) {
			Category parent = categoryService.getActiveById(category.getParentId());
			if (parent != null && parent.getCompanyId().equals(category.getCompanyId())) {
				category.setParentId(categoryModel.getParentId());

			} else {
				throw new ApplicationException(APIStatus.INVALID_PARAMETER);
			}
		}
		category.setCategoryId(categoryModel.getId());
		category.setPosition(categoryModel.getPosition());
		category.setDescription(categoryModel.getDescription());

		categoryService.saveOrUpdate(category);
		return responseUtil.sucessResponse(category);
	}

	@RequestMapping(value = APIName.CATEGORIES_DELETE, method = RequestMethod.POST, produces = APIName.CHARSET)
	public ResponseEntity<APIResponse> deleteCategory(@PathVariable(value = "company_id") long companyId,
			@RequestBody List<Long> categoryIds) {
		Company company = companyService.findByCompanyId(companyId);
		if (company == null) {
			throw new ApplicationException(APIStatus.INVALID_PARAMETER);
		}

		List<Category> categoryList = categoryService.getAllActiveByIdsAndCompanyId(categoryIds, companyId);
		if (categoryList.isEmpty()) {
			throw new ApplicationException(APIStatus.INVALID_PARAMETER);
		}

		categoryList.forEach(category -> category.setStatus(0));

		categoryService.saveOrUpdate(categoryList);
		return responseUtil.sucessResponse(null);
	}

	@RequestMapping(value = APIName.CATEGORIES_LIST, method = RequestMethod.GET, produces = APIName.CHARSET)
	public ResponseEntity<APIResponse> getCategories(@PathVariable(value = "company_id") long companyId,
			@RequestParam(value = "search_key", required = false) String search,
			@RequestParam(value = "sort_case", defaultValue = "1", required = false) int sortCase,
			@RequestParam(value = "asc_sort", defaultValue = "true", required = false) boolean ascSort,
			@RequestParam(value = "page_size", defaultValue = "10", required = false) int pageSize,
			@RequestParam(value = "page_number", defaultValue = "1", required = false) int pageNumber) {
		Page<Category> categoryPage = categoryService.getAllActiveWithFilterSerachSort(companyId, search, pageNumber,
				pageSize, sortCase, ascSort);
		return responseUtil.sucessResponse(categoryPage);
	}

	@RequestMapping(value = APIName.CATEGORIES_DETAIL, method = RequestMethod.GET, produces = APIName.CHARSET)
	public ResponseEntity<APIResponse> deleteCategory(@PathVariable(value = "company_id") long companyId,
			@PathVariable(value = "category_id") long categoryId) {
		Company company = companyService.findByCompanyId(companyId);
		if (company == null) {
			throw new ApplicationException(APIStatus.INVALID_PARAMETER);
		}
		Category category = categoryService.getActiveById(categoryId);
		if (category == null || category.getCompanyId() != companyId) {
			throw new ApplicationException(APIStatus.INVALID_PARAMETER);

		}
		return responseUtil.sucessResponse(category);
	}
}
