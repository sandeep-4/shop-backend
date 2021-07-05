package spring.java.io.shop.api.response.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PagingResponseModel {

	private List<?> data;
	private long totalResult;
	private int totalPage;
	private int currentPage;
	
	
	
	
}
