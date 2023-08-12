package com.education.controller;


import com.education.entity.Course;
import com.education.entity.User;
import com.education.repository.CourseRepo;
import com.education.repository.ImageRepo;
import com.education.repository.UserRepo;
import com.education.service.CourseService;
import com.education.service.ImageService;
import com.education.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private UserRepo userRepo;

//	@Autowired
//	private CourseRepo courseRepo;
	@Autowired
	private UserService userService;
@Autowired
private ImageService imageService;

	@Autowired
	private CourseService courseService;

	@ModelAttribute
	public void commonUser(Principal p, Model m) {
		if (p != null) {
			String email = p.getName();
			User user = userRepo.findByEmail(email);

			m.addAttribute("user", user);


		}
	}

	@GetMapping("/profile")
	public String profile( Model model) {

		long numberOfUsers = userService.getTotalNumberOfUsers();
//		int numberOfEvents = eventService.getTotalNumberOfEvents();
		long numberOfCourses = courseService.getTotalNumberOfCourses();

		model.addAttribute("numberOfUsers", numberOfUsers);
//		model.addAttribute("numberOfEvents", numberOfEvents);
		model.addAttribute("numberOfCourses", numberOfCourses);


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

	@GetMapping("/courses")
	public String courses(Model model) {
		List<Course> courses = courseService.getAllCourses();
		model.addAttribute("courses", courses);
		model.addAttribute("newCourse", new Course());
		return "admin/courses";
	}

	@PostMapping("/add-course")
	public String addCourse(@ModelAttribute Course newCourse,
							@RequestParam("imageFile") MultipartFile imageFile) {
		if (!imageFile.isEmpty()) {
			try {
				String imageName = StringUtils.cleanPath(Objects.requireNonNull(imageFile.getOriginalFilename()));
				Path imagePath = Paths.get(
						"src/main/resources/static/assets/img").resolve(imageName);
				Files.copy(imageFile.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
				newCourse.setImage("/assets/img/" + imageName);
			} catch (IOException e) {
				// Handle the exception
			}
		}

		courseService.saveCourse(newCourse);
		return "redirect:/admin/courses";
	}

	@GetMapping("/delete-course/{id}")
	public String deleteCourse(@PathVariable Long id) {
		courseService.deleteCourse(id);
		return "admin/courses";
	}
}




