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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
@DynamicInsert
@DynamicUpdate
@Table(name="users")
@XmlRootElement
public class User implements Serializable{
	
	
	private static final long serialVersionUID = 1L;

	
	@Id
	@Basic(optional = false)
	private String userId;
	
	@Basic(optional = false)
	private Long companyId;
	
	@Basic(optional = false)
	private int roleId;
	
	@Basic(optional = false)
	private String email;
	
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Basic(optional = false)
	private String passwordHash;
	
	private String firstName;
	
	private String middleName;
	
	private String lastName;
	
	@Basic(optional = false)
	private int status;
	
	@JsonIgnore
	@Basic(optional = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	
	@JsonIgnore
	@Basic(optional = false)
	private String salt;
	
}
