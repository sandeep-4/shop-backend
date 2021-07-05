package spring.java.io.shop.api.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import spring.java.io.shop.api.APIName;
import spring.java.io.shop.api.AbstractBaseAPI;
import spring.java.io.shop.api.response.model.StatusResponse;
import spring.java.io.shop.database.model.ProductAttributeDetail;
import spring.java.io.shop.service.ProductAttributeDetailsService;
import spring.java.io.shop.service.products.ProductAttributeDetailService;

@Api(value = "ProductAttributeDetail API")
@RestController
public class ProductAttributeDetailAPI extends AbstractBaseAPI{

	@Autowired
	ProductAttributeDetailsService productAttributeDetailService;
	
	@ApiOperation(value = "product attribute")
    @RequestMapping(value = APIName.PRODUCT_DETAILS, method = RequestMethod.GET, produces = APIName.CHARSET)
	public String getproductDetails(@PathVariable(value="product_id")Long productId) {
		StatusResponse statusResponse = null;
		List<ProductAttributeDetail> productdetails=(List<ProductAttributeDetail>) productAttributeDetailService.findAllByProductId(productId);
		if(productdetails!=null) {
			return writeObjectToJson(new StatusResponse(200,productdetails));
		}else {
			statusResponse.setResult("not found");
		}
		return writeObjectToJson(statusResponse);
	}
}
