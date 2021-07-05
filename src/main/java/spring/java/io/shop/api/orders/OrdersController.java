package spring.java.io.shop.api.orders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import spring.java.io.shop.api.APIName;
import spring.java.io.shop.api.request.model.OrdersRequestModel;
import spring.java.io.shop.api.response.model.APIResponse;
import spring.java.io.shop.api.response.util.APIStatus;
import spring.java.io.shop.api.response.util.ResponseUtil;
import spring.java.io.shop.database.model.OrderDetail;
import spring.java.io.shop.database.model.Orders;
import spring.java.io.shop.database.model.Payment;
import spring.java.io.shop.database.model.Product;
import spring.java.io.shop.database.model.UserAddress;
import spring.java.io.shop.exception.ApplicationException;
import spring.java.io.shop.repository.PaymentRepository;
import spring.java.io.shop.repository.UserAddressRepository;
import spring.java.io.shop.service.order.OrderAddressService;
import spring.java.io.shop.service.order.OrderDetailService;
import spring.java.io.shop.service.order.OrderPaymentService;
import spring.java.io.shop.service.order.OrderService;
import spring.java.io.shop.service.product.ProductService;
import spring.java.io.shop.util.Constant;

@RestController
@RequestMapping(APIName.ORDERS)
public class OrdersController {

	@Autowired
	OrderService orderService;
	@Autowired
	OrderDetailService orderDetailService;
	@Autowired
	OrderAddressService orderAddressService;
	@Autowired
	OrderPaymentService orderPaymentService;
	@Autowired
	ProductService productService;
	@Autowired
	UserAddressRepository userAddressRepository;
	@Autowired
	PaymentRepository paymentRepository;

	@Autowired
	ResponseUtil responseUtil;

	@RequestMapping(path = APIName.ORDERS_BY_COMPANY, method = RequestMethod.POST)
	public ResponseEntity<?> fetPagingorder(@PathVariable("company_id") long companyId,
			@RequestBody OrdersRequestModel ordersRequestModel) {

		try {
			Page<Orders> listOrders = orderService.doPagingOrders(ordersRequestModel, companyId);
			return responseUtil.sucessResponse(listOrders);
		} catch (Exception e) {
			throw new ApplicationException(APIStatus.ERR_GET_LIST_ORDERS);
		}
	}

	@RequestMapping(path = APIName.ORDERS_DETAIL_BY_COMPANY, method = RequestMethod.GET)
	public ResponseEntity<APIResponse> getDetailOrders(@PathVariable("company_id") Long companyId,
			@PathVariable("order_id") Long orderId) {
		Map<String, Object> resultOrders = new HashMap<String, Object>();
		try {
			Orders order = orderService.getOrderByOrderIdAndCompanyID(orderId, companyId);
			if (order != null) {
				resultOrders.put("orders", order);
				List<OrderDetail> orderDetailByOrderId = orderDetailService.getListOrderDetail(orderId);
				List<Map<String, Object>> listOrderDetail = new ArrayList<Map<String, Object>>();
				if (orderDetailByOrderId != null && !orderDetailByOrderId.isEmpty()) {
					for (OrderDetail orderDetail : orderDetailByOrderId) {
						Map<String, Object> detail = new HashMap<String, Object>();
						Product product = productService.getProductById(companyId, orderDetail.getProductId());
						Payment payment = paymentRepository.findByPaymentId(order.getPaymentId());
						if (product != null && payment != null) {
							detail.put("product", product);
							detail.put("payment", payment);
							detail.put("ordersDetail", orderDetail);
							listOrderDetail.add(detail);
						}
					}
					resultOrders.put("listOrdersDetail", listOrderDetail);
				}
				UserAddress userAddress = userAddressRepository.findByAddressIdAndStatus(order.getAdressId(),
						Constant.STATUS.ACTIVE_STATUS.getValue());
				resultOrders.put("orderAddress", userAddress);

			}
		return responseUtil.sucessResponse(resultOrders);
		} catch (Exception e) {
			throw new ApplicationException(APIStatus.ERR_GET_DETAIL_ORDERS);
		}
	}
	
	
	
	
	 @RequestMapping(path = APIName.CHANGE_STATUS_ORDERS_BY_COMPANY, method = RequestMethod.GET)
	    public ResponseEntity<APIResponse> changeOrders(
	            @PathVariable("company_id") Long companyId,
	            @PathVariable("order_id") Long orderId,
	            @PathVariable("status") int status
	    ){
		 try {
			if(companyId!=null) {
				if(orderId!=null) {
					Orders order=orderService.getOrderByOrderIdAndCompanyID(orderId, companyId);
					if(order!=null) {
						switch(status) {
						 case 0:
                             order.setStatus(Constant.ORDER_STATUS.PENDING.getStatus());
                             break;
                         case 1:
                             order.setStatus(Constant.ORDER_STATUS.SHIPPING.getStatus());
                             break;
                         case 2:
                             order.setStatus(Constant.ORDER_STATUS.COMPLETED.getStatus());
                             break;
                         default:
                             order.setStatus(Constant.ORDER_STATUS.PENDING.getStatus());
                             break;
                     }
                     orderService.updateStatusOrder(order);

                 } else {
                     throw new ApplicationException(APIStatus.ERR_ORDER_ID_NOT_FOUND);
                 }
                 return responseUtil.sucessResponse("Change status order succesfully");
             } else {
                 throw new ApplicationException(APIStatus.ERR_ORDER_ID_EMPTY);
             }
         } else {
             throw new ApplicationException(APIStatus.ERR_COMPANY_ID_EMPTY);
         }
     } catch (Exception e) {
         throw new ApplicationException(APIStatus.ERR_DELETE_ORDER);
     }

 }

}
