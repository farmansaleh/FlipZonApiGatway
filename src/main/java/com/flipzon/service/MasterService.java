package com.flipzon.service;

import java.util.List;
import com.flipzon.dto.ApiResponse;
import com.flipzon.entity.Role;

public interface MasterService {
	
	ApiResponse<Role> createRole(Role role) throws Exception;
	List<Role> getAllRoles() throws Exception;
	Role getRoleById(long roleId) throws Exception;
	ApiResponse<Role> updateRole(Role role) throws Exception;
	ApiResponse<?> deActiveRole(Role role) throws Exception;
	
//	ApiResponse<MpgUserRole> assignUserRole(MpgUserRole role) throws Exception;
//	List<MpgUserRole> getAssignedUserRoleById(String roleId) throws Exception;
	
}
