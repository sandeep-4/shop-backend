package spring.java.io.shop.api.product;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import spring.java.io.shop.api.APIName;
import spring.java.io.shop.api.controller.AbstractBaseController;
import spring.java.io.shop.api.request.model.CreateProductModel;
import spring.java.io.shop.api.request.model.ListProductModel;
import spring.java.io.shop.api.response.model.APIResponse;
import spring.java.io.shop.api.response.model.PagingResponseModel;
import spring.java.io.shop.api.response.util.APIStatus;
import spring.java.io.shop.database.model.Category;
import spring.java.io.shop.database.model.Product;
import spring.java.io.shop.database.model.ProductCategory;
import spring.java.io.shop.database.model.ProductCategoryId;
import spring.java.io.shop.exception.ApplicationException;
import spring.java.io.shop.repository.CategoryRepository;
import spring.java.io.shop.repository.ProductCategoryRepository;
import spring.java.io.shop.service.ProductAttributeDetailsService;
import spring.java.io.shop.service.product.ProductServiceImpl;
import spring.java.io.shop.util.Constant;

@Api(value = "products")
@RestController
@RequestMapping(APIName.PRODUCTS)
public class ProductAPI extends AbstractBaseController {

	@Autowired
	private ProductServiceImpl productService;

	@Autowired
	private ProductAttributeDetailsService productAttributeService;

	@Autowired
	private ProductCategoryRepository productCategoryRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@ApiOperation(value = "get product byId", notes = "")
	@RequestMapping(method = RequestMethod.GET, produces = APIName.CHARSET)
	public ResponseEntity<APIResponse> getAllProducts(@PathVariable("compantId") long compnayId,
			@RequestParam(required = false, defaultValue = Constant.DEFAULT_PAGE_NUMBER) Integer pageNumber,
			@RequestParam(required = false, defaultValue = Constant.DEFAULT_PAGE_SIZE) Integer pageSize) {
		Page<Product> products = productService.getByCompanyId(compnayId, pageNumber, pageSize);
		return responseUtil.sucessResponse(products.getContent());

	}

	@ApiOperation(value = "get Product by product id", notes = "")
	@RequestMapping(path = APIName.PRODUCT_BY_ID, method = RequestMethod.GET, produces = APIName.CHARSET)
	public ResponseEntity<APIResponse> getProductById(HttpServletRequest request, @PathVariable Long product_id,
			@PathVariable Long company_id) {
		Product p = productService.getProductById(company_id, product_id);
		if (p != null) {
			List<ProductCategory> listProductCate = productCategoryRepository.getProCateByProductId(product_id);
			List<Map<String, Object>> listCate = new ArrayList<Map<String, Object>>();

			for (ProductCategory result : listProductCate) {
				Map<String, Object> category = new HashMap<>();
				Category cate = categoryRepository.findByCategoryId(result.getId().getCategoryId());
				if (cate != null) {
					category.put("text", cate.getName());
					category.put("id", cate.getCategoryId());
				}
				listCate.add(category);
			}
			Map<String, Object> result = new HashMap();
			result.put("product", p);
			result.put("list_category", listCate);
			return responseUtil.sucessResponse(result);

		} else {
			throw new ApplicationException(APIStatus.GET_PRODUCT_ERROR);
		}

	}

	@ApiOperation(value = "get list product by product ids", notes = "")
	@RequestMapping(path = APIName.PRODUCT_BY_IDS, method = RequestMethod.POST, produces = APIName.CHARSET)
	public ResponseEntity<APIResponse> getListProductByIds(@PathVariable Long companyId,
			@RequestBody List<Long> productIds) {
		if (productIds != null && !productIds.isEmpty()) {
			List<Product> products = (List<Product>) productService.getProductsById(companyId, productIds);
			if (products != null) {
				return responseUtil.sucessResponse(products);
			} else {
				throw new ApplicationException(APIStatus.INVALID_PARAMETER);

			}
		} else {
			throw new ApplicationException(APIStatus.INVALID_PARAMETER);

		}
	}

	@ApiOperation(value = "get products by category id", notes = "")
	@RequestMapping(value = APIName.PRODUCTS_BY_CATEGORY, method = RequestMethod.GET, produces = APIName.CHARSET)
	public ResponseEntity<APIResponse> getProductByCategoryId(@PathVariable("companyId") Long companyId,
			@RequestParam Long categoryId,
			@RequestParam(required = false, defaultValue = Constant.DEFAULT_PAGE_NUMBER) Integer pageNumber,
			@RequestParam(required = false, defaultValue = Constant.DEFAULT_PAGE_SIZE) Integer pageSize) {

		Page<Product> products = productService.getByCompanyIdAndCategoryId(companyId, categoryId, pageNumber,
				pageSize);
		return responseUtil.sucessResponse(products.getContent());
	}

	@ApiOperation(value = "filter products to list", notes = "")
	@RequestMapping(value = APIName.PRODUCTS_FILTER_LIST, method = RequestMethod.POST, produces = APIName.CHARSET)
	public ResponseEntity<APIResponse> getProductFilterList(HttpServletRequest request,
			@RequestBody ListProductModel listProduct) {
		try {
			Page<Product> products = productService.doFilterSearchSortPagingProduct(listProduct.getCompanyId(),
					listProduct.getCategoryId(), listProduct.getAttributeId(), listProduct.getSearchKey(),
					listProduct.getMinPrice(), listProduct.getMaxPrice(), listProduct.getMinRank(),
					listProduct.getMaxRank(), listProduct.getSortCase(), listProduct.getAscSort(),
					listProduct.getPageSize(), listProduct.getPageNumber());

			PagingResponseModel finalRes = new PagingResponseModel(products.getContent(), products.getTotalElements(),
					products.getTotalPages(), products.getNumber());
			return responseUtil.sucessResponse(finalRes);
		} catch (Exception ex) {
			throw new ApplicationException(APIStatus.GET_LIST_PRODUCT_ERROR);
		}
	}

