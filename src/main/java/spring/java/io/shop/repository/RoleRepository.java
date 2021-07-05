package spring.java.io.shop.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import spring.java.io.shop.database.model.Role;

@Repository
@Transactional
public interface RoleRepository extends PagingAndSortingRepository<Role, Integer>{
Role findByRoleId(Integer roleId);
}
