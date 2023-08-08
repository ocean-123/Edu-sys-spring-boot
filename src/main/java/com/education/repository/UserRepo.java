package com.education.repository;


import com.education.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer> {


	public User findByEmail(String email);

	boolean existsByEmail(String email);

}
