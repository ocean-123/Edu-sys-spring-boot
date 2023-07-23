package com.education.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.education.entity.User;
import com.education.repository.UserRepo;

import jakarta.servlet.http.HttpSession;

@Service
public  class UserServiceImpl implements UserService {



	@Autowired
	private UserRepo userRepo;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;






	@Override
	public User saveUser(User user) {

		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		String encodedConfirmPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedConfirmPassword);


		return userRepo.save(user);
	}


	@Override
	public void removeSessionMessage() {

		HttpSession session = ((ServletRequestAttributes) (RequestContextHolder.getRequestAttributes())).getRequest()
				.getSession();

		session.removeAttribute("message");
	}

//	@Override
//	public User findByEmailOrUsername(String emailOrUsername) {
//		User user = userRepo.findByEmailOrUsername(emailOrUsername,emailOrUsername);
//		return user;
//	}

}
