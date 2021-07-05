package spring.java.io.shop.api.request.model;

import com.sun.istack.NotNull;

import lombok.Data;

@Data
public class UpdateCategoryRequestModel {

	private Long id;
	private Long parentId;
	
	@NotNull
	private String name;
	private Integer position;
	private String description;
}
