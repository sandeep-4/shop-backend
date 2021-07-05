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
public class CreateProductModel {

	private long productId;
	private long companyId;
	private List<Long> listCategoriesId;
	private String name;
    private String browsingName;
    private double salePrice;
    private double listPrice;
    private String defaultImage;
    private String overview;
    private int quantity;
    private Boolean isStockControlled;
    private String description;
    private int rank;
    private String sku;
	
	
}
