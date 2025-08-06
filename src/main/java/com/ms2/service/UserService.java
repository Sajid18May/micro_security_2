package com.ms2.service;

import com.ms2.dto.APIResponse;
import com.ms2.dto.UserDto;

public interface UserService {
	
	public APIResponse<String> registerMethod(UserDto dto);
}
