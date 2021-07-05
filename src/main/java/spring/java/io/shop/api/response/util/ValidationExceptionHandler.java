package spring.java.io.shop.api.response.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import spring.java.io.shop.api.response.model.APIResponse;
import spring.java.io.shop.exception.ApplicationException;

@ControllerAdvice
public class ValidationExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private ResponseUtil responseUtil;

	protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@ExceptionHandler(value = ApplicationException.class)
	public ResponseEntity<APIResponse> handleApplicationException(ApplicationException ex, WebRequest request) {
		LOGGER.debug("handleApplicationException", ex);
		ResponseEntity<APIResponse> response;
		if (ex.getApiStatus() == APIStatus.ERR_BAD_REQUEST) {
			response = responseUtil.badRequestResponse(ex.getData());
		} else {
			response = responseUtil.buildResponse(ex.getApiStatus(), ex.getData(), HttpStatus.OK);
		}
		return response;
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return new ResponseEntity(new APIResponse<>(APIStatus.ERR_BAD_REQUEST, null), headers, status);
	}

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<APIResponse> handleUncaughtException(Exception ex, WebRequest request) {
		LOGGER.error("handlerUncaughtException", ex);
		return responseUtil.buildResponse(APIStatus.ERR_INTERNAL_SERVER, "Please contact system admin",
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
