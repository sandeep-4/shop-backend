package spring.java.io.shop.repository.specification;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import spring.java.io.shop.database.model.User;
import spring.java.io.shop.util.Constant;

public class UserSpecification implements Specification<User> {

	private static final long serialVersionUID = 1L;
	private final long companyId;
	private final String searchKey;
	private final int sortCase;
	private final boolean isAscSort;
	private final String userId;

	public UserSpecification(String userId,long companyId, String searchKey, int sortCase, boolean isAscSort) {
		super();
		this.companyId = companyId;
		this.searchKey = searchKey;
		this.sortCase = sortCase;
		this.isAscSort = isAscSort;
		this.userId = userId;
	}

	
	@Override
	public Predicate toPredicate(Root<User> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
	List<Predicate> predicates=new LinkedList<>();
	
	if(companyId!=-1) {
		predicates.add(cb.equal(root.get("companyId"),companyId));
	}
	
	if(searchKey!=null && !searchKey.trim().isEmpty()) {
		String wrapSearch="%"+searchKey.trim()+"%";
		Predicate firstName=cb.like(root.<String>get("firstName"), wrapSearch);
		Predicate lastName=cb.like(root.<String>get("lastName"), wrapSearch);
		Predicate email=cb.like(root.<String>get("email"), wrapSearch);
		Predicate search=cb.or(firstName,lastName,email);
		
		predicates.add(search);
	}
	predicates.add(cb.notEqual(root.get("userId"), userId));
	predicates.add(cb.equal(root.get("status"),Constant.USER_STATUS.ACTIVE.getStatus()));
	
	Path orderClause;
      switch (sortCase) {
          case Constant.SORT_BY_FIRST_NAME:
              orderClause = root.get("firstName");
              break;
          case Constant.SORT_BY_LAST_NAME:
              orderClause = root.get("lastName");
              break;
          case Constant.SORT_BY_EMAIL_ADDRESS:
              orderClause = root.get("email");
              break;
          case Constant.SORT_BY_CREATE_DATE:
              orderClause = root.get("createDate");
              break;
          default: // sort by product name
              orderClause = root.get("firstName");
              break;
      }
      
      if(isAscSort) {
    	  cq.orderBy(cb.asc(orderClause));
      }else {
    	  cq.orderBy(cb.desc(orderClause));
      }

      return cb.and(predicates.toArray(new Predicate[] {}));
	
	}

}
