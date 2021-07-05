package spring.java.io.shop.api.request.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestModel {

	private UserRequestModel user;
	private List<ProductInfo> productList;
	private long paymentId;
}
