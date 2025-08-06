package com.ms2.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ms2.dto.APIResponse;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/sm/v1/admin")
public class AdminController {
	
	@PostMapping("/welcome")
	public ResponseEntity<APIResponse<String>> welcome() {
		APIResponse<String> response=new APIResponse<>();
		response.setMessage("Welcome Admin");
		response.setData("Welcome to the Admin Access");
		response.setStatus(200);
		
		return new ResponseEntity<>(response,HttpStatusCode.valueOf(response.getStatus()));
	}
	
}
