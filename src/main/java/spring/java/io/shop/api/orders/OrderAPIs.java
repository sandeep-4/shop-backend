package spring.java.io.shop.api.orders;

import java.util.Date;

import org.apache.tomcat.util.http.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import spring.java.io.shop.api.APIName;
import spring.java.io.shop.api.AbstractBaseAPI;
import spring.java.io.shop.api.request.model.OrderRequestModel;
import spring.java.io.shop.api.request.model.ProductInfo;
import spring.java.io.shop.api.response.model.APIResponse;
import spring.java.io.shop.api.response.model.StatusResponse;
import spring.java.io.shop.database.model.OrderDetail;
import spring.java.io.shop.database.model.Orders;
import spring.java.io.shop.database.model.Product;
import spring.java.io.shop.database.model.UserAddress;
import spring.java.io.shop.service.OrderService;
import spring.java.io.shop.service.UserTokenService;
import spring.java.io.shop.service.order.OrderAddressImpl;
import spring.java.io.shop.service.order.OrderDetailImpl;
import spring.java.io.shop.service.product.ProductService;
import spring.java.io.shop.service.products.UserAddressService;
import spring.java.io.shop.service.products.UserService;
import spring.java.io.shop.util.Constant;

@Api
@RequestMapping(value = APIName.ORDERS)
public class OrderAPIs extends AbstractBaseAPI {

	@Autowired
	OrderService ordersService;
	@Autowired
	UserService customerService;

	@Autowired
	UserTokenService userTokenService;
	@Autowired
	OrderAddressImpl orderAddressImpl;

	@Autowired
	UserAddressService userAddressService;
	@Autowired
	ProductService productService;

	@Autowired
	OrderDetailImpl orderDetailImpl;

	@Autowired
	spring.java.io.shop.api.response.util.ResponseUtil responseUtil;

	@RequestMapping(value = APIName.ORDER_CREATE, method = RequestMethod.POST, produces = APIName.CHARSET)
	@ResponseBody
	public ResponseEntity<APIResponse> addOrders(@PathVariable long comapny_id,
			@RequestBody OrderRequestModel orderRequest) {
		Date date = new Date();
		UserAddress userAddress;
		if (orderRequest.getUser().getUserId() == null || orderRequest.getUser().getUserId().isEmpty()) {
			userAddress = new UserAddress();
			userAddress.setUserId(orderRequest.getUser().getUserId());
			userAddress.setAdress(orderRequest.getUser().getAddress());
			userAddress.setPhone(orderRequest.getUser().getPhone());
			userAddress.setFax(orderRequest.getUser().getFax());
			userAddress.setCity(orderRequest.getUser().getCity());
			userAddress.setCountry(orderRequest.getUser().getCountry());
			userAddress.setStatus(Constant.STATUS.ACTIVE_STATUS.getValue());

			userAddressService.save(userAddress);

		} else {
			userAddress = userAddressService.getAddressByUserIdAndStatus(orderRequest.getUser().getUserId(),
					Constant.STATUS.ACTIVE_STATUS.getValue());
		}
		Orders orders = new Orders();
		orders.setUserId(orderRequest.getUser().getUserId());
		orders.setCompanyId(comapny_id);
		orders.setCustomerEmail(orderRequest.getUser().getEmail());
		orders.setCustomerFirstname(orderRequest.getUser().getFirstName());
		orders.setCustomerMiddlename(orderRequest.getUser().getMiddleName());
		orders.setCustomerLastname(orderRequest.getUser().getLastName());
		orders.setPaymentId(orderRequest.getPaymentId());
		orders.setAdressId(userAddress.getAddressId());
		orders.setStatus(Constant.ORDER_STATUS.PENDING.getStatus());
		orders.setCreatedAt(date);
		orders.setUpdatedAt(date);

		ordersService.save(orders);

		if (orderRequest.getProductList().size() > 0) {
			for (ProductInfo productInfo : orderRequest.getProductList()) {
				Product product = productService.getProductById(comapny_id, productInfo.getProductId());
				if (product != null) {
					OrderDetail orderDetail = new OrderDetail();
					orderDetail.setOrderId(orders.getId());
					orderDetail.setOrderId(orders.getId());
					orderDetail.setProductId(product.getProductId());
					orderDetail.setName(product.getName());
					orderDetail.setPrice(product.getSalePrice());
					orderDetail.setQuantity(productInfo.getQuantity());
					orderDetail.setCreatedAt(date);
					orderDetailImpl.saveOrUpdate(orderDetail);
				}
			}
		}
		return responseUtil.sucessResponse(orders);
	}

	@ApiOperation(value = "get orders by company id", notes = "")
	@RequestMapping(value = APIName.ORDERS_BY_COMPANY, method = RequestMethod.GET, produces = APIName.CHARSET)
	public String getOrdersCompanyId(@PathVariable(value = "id") Long companyId,
			@RequestParam(defaultValue = Constant.DEFAULT_PAGE_NUMBER, required = false) int pageNumber,
			@RequestParam(defaultValue = Constant.DEFAULT_PAGE_SIZE, required = false) int pageSize) {

		// http://localhost:8080/api/orders/1?pagenumber=1&pagesize=2
		
		Page<Orders> orders=ordersService.findAllByCompanyId(companyId, pageNumber, pageSize);
		return writeObjectToJson(new StatusResponse(200,orders.getContent(),orders.getTotalElements()));
	}

}
