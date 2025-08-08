package com.ms2.service;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Service
public class JWTService {
	
	private static final String SECRET_KEY="my-secret-key";
	private static final long EXPIRE_TIME=86400000;
	
	public String generateToken(String username,String role) {
		return JWT
				.create()
				.withSubject(username)
				.withClaim("role", role)
				.withIssuedAt(new Date())
				.withExpiresAt(new Date(System.currentTimeMillis()+EXPIRE_TIME))
				.sign(Algorithm.HMAC256(SECRET_KEY));
				
	}
}
