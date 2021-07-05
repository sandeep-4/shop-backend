package spring.java.io.shop.api.review;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import spring.java.io.shop.api.APIName;
import spring.java.io.shop.api.AbstractBaseAPI;
import spring.java.io.shop.api.response.model.StatusResponse;
import spring.java.io.shop.api.response.util.APIStatus;
import spring.java.io.shop.database.model.Review;
import spring.java.io.shop.database.model.User;
import spring.java.io.shop.database.model.UserToken;
import spring.java.io.shop.service.ReviewService;
import spring.java.io.shop.service.UserService;
import spring.java.io.shop.service.UserTokenService;
import spring.java.io.shop.util.Constant;

@RestController
@RequestMapping(APIName.REVIEWS)
public class ReviewAPI extends AbstractBaseAPI {

	@Autowired
	private ReviewService reviewService;

	@Autowired
	UserService userService;

	@Autowired
	UserTokenService userTokenService;

	@RequestMapping(value = APIName.REVIEWS_BY_PRODUCT_ID, method = RequestMethod.GET, produces = APIName.CHARSET)
	public String getReviewByProductId(@PathVariable(value = "id") Long productId,
			@RequestParam(value = "pagenumber", defaultValue = Constant.DEFAULT_PAGE_NUMBER, required = false) int pageNumber,
			@RequestParam(value = "pagesize", defaultValue = Constant.DEFAULT_PAGE_SIZE, required = false) int pageSize) {

		Page<Review> reviews = reviewService.findbyProductId(productId, pageNumber, pageSize);
		return writeObjectToJson(new StatusResponse(200, reviews.getContent(), reviews.getTotalElements()));
	}

	@RequestMapping(value = APIName.REVIEWS_ADD, method = RequestMethod.POST, produces = APIName.CHARSET)
	public String createReview(HttpServletRequest request, @RequestBody Review reviewBody,
			@PathVariable Long companyId) {

		StatusResponse statusResponse = null;

		String token = request.getParameter("token");
		if (token != null && !token.isEmpty()) {
			Date now = new Date();
			UserToken userToken = userTokenService.getTokenById(token);
			if (userToken == null) {
				statusResponse = new StatusResponse(APIStatus.ERR_UNAUTHORIZED);

			} else if (userToken.getExpirationDate().getTime() - now.getTime() > 0) {
				statusResponse = new StatusResponse(APIStatus.ERR_UNAUTHORIZED);
			} else {
				User user = userService.getUserByUserIdAndComIdAndStatus(userToken.getUserId(), companyId,
						Constant.USER_STATUS.ACTIVE.getStatus());
				if (user != null && user.getUserId() != null && !user.getUserId().isEmpty()) {
					reviewBody.setUserId(user.getUserId());
					reviewBody.setCompanyId(companyId);
					reviewBody.setCreateDate(new Date());
					reviewService.save(reviewBody);

					statusResponse = new StatusResponse(APIStatus.OK);
				} else {
					statusResponse = new StatusResponse(APIStatus.ERR_USER_NOT_EXIST);
				}
			}
		} else {
			statusResponse = new StatusResponse(APIStatus.INVALID_PARAMETER);
		}
		return writeObjectToJson(statusResponse);

	}

}
