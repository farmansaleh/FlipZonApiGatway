package com.flipzon.entity;

import java.util.List;

public class MpgUserRole {

	private String role_id;
	private List<String> user_id;
	
	public String getRole_id() {
		return role_id;
	}
	public void setRole_id(String role_id) {
		this.role_id = role_id;
	}
	public List<String> getUser_id() {
		return user_id;
	}
	public void setUser_id(List<String> user_id) {
		this.user_id = user_id;
	}

}
