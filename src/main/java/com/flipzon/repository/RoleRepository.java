package com.flipzon.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.flipzon.entity.ERole;
import com.flipzon.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	
	List<Role> findAllByOrderByName();
	Optional<Role> findByName(ERole role);
	
	@Modifying
	@Query(value = "update Role set status=?1 where id=?2")
	int updateRoleStatus(int status,long id);
	
}
