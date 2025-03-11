package com.flipzon.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flipzon.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	public List<User> findAllByOrderByName();
	public Optional<User> findByMobileNo(String mobileNo);
	public Optional<User> findByEmail(String email);
	
}
