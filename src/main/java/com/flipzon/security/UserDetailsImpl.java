package com.flipzon.security;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.flipzon.entity.User;

/**
 * @author Farman Saleh
 * @since 13/01/2024
 *
 */

public class UserDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 1L;
	
	private long id;
	private String mobileNo;
	private String emailId;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;
	
	public UserDetailsImpl(long id, String mobileNo, String emailId, String password,
		Collection<? extends GrantedAuthority> authorities) {
	    this.id = id;
	    this.mobileNo = mobileNo;
	    this.emailId = emailId;
	    this.password = password;
	    this.authorities = authorities;
	}
	
	public static UserDetailsImpl build(User user) {
		
		List<GrantedAuthority> authorities = user.getRoles().stream().map(role -> {
			return new SimpleGrantedAuthority("ROLE_"+role.getName());
		}).collect(Collectors.toList());
		
		return new UserDetailsImpl(user.getId(), user.getMobileNo(), user.getEmail(), user.getPassword(), authorities);
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	@Override
	public String getUsername() {
		return this.mobileNo;
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return true;
	}
	
}
