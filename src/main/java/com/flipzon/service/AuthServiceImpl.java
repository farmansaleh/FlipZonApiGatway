package com.flipzon.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.flipzon.dto.ApiResponse;
import com.flipzon.dto.AuthRequest;
import com.flipzon.dto.AuthResponse;
import com.flipzon.dto.SignupRequest;
import com.flipzon.entity.ERole;
import com.flipzon.entity.Role;
import com.flipzon.entity.User;
import com.flipzon.repository.RoleRepository;
import com.flipzon.repository.UserRepository;
import com.flipzon.security.JwtService;
import com.flipzon.security.UserDetailsImpl;

@Service
public class AuthServiceImpl implements AuthService {
	
	@Autowired UserRepository userRepository;
	@Autowired PasswordEncoder passEncoder;
	@Autowired JwtService jwtService;
	@Autowired AuthenticationManager authenticationManager;
	@Autowired RoleRepository roleRepository;

	@Override
	public ApiResponse<AuthResponse> register(SignupRequest signup) throws Exception {
		
		if(userRepository.findByMobileNo(signup.getMobileNo()).isPresent()) {
			throw new RuntimeException("A user is already registered with this mobile number.");
		}
		
		if(userRepository.findByEmail(signup.getEmail()).isPresent()) {
			 throw new RuntimeException("A user is already registered with this e-mail address.");
		}
		
		User user = new User();
		user.setName(signup.getName());
		user.setMobileNo(signup.getMobileNo());
		user.setEmail(signup.getEmail());
		user.setPassword(passEncoder.encode(signup.getPassword()));
		
		List<Role> roles = new ArrayList<Role>();
		signup.getRoles().forEach(role -> {
			Role adminRole = roleRepository.findByName(ERole.valueOf(role))
				.orElseThrow(() -> new RuntimeException(role+" role not found."));
			roles.add(adminRole);
		});
		
		user.setRoles(roles);
		
		user = userRepository.save(user);
		return new ApiResponse<AuthResponse>(200, "Registered successfully!", new AuthResponse(jwtService.generateToken(UserDetailsImpl.build(user))));
	}
	
	@Override
	public List<User> getAllUsers() throws Exception {
		return userRepository.findAllByOrderByName();
	}
	
	@Override
	public User getUserByUsername(String username) throws Exception {
		return userRepository.findByMobileNo(username).orElseThrow(() -> new UsernameNotFoundException("User not found!"));
	}
	
	@Override
	public ApiResponse<AuthResponse> login(AuthRequest authReq) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authReq.getMobileNo(), authReq.getPassword()));
		User user = userRepository.findByMobileNo(authReq.getMobileNo()).orElseThrow(() -> new UsernameNotFoundException("User not found!"));
		return new ApiResponse<AuthResponse>(200, "Login successfully!", new AuthResponse(jwtService.generateToken(UserDetailsImpl.build(user))));
	}
	
}
