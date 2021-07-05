package spring.java.io.shop.api.request.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestModel {

	private String userId;
	public String firstName;
	public String lastName;
	public String middleName;
	public String email;
	public String phone;
	public String fax;
	public String address;
	public String city;
	public String country;
}
