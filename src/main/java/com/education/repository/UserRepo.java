package com.education.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.education.entity.User;

public interface UserRepo extends JpaRepository<User, Integer> {

	public User findByEmail(String email);

	boolean existsByEmail(String email);
	User findByUsername(String username);


//	User findByEmailOrUsername(String email,String Username);

//	User findByEmailOrUsername(String emailOrUsername);
}
