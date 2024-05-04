package com.becoder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.becoder.entity.User;

@Repository
public interface UserRepo  extends JpaRepository<User, Integer>{
	
	public User findByEmail(String email);
	
	
	public User findByVerificationCode(String code);
	

}
