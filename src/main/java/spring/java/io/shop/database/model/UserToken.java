package spring.java.io.shop.database.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
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
@Table(name = "user_tokens")
@XmlRootElement
public class UserToken implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Basic(optional = false)
	private String token;
	@Basic(optional = false)	
	private Long comapnyId;
	@Basic(optional = false)
	private String userId;
	@Basic(optional = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date loginDate;
	@Basic(optional = false)
	@Temporal(TemporalType.TIMESTAMP)	
	private Date expirationDate;
	@Basic(optional = false)
	private String sessionData;
	
}
