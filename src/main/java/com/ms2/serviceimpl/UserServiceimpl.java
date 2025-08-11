package com.ms2.serviceimpl;

import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ms2.dto.APIResponse;
import com.ms2.dto.UserDto;
import com.ms2.entity.User;
import com.ms2.ripository.UserRepository;
import com.ms2.service.UserService;

@Service
public class UserServiceimpl implements UserService{
	
	private UserRepository repository;
	private PasswordEncoder passwordEncoder;
	
	public UserServiceimpl(UserRepository repository,PasswordEncoder passwordEncoder) {
		this.repository=repository;
		this.passwordEncoder=passwordEncoder;
	}

	@Override
	public APIResponse<String> registerMethod(UserDto dto) {
		
		APIResponse<String> response=new APIResponse<>();
		
		if(repository.existsByUsername(dto.getUsername())) {
			response.setMessage("Duplicate Entry");
			response.setStatus(409);
			response.setData("Username already exists");
			return response;
		}
		
		if(repository.existsByEmail(dto.getEmail())) {
			response.setMessage("Duplicate Entry");
			response.setStatus(409);
			response.setData("Email already exists");
			return response;
		}
		User user=new User();
		BeanUtils.copyProperties(dto, user);
		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		repository.save(user);
		
		response.setMessage("Success");
		response.setStatus(201);
		response.setData("User Registered");
		
		return response;
	}

}
