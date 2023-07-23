package com.education.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.education.entity.User;
import com.education.repository.UserRepo;
import com.education.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepo userRepo;

	@ModelAttribute
	public void commonUser(Principal p, Model m) {
		if (p != null) {
			String email = p.getName();
			User user = userRepo.findByEmail(email);
			m.addAttribute("user", user);
		}

	}

	@GetMapping("/index")
	public String index() {
		return "index";
	}

	@GetMapping("/register")
	public String register() {
		return "register";
	}

	@GetMapping("/signin")
	public String login() {
		return "login";
	}

	/*
	 * @GetMapping("/user/profile") public String profile(Principal p, Model m) {
	 * String email = p.getName(); User user = userRepo.findByEmail(email);
	 * m.addAttribute("user", user); return "profile"; }
	 * 
	 * @GetMapping("/user/home") public String home() { return "home"; }
	 */

	@PostMapping("/saveUser")
	public String saveUser(@ModelAttribute User user, HttpSession session, Model m) {
		// Check if any user exists in the database with the same email
		if (userRepo.existsByEmail(user.getEmail())) {
			session.setAttribute("message", "Email already exists");
			return "register";
		}

		//check if any user have same username
		if (userRepo.existsByEmail(user.getUsername())) {
			session.setAttribute("message", "username already exists");
			return "register";
		}


		// Check if any required fields are empty
		if (user.getUsername().isEmpty() || user.getFirst_name().isEmpty() || user.getLast_name().isEmpty()
				|| user.getEmail().isEmpty() || user.getMobileNo().isEmpty() || user.getDob() == null
				|| user.getPassword().isEmpty() || user.getConfirm_password().isEmpty()) {
			session.setAttribute("message", "All fields are required");
			return "register";
		}

		// Check if password and confirm password match
		if (!user.getPassword().equals(user.getConfirm_password())) {
			session.setAttribute("message", "Passwords do not match");
			return "register";
		}

		// Check if the username is alphanumeric and within the allowed length
		if (!user.getUsername().matches("^[a-zA-Z0-9]+$") || user.getUsername().length() < 6) {
			session.setAttribute("message", "Username must be alphanumeric and minimum 6 characters");
			return "register";
		}

		// Check if the password is within the allowed length
		if (user.getPassword().length() > 20 || user.getPassword().length() < 6) {
			session.setAttribute("message", "Password must be between 6 and 20 characters");
			return "register";
		}

		// Set role based on the number of users in the database
		if (userRepo.count() == 0) {
			user.setRole("ROLE_ADMIN");
		} else {
			user.setRole("ROLE_USER");
		}

		// Save the user to the database
		User u = userService.saveUser(user);

		if (u != null) {
			session.setAttribute("message", "Register successfully");
		} else {
			session.setAttribute("message", "Something wrong server");
		}

		return "redirect:/register";
	}


}
