package spring.java.io.shop.database.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@DynamicInsert
@DynamicUpdate
@Table(name = "products")
@XmlRootElement
public class Product implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productId;
	
	private Long companyId;
	
	private String name;
	
	private String browsingName;
	
	private double salePrice;
	
	private double listPrice;
	
	private String defaultImage;
	
	private String overview;
	
	private int quantity;
	
	private boolean isStockControlled;
	
	private int status;
	
	private String description;
	
	private int rank;
	
	private String sku;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedOn;
	
	
	
	
}
