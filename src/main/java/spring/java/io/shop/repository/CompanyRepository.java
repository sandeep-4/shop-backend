package spring.java.io.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import spring.java.io.shop.database.model.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer>{

	@Query("SELECT c FROM Company c")
	Iterable<Company> findAll(long companyId);
	
	@Query("SELECT c FROM Company c WHERE c.companyId=:companyId")
	Company findByCompanyId(@Param("companyId")long companyId);
}
