package com.flipzon.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.flipzon.entity.User;
import com.flipzon.repository.UserRepository;

/**
 * @author Farman Saleh
 * @since 13/01/2024
 *
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String mobileNo) throws UsernameNotFoundException {
		User user = userRepo.findByMobileNo(mobileNo).orElseThrow(()->new UsernameNotFoundException("User not found with mobile no : "+mobileNo));;
		return UserDetailsImpl.build(user);
	}
}
