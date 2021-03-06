package spring.java.io.shop.database.model;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@DynamicInsert
@DynamicUpdate
@Table(name = "histories")
@XmlRootElement
public class History implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Basic(optional = false)
	    @Column(name = "history_id")
	    private Integer historyId;
	    
	    @Basic(optional = false)
	    @Column(name = "company_id")
	    private int companyId;
	    
	    @Basic(optional = false)
	    @Column(name = "type")
	    private int type;
	    
	    @Basic(optional = false)
	    @Column(name = "create_date")
	    @Temporal(TemporalType.TIMESTAMP)
	    private Date createDate;
	    
	    @Basic(optional = false)
	    @Column(name = "value")
	    private String value;
	    

}
