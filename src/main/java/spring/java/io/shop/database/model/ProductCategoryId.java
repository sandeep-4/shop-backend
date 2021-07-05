package spring.java.io.shop.database.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ProductCategoryId implements Serializable{

	private static final long serialVersionUID = 1L;

	//    @Id
    @Basic(optional = false)
    @Column(name = "product_id")
    private Long productId;

//    @Id
    @Basic(optional = false)
    @Column(name = "category_id")
    private Long categoryId;
}
