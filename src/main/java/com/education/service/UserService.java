package com.education.service;


import com.education.entity.User;

public interface UserService {

	public User saveUser(User user);

	public void removeSessionMessage();

}
