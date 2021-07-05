package spring.java.io.shop.api.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import spring.java.io.shop.api.APIName;
import spring.java.io.shop.api.AbstractBaseAPI;
import spring.java.io.shop.api.response.model.StatusResponse;
import spring.java.io.shop.database.model.ProductAttribute;
import spring.java.io.shop.service.ProductAttributeService;

@RestController
@Api(value = "Product Attribute API")
public class ProductAttributeAPI extends AbstractBaseAPI{

	@Autowired
	private ProductAttributeService productAttributeService;
	
	@ApiOperation(value="get productAttribute")
	@RequestMapping(value=APIName.PRODUCT_ATTRIBUTES,method=RequestMethod.GET,produces=APIName.CHARSET)
	public String getProductAttribute() {
		List<ProductAttribute> productAttribute=(List<ProductAttribute>) productAttributeService.findAll();
		return writeObjectToJson(new StatusResponse<>(HttpStatus.OK.value(),productAttribute));
	}
}
