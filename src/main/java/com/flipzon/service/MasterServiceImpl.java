package com.flipzon.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flipzon.dto.ApiResponse;
import com.flipzon.entity.Role;
import com.flipzon.repository.RoleRepository;
import com.flipzon.repository.UserRepository;
import com.flipzon.utility.FlipZonUtility;

@Service
public class MasterServiceImpl implements MasterService {

	@Autowired RoleRepository roleRepository;
	@Autowired UserRepository userRepository;
	
	
	@Override
	public ApiResponse<Role> createRole(Role role) throws Exception {
		return new ApiResponse<Role>(200, "Role saved!", roleRepository.save(role));
	}

	@Override
	public List<Role> getAllRoles() throws Exception {
		return roleRepository.findAllByOrderByName();
	}

	@Override
	public Role getRoleById(long roleId) throws Exception {
		return roleRepository.findById(roleId).orElseThrow(()->new RuntimeException("Role not found"));
	}

	@Override
	public ApiResponse<Role> updateRole(Role mstRole) throws Exception {
		Role role = roleRepository.findById(mstRole.getId()).orElseThrow(()->new RuntimeException("Role not found"));
		return new ApiResponse<Role>(200, "Role updated successfully!", roleRepository.save(role));
	}
	
	@Override
	public ApiResponse<?> deActiveRole(Role role) throws Exception {
		int count = roleRepository.updateRoleStatus(role.getStatus(), role.getId());
		if(count > 0) {
			return new ApiResponse<>(200, "Role "+FlipZonUtility.getStatusMessage(role.getStatus())+" successfully!", null);
		}else {
		}
		return new ApiResponse<>(500, "Role not "+FlipZonUtility.getStatusMessage(role.getStatus())+"!", null);
	}

	//User Role Mapping
//	@Override
//	public ApiResponse<MpgUserRole> assignUserRole(MpgUserRole mpgUserRole) throws Exception {
////		userRepository.findById(mpgUserRole.getUser_id()).orElseThrow(()->new RuntimeException("User not found"));
////		roleRepository.findById(mpgUserRole.getRole_id()).orElseThrow(()->new RuntimeException("Role not found"));
////		
////		mpgUserRole.setCreatedBy(FlipZonUtility.getCurrentUserId());
//		return new ApiResponse<MpgUserRole>(200, "User mapped!", mpgUserRoleRepo.save(mpgUserRole));
//	}
//
//	@Override
//	public List<MpgUserRole> getAssignedUserRoleById(String roleId) throws Exception {
//		return mpgUserRoleRepo.findAllByOrderByUserId();
//	}

}
