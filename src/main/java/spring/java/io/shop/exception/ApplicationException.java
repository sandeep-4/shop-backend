package spring.java.io.shop.exception;

import java.util.List;

import spring.java.io.shop.api.response.util.APIStatus;
import spring.java.io.shop.util.Constant;
import spring.java.io.shop.util.Constant.ParamError;

public class ApplicationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private APIStatus apiStatus;
	private List<Constant.ParamError> data;

	public ApplicationException(APIStatus apiStatus) {
		this.apiStatus = apiStatus;
	}

	 public ApplicationException(APIStatus apiStatus, List<Constant.ParamError> data) {
	        this(apiStatus);
	        this.data = data;
	    }

	    public ApplicationException(Throwable cause) {
	        super(cause);
	    }

	    public APIStatus getApiStatus() {
	        return apiStatus;
	    }

	    public List<Constant.ParamError> getData() {
	        return data;
	    }

}
