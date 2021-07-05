package spring.java.io.shop.api.controller.auth;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import spring.java.io.shop.api.APIName;
import spring.java.io.shop.api.controller.AbstractBaseController;
import spring.java.io.shop.api.request.model.AuthRequestModel;
import spring.java.io.shop.api.response.model.APIResponse;
import spring.java.io.shop.api.response.model.UserDetailResponseModel;
import spring.java.io.shop.api.response.util.APIStatus;
import spring.java.io.shop.auth.AuthUserFactory;
import spring.java.io.shop.database.model.User;
import spring.java.io.shop.database.model.UserAddress;
import spring.java.io.shop.database.model.UserToken;
import spring.java.io.shop.exception.ApplicationException;
import spring.java.io.shop.service.UserAddressService;
import spring.java.io.shop.service.auth.AuthService;
import spring.java.io.shop.util.Constant;
import spring.java.io.shop.util.MD5Hash;

@RestController
@RequestMapping(APIName.AUTH_API)
public class AuthController extends AbstractBaseController {

	@Autowired
	AuthService authService;

	@Autowired
	AuthUserFactory authUserFactory;

	@Autowired
	UserAddressService userAddressService;

	@RequestMapping(path = APIName.ADMIN_LOGIN_API, method = RequestMethod.POST)
    public ResponseEntity<APIResponse> adminLogin(
            @PathVariable("company_id") Long companyId,
            @RequestBody AuthRequestModel authRequestModel
    ) {
		User adminUser=authService.getUserByEmailAndCompanyIdAndStatus(authRequestModel.getUsername(), companyId, Constant.USER_STATUS.ACTIVE.getStatus());
		if(adminUser==null) {
            throw new ApplicationException(APIStatus.ERR_USER_NOT_EXIST);
		}else {
			String passwordHash=null;
			try {
				passwordHash=MD5Hash.MD5Encrypt(authRequestModel.getPassword()+adminUser.getSalt());
			} catch (Exception e) {
                throw new RuntimeException("User login encrypt password error", e);
			}
			if(passwordHash.equals(adminUser.getPasswordHash())) {
				if(adminUser.getRoleId()==Constant.USER_ROLE.SYS_ADMIN.getRoleId()
                        || adminUser.getRoleId() == Constant.USER_ROLE.STORE_MANAGER.getRoleId()
                        || adminUser.getRoleId() == Constant.USER_ROLE.STORE_ADMIN.getRoleId()) {
					UserToken userToken=authService.createUserToken(adminUser, authRequestModel.isKeepMeLogin());
					Authentication authentication=new UsernamePasswordAuthenticationToken(adminUser.getEmail(), adminUser.getPasswordHash());
					SecurityContextHolder.getContext().setAuthentication(authentication);
					return responseUtil.sucessResponse(userToken.getToken());
				}
					else {
	                    throw new ApplicationException(APIStatus.ERR_PERMISSION_DENIED);
	                }
	            } else {
	                throw new ApplicationException(APIStatus.ERR_USER_NOT_VALID);
	            }

	        }
	    }

	@RequestMapping(path = APIName.SESSION_DATA, method = RequestMethod.GET)
	public ResponseEntity<APIResponse> getSessionData(HttpServletRequest request,
			@PathVariable("company_id") Long companyId) {
		String userId = getAuthUserFromSession(request).getId();
		if (userId != null && !"".equals(userId)) {
			User user = authService.getUserByEmailAndCompanyIdAndStatus(userId, companyId,
					Constant.USER_STATUS.ACTIVE.getStatus());
			UserAddress userAddress = userAddressService.getAddressByUSerIdANdStatus(userId,
					Constant.STATUS.ACTIVE_STATUS.getValue());
			if (user != null) {
				UserDetailResponseModel userResponse = new UserDetailResponseModel();
				userResponse.setUserId(user.getUserId());
				userResponse.setCompanyId(user.getCompanyId());
				userResponse.setEmail(user.getEmail());
				userResponse.setFirstName(user.getFirstName());
				userResponse.setLastName(user.getLastName());
				userResponse.setRoleId(user.getRoleId());
				if (userAddress != null) {
					userResponse.setAddress(userAddress.getAdress());
					userResponse.setCity(userAddress.getCity());
					userResponse.setCountry(userAddress.getCountry());
					userResponse.setPhone(userAddress.getPhone());
				}
				return responseUtil.sucessResponse(userResponse);
			} else {
				throw new ApplicationException(APIStatus.ERR_USER_NOT_EXIST);
			}
		} else {
			throw new ApplicationException(APIStatus.INVALID_PARAMETER);
		}
	}

	@RequestMapping(path = APIName.USERS_LOGOUT, method = RequestMethod.POST)
	public ResponseEntity<APIResponse> logout(@RequestHeader(value = Constant.HEADER_TOKEN) String tokenId) {
		if (tokenId != null && !"".equals(tokenId)) {
			UserToken userToken = authService.getUserTokenById(tokenId);
			if (userToken != null) {
				authService.delteUserToken(userToken);
				return responseUtil.sucessResponse("OK");
			} else {
				throw new ApplicationException(APIStatus.ERR_SESSION_NOT_FOUND);
			}
		} else {
			throw new ApplicationException(APIStatus.INVALID_PARAMETER);
		}
	}
}
