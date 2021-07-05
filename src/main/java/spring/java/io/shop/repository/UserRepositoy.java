package spring.java.io.shop.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import spring.java.io.shop.database.model.User;

@Repository
public interface UserRepositoy extends /*
 * Copyright 2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
PagingAndSortingRepository<User, String>,JpaSpecificationExecutor<User>{

	User findByEmailAndCompanyIdAndStatus(String email,Long companyId,int status);
	
	User findByUserIdAndCompanyIdAndStatus(String userId,long compnayId,int status);
	
//	User findByEmail(String email);
	@Query("SELECT u FROM User u  WHERE u.email=:email AND u.companyId=:companyId")
	User findByEmail(@Param("email") String email,@Param("companyId") long companyId);
	
	User findByUserId(String userId);
}
