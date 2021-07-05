package spring.java.io.shop.api.request.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrdersRequestModel {

	private String searchKey;
	private int sortCase;
	private boolean ascSort;
	private int pageNumber;
	private int pageSize;
	private int status;
	
}
