package com.ms2.ripository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ms2.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	public User findUserByUsername(String username);
	public User findUserByEmail(String email);
	public boolean existsByUsername(String Username);
	public boolean existsByEmail(String Username);
}
