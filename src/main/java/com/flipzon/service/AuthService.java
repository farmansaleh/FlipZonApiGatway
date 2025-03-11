package com.flipzon.service;

import java.util.List;

import com.flipzon.dto.ApiResponse;
import com.flipzon.dto.AuthRequest;
import com.flipzon.dto.AuthResponse;
import com.flipzon.dto.SignupRequest;
import com.flipzon.entity.User;

public interface AuthService {
	
	ApiResponse<AuthResponse> register(SignupRequest signup) throws Exception;
	List<User> getAllUsers() throws Exception;
	User getUserByUsername(String username) throws Exception;
	ApiResponse<AuthResponse> login(AuthRequest authReq);
	
}
