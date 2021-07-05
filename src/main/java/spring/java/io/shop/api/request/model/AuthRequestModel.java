package spring.java.io.shop.api.request.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequestModel {

	public String username;
	public String password;
	public boolean keepMeLogin;
}