	@ApiOperation(value = "create product", notes = "")
	@RequestMapping(value = APIName.PRODUCT_CREATE, method = RequestMethod.POST, produces = APIName.CHARSET)
	public ResponseEntity<APIResponse> createProduct(HttpServletRequest request,
			@RequestBody CreateProductModel productRequest) {
		try {
			Product product = new Product();
			product.setBrowsingName(productRequest.getBrowsingName());
			product.setCompanyId(productRequest.getCompanyId());
			product.setCreatedOn(new Date());
			product.setDefaultImage(productRequest.getDefaultImage());
			product.setDescription(productRequest.getDescription());
			product.setStockControlled(productRequest.getIsStockControlled());
			product.setListPrice(productRequest.getListPrice());
			product.setName(productRequest.getName());
			product.setOverview(productRequest.getOverview());
			product.setQuantity(productRequest.getQuantity());
			product.setRank(productRequest.getRank());
			product.setSalePrice(productRequest.getSalePrice());
			product.setSku(productRequest.getSku());
			product.setStatus(Constant.STATUS.ACTIVE_STATUS.getValue());
			product.setUpdatedOn(new Date());

			productService.save(product);

			// creating category;
			for (long categoriesId : productRequest.getListCategoriesId()) {
				ProductCategoryId productCategoryId = new ProductCategoryId();
				ProductCategory productCategory = new ProductCategory();

				productCategoryId.setCategoryId(categoriesId);
				productCategoryId.setProductId(product.getProductId());
				productCategory.setId(productCategoryId);
				productCategoryRepository.save(productCategory);
			}
			return responseUtil.sucessResponse(product);

		} catch (Exception e) {
			throw new ApplicationException(APIStatus.CREATE_PRODUCT_ERROR);

		}

	}

	@ApiOperation(value = "delete product list", notes = "")
	@RequestMapping(value = APIName.PRODUCTS_DELETE, method = RequestMethod.POST, produces = APIName.CHARSET)
	public ResponseEntity<APIResponse> deleteProduct(HttpServletRequest request, @RequestBody List<Long> ids,
			@PathVariable Long company_id) {
		try {
			for (long id : ids) {
				Product product = productService.getProductById(company_id, id);
				if (product != null) {
					product.setStatus(Constant.STATUS.DELETED_STATUS.getValue());
					productService.update(product);
					List<ProductCategory> listProductCate = productCategoryRepository.getProCateByProductId(id);
					for (ProductCategory result : listProductCate) {
						productCategoryRepository.delete(result);
					}
				}
			}
			return responseUtil.sucessResponse(null);
		} catch (Exception e) {
			throw new ApplicationException(APIStatus.DELETE_PRODUCT_ERROR);
		}
	}

	 @ApiOperation(value = "update product", notes = "")
	    @RequestMapping(value = APIName.PRODUCTS_UPDATE, method = RequestMethod.POST, produces = APIName.CHARSET)
	    public ResponseEntity<APIResponse> updateProduct(HttpServletRequest request,
	            @RequestBody CreateProductModel productRequest) {
	        try {
	            Product product = productService.getProductById(productRequest.getCompanyId(), productRequest.getProductId());
	            if (product != null) {
	                product.setBrowsingName(productRequest.getBrowsingName());
	                product.setDefaultImage(productRequest.getDefaultImage());
	                product.setDescription(productRequest.getDescription());
	                product.setStockControlled(productRequest.getIsStockControlled());
	                product.setListPrice(productRequest.getListPrice());
	                product.setName(productRequest.getName());
	                product.setOverview(productRequest.getOverview());
	                product.setQuantity(productRequest.getQuantity());
	                product.setRank(productRequest.getRank());
	                product.setSalePrice(productRequest.getSalePrice());
	                product.setSku(productRequest.getSku());
	                product.setUpdatedOn(new Date());
	    
	                productService.update(product);
	          
	                List<ProductCategory> listProductCate = productCategoryRepository.getProCateByProductId(productRequest.getProductId());
	                for (ProductCategory result : listProductCate) {
	          
	                    productCategoryRepository.delete(result);
	                }
	            
	                for (Long categoriesId : productRequest.getListCategoriesId()) {
	                    ProductCategoryId productCategoryId = new ProductCategoryId();
	                    productCategoryId.setCategoryId(categoriesId);
	                    productCategoryId.setProductId(product.getProductId());
	                    ProductCategory productCategory = new ProductCategory();
	                    productCategory.setId(productCategoryId);
	                    productCategoryRepository.save(productCategory);
	                }
	                return responseUtil.sucessResponse(product);
	            } else {
	                throw new ApplicationException(APIStatus.GET_PRODUCT_ERROR);
	            }

	        } catch (Exception ex) {
	            throw new ApplicationException(APIStatus.UPDATE_PRODUCT_ERROR);
	        }
	    }
	}
