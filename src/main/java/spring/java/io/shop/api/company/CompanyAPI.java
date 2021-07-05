package spring.java.io.shop.api.company;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import spring.java.io.shop.api.APIName;
import spring.java.io.shop.api.AbstractBaseAPI;
import spring.java.io.shop.api.response.model.StatusResponse;
import spring.java.io.shop.database.model.Company;
import spring.java.io.shop.service.CompanyService;

@RestController
public class CompanyAPI extends AbstractBaseAPI{

	@Autowired
	CompanyService companyService;
	
	@RequestMapping(value=APIName.COMPANIES,method = RequestMethod.GET, produces = APIName.CHARSET)
	public String getAllCompanies() {
		List<Company> companies=(List<Company>) companyService.findAll();
		return writeObjectToJson(new StatusResponse(200,companies));
	}
	
    @RequestMapping(value = APIName.COMPANIES_SEARCH_BY_ID, method = RequestMethod.GET, produces = APIName.CHARSET)
    public String getCompnayById(@PathParam(value = "id")Long companyId) {
    	Company companies=companyService.findByCompanyId(companyId);
		return writeObjectToJson(new StatusResponse(200,companies));
    	
    }
}
