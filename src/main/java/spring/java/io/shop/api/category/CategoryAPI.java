package spring.java.io.shop.api.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import spring.java.io.shop.api.APIName;
import spring.java.io.shop.api.AbstractBaseAPI;
import spring.java.io.shop.api.response.model.StatusResponse;
import spring.java.io.shop.api.response.util.APIStatus;
import spring.java.io.shop.database.model.Category;
import spring.java.io.shop.repository.CategoryRepository;

@RestController
@Api(value="Category API")
public class CategoryAPI extends AbstractBaseAPI{

	@Autowired
	CategoryRepository categoryRepository;
	
	@ApiOperation(value = "getCategory")
	@RequestMapping(value=APIName.CATEGORIES,method=RequestMethod.GET,produces = APIName.CHARSET)
	public String getCategories(@PathVariable(name = "companyId")long companyId) {
		List<Category> categories=(List<Category>) categoryRepository.findByCompanyId(companyId);
		return writeObjectToJson(new StatusResponse<>(HttpStatus.OK.value(),categories));
	}
	
	@ApiOperation(value="create")
	@RequestMapping(value = APIName.CATEGORIES, method = RequestMethod.POST, produces = APIName.CHARSET)
	@ResponseBody
	public String addCategory(@PathVariable(value="compnayId")long companyId,
			@RequestParam(name="parent_id",required=false)Long parentID,
			 @RequestParam(name = "name", required = true) String name,
	            @RequestParam(name = "status", required = false) Integer Status,
	            @RequestParam(name = "position", required = false) Integer position,
	            @RequestParam(name = "description", required = false) String description
			) {
		Category category=new Category();
		category.setCompanyId(companyId);
		category.setParentId(parentID);
		category.setName(name);
		category.setStatus(Status);
		category.setPosition(position);
		category.setDescription(description);
		
		categoryRepository.save(category);
		
		return writeObjectToJson(new StatusResponse<>(HttpStatus.OK.value(),category));
	}
	
	 @RequestMapping(value = APIName.CATEGORIES_ID, method = RequestMethod.DELETE, produces = APIName.CHARSET)
	    public String deleteCategory(@PathVariable(value = "id") Long categoryId) {
		 StatusResponse statusResponse=null;
		 Category category=categoryRepository.findByCategoryId(categoryId);
		 if(category!=null) {
			 categoryRepository.delete(category);
			 statusResponse=new StatusResponse<>(APIStatus.OK.getCode(),"deleted sucessfully");
		 }else {
			 statusResponse.setResult("not found");
		 }
		 return writeObjectToJson(statusResponse);
	 }
	
	 
	    @RequestMapping(value = APIName.CATEGORIES_ID, method = RequestMethod.PUT, produces = APIName.CHARSET)
	    public String updateCategory(@PathVariable(value = "companyId") Long companyId,
	            @PathVariable(value = "id") Long categoryId,
	            @RequestParam(name = "name") String name,
	            @RequestParam(name = "status", required = false) Integer status,
	            @RequestParam(name = "parent_id", required = false) Long ParentID,
	            @RequestParam(name = "position", required = false) Integer position,
	            @RequestParam(name = "description", required = false) String description) {
	    	
	    	StatusResponse statusResponse=null;
	    	Category category=categoryRepository.findByCategoryId(categoryId);
	    	
	    	if(category!=null) {
	    		if(!name.equals("")) {
	    			category.setName(name);
	    		}
	    		else {
	    			statusResponse=new StatusResponse(APIStatus.OK.getCode(), "update name no successfully");
	                return writeObjectToJson(statusResponse);
	    		}
	    		if(ParentID!=null) {
	    			Category parent=categoryRepository.findByParentId(ParentID);
	    			if(parent!=null && parent.getCompanyId()==category.getCompanyId()) {
	    				category.setParentId(ParentID);
	    			}
	    			else {
	    				statusResponse = new StatusResponse(APIStatus.OK.getCode(), "update parent_id no successfully");
	                    return writeObjectToJson(statusResponse);
	    			}
	    			
	    		}
	    		if(status!=null) {
	    			category.setStatus(status);
	    		}
	    		if(position!=null) {
	    			category.setPosition(position);
	    		}
	    		 if (description != null) {
	                 category.setDescription(description);
	             }
	    		 categoryRepository.save(category);
	             statusResponse = new StatusResponse(APIStatus.OK.getCode(), category);

	    	}
	    	return writeObjectToJson(statusResponse);
	    }
	
	
	
	
	
	
	
	
	
	
}
