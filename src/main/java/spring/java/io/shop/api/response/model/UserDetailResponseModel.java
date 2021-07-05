package spring.java.io.shop.api.response.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailResponseModel {

	private String userId;
	private Long companyId;
	private int roleId;
	private String email;
	private String firstName;
	private String middleName;
	private String lastName;
	private Date createDate;
	private String salt;
	private String address;
	private String phone;
	private String fax;
	private String city;
	private String country;

}
