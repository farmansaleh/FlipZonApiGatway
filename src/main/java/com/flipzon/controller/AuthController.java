package com.flipzon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flipzon.dto.ApiResponse;
import com.flipzon.dto.AuthRequest;
import com.flipzon.dto.AuthResponse;
import com.flipzon.dto.SignupRequest;
import com.flipzon.entity.User;
import com.flipzon.service.AuthService;

import jakarta.validation.Valid;

/**
 * @author Farman Saleh
 * @since 07/02/2024
 *
 */

@RestController
@RequestMapping("/users")
public class AuthController {

	@Autowired AuthService authService;
	
	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public List<User> getAllUsers() throws Exception {
		return authService.getAllUsers();
	}
	
	@PostMapping(value = "/register")
	public ApiResponse<AuthResponse> register(@Valid @RequestBody SignupRequest signup) throws Exception {
		return authService.register(signup);
	}
	
	@PostMapping(value = "/login")
	public ApiResponse<AuthResponse> login(@RequestBody AuthRequest authReq) {
		return authService.login(authReq);
	}
	
}
