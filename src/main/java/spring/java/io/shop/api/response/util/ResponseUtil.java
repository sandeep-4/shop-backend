package spring.java.io.shop.api.response.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import spring.java.io.shop.api.response.model.APIResponse;
import spring.java.io.shop.util.Constant;

@Component
public class ResponseUtil {

	private APIResponse _createSSOResponse(APIStatus apiStatus,Object data) {
		return new APIResponse(apiStatus,data);
	}
	
	public ResponseEntity<APIResponse> buildResponse(APIStatus apistatus,Object data,HttpStatus httpStatus){
		return new ResponseEntity(_createSSOResponse(apistatus, data),httpStatus);
	}
	
	public ResponseEntity<APIResponse> sucessResponse(Object data){
		return buildResponse(APIStatus.OK, data, HttpStatus.OK);
	}
	
	public ResponseEntity<APIResponse> badRequestResponse(List<Constant.ParamError> errors){
		Map<String, String> errMap=null;
		if(errors!=null) {
			errMap=new HashMap<>();
			for(Constant.ParamError error:errors){
				errMap.put(error.getName(),error.getDesc());
			}
		}
		return buildResponse(APIStatus.ERR_BAD_REQUEST, errMap, HttpStatus.BAD_REQUEST);
	}


	
}
