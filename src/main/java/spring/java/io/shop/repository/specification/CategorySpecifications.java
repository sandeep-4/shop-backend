package spring.java.io.shop.repository.specification;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import spring.java.io.shop.database.model.Category;

@Component
public class CategorySpecifications {

	public Specification<Category> doFilterSearch(long companyId, String keyword, int sortCase, boolean ascSort) {
		return (Root<Category> root, CriteriaQuery<?> cq, CriteriaBuilder cb) -> {
			List<Predicate> predicates = new LinkedList<>();

			predicates.add(cb.equal(root.<Long>get("companyId"), companyId));
			predicates.add(cb.equal(root.<Integer>get("status"), 1));

			if (keyword != null && !"".equals(keyword)) {
				predicates.add(cb.like(root.<String>get("name"), "%" + keyword + "%"));
			}

			Path orderClause;
			switch (sortCase) {
			case 1:
				orderClause = root.get("name");
				break;
			case 2:
				orderClause = root.get("description");
				break;
			case 3:
				orderClause = root.get("parentId");
				break;
			default:
				orderClause = root.get("name");
				break;
			}
			if (ascSort) {
				cq.orderBy(cb.asc(orderClause));
			} else {
				cq.orderBy(cb.desc(orderClause));

			}
			return cb.and(predicates.toArray(new Predicate[] {}));
		};
	}
}
