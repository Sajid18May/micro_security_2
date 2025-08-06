package com.ms2.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ms2.dto.APIResponse;
import com.ms2.dto.LoginDto;
import com.ms2.dto.UserDto;
import com.ms2.serviceimpl.UserServiceimpl;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/sm/v1")
public class UserController {

	private UserServiceimpl serviceimpl;
	private AuthenticationManager authenticationManager;

	UserController(UserServiceimpl serviceimpl,AuthenticationManager authenticationManager) {
		this.serviceimpl = serviceimpl;
		this.authenticationManager=authenticationManager;
	}

	@PostMapping("/login")
	public ResponseEntity<APIResponse<String>> userLogin(@RequestBody LoginDto dto) {

		APIResponse<String> response = new APIResponse<>();

		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(dto.getUsername(),
				dto.getPassword());
		
		try {
			Authentication authentication=authenticationManager.authenticate(token);
			if(authentication.isAuthenticated()) {
				response.setMessage("Hello "+dto.getUsername());
				response.setStatus(200);
				response.setData("Login Succesful");
				return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatus()));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		response.setMessage("Failed");
		response.setStatus(401);
		response.setData("Un-Authorized Access");
		return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatus()));
	}

	@PostMapping("/register")
	public ResponseEntity<APIResponse<String>> userRegistration(@RequestBody UserDto dto) {

		APIResponse<String> response = serviceimpl.registerMethod(dto);

		return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatus()));
	}

}
