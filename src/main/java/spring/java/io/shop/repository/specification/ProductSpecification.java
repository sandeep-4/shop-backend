package spring.java.io.shop.repository.specification;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import javassist.compiler.ast.CondExpr;
import spring.java.io.shop.database.model.Product;
import spring.java.io.shop.database.model.ProductAttributeDetail;
import spring.java.io.shop.database.model.ProductCategory;
import spring.java.io.shop.util.Constant;

public class ProductSpecification implements Specification<Product> {

	private static final long serialVersionUID = 1L;
	private final long companyId;
	private final long categoryId;
	private final long attributeId;
	private final String searchKey;
	private double minPrice;
	private final double maxPrice;
	private int minRank;
	private final int maxRank;
	private final int sortCase;
	private final boolean isAscSort;

	public ProductSpecification(long companyId, long categoryId, long attributeId, String searchKey, double minPrice,
			double maxPrice, int minRank, int maxRank, int sortCase, boolean isAscSort) {
		super();
		this.companyId = companyId;
		this.categoryId = categoryId;
		this.attributeId = attributeId;
		this.searchKey = searchKey;
		this.minPrice = minPrice;
		this.maxPrice = maxPrice;
		this.minRank = minRank;
		this.maxRank = maxRank;
		this.sortCase = sortCase;
		this.isAscSort = isAscSort;
	}

	@Override
	public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
		List<Predicate> predicates = new LinkedList<>();
		predicates.add(cb.equal(root.get("status"), Constant.STATUS.ACTIVE_STATUS.getValue()));

		if (companyId != -1) {
			predicates.add(cb.equal(root.get("companyId"), companyId));
		}

		if (companyId != -1) {
			Root<ProductCategory> proCatRoot = cq.from(ProductCategory.class);
			predicates.add(cb.equal(proCatRoot.get("id").get("categoryId"), categoryId));
			predicates.add(cb.equal(root.get("productId"), proCatRoot.get("id").get("productId")));
		}

		if (attributeId != -1) {
			Root<ProductAttributeDetail> padRoot = cq.from(ProductAttributeDetail.class);
			predicates.add(cb.equal(padRoot.get("attributeId"), attributeId));
			predicates.add(cb.equal(root.get("productId"), padRoot.get("productId")));
		}

		if (searchKey != null && !searchKey.trim().isEmpty()) {
			predicates.add(cb.like(root.<String>get("name"), "%" + searchKey.trim() + "%"));
		}

		// price
		if (minPrice < 0) {
			minPrice = 0;
		}

		if (minPrice < maxPrice) {
			predicates.add(cb.between(root.<Double>get("salePrice"), minPrice, maxPrice));
		} else if (minPrice > 0) {
			if (maxPrice == -1) {
				predicates.add(cb.greaterThanOrEqualTo(root.<Double>get("salePrice"), minPrice));
			} else if (minPrice == maxPrice) {
				predicates.add(cb.equal(root.<Double>get("salePrice"), minPrice));
			}
		}

		// validate rank
		if (minRank < maxRank) {
			predicates.add(cb.between(root.<Integer>get("rank"), minRank, maxRank));
		} else if (minRank > 0) {
			if (maxRank == -1) {
				predicates.add(cb.greaterThanOrEqualTo(root.<Integer>get("rank"), minRank));
			} else if (minRank == maxRank) {
				predicates.add(cb.equal(root.<Integer>get("rank"), minRank));
			}
		}

		Path orderClause;
		switch (sortCase) {
		case Constant.SORT_BY_PRODUCT_NAME:
			orderClause = root.get("name");
			break;
		case Constant.SORT_BY_PRODUCT_PRICE:
			orderClause = root.get("salePrice");
			break;
		case Constant.SORT_BY_PRODUCT_QUANTITY:
			orderClause = root.get("quantity");
			break;
		case Constant.SORT_BY_PRODUCT_CREATE_DATE:
			orderClause = root.get("createdOn");
			break;
		default: // sort by product name
			orderClause = root.get("createdOn");
		}
		
		if(isAscSort) {
			cq.orderBy(cb.asc(orderClause));
		}else {
			cq.orderBy(cb.desc(orderClause));
		}
	
	return cb.and(predicates.toArray(new Predicate[] {}));
	}
	

}
