package com.education.controller;


import com.education.entity.User;
import com.education.repository.ImageRepo;
import com.education.repository.UserRepo;
import com.education.service.ImageService;
import com.education.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private UserRepo userRepo;
	@Autowired
	private UserService userService;
@Autowired
private ImageService imageService;

	@ModelAttribute
	public void commonUser(Principal p, Model m) {
		if (p != null) {
			String email = p.getName();
			User user = userRepo.findByEmail(email);

			m.addAttribute("user", user);


		}
	}

	@GetMapping("/profile")
	public String profile() {
		return "admin/admin_profile";
	}

	@GetMapping("/test")
	public String test(){
		return "admin/test";
	}
	@GetMapping("/users")
	public String users(Principal p, Model model) {
		commonUser(p, model); // Fetch and add user information
		List<User> users = userRepo.findAll();
		model.addAttribute("users", users);
		return "admin/users"; // Update the template name
	}

	@GetMapping("/delete/{id}")
	public String deleteUserById(@PathVariable Long id, Model model) {
//		User user = userRepo.deleteUserById(id);
//		model.addAttribute("deleted user",user);
		userService.deleteUserById(id);
		return "redirect:/admin/users"; // Redirect back to the user list page
	}





	@PostMapping("/upload")
	public String handleImageUpload(@RequestParam("image") MultipartFile file) throws IOException {
		if (!file.isEmpty()) {
			imageService.saveImage(file);
		}
		return "redirect:/";
	}
	}





