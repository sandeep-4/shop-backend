package spring.java.io.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.java.io.shop.database.model.Company;
import spring.java.io.shop.repository.CompanyRepository;

@Service
public class CompanyService {

	@Autowired
	private CompanyRepository companyRepository;
	
	public Iterable<Company> findAll(){
		return companyRepository.findAll();
	}
	
	public Company findByCompanyId(Long companyId) {
		return companyRepository.findByCompanyId(companyId);
	}


}
