package com.education.controller;


import com.education.entity.Country;
import com.education.entity.Course;
import com.education.entity.User;
import com.education.repository.UserRepo;
import com.education.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

	@Autowired
	private CountryInfo countryInfo;
	@Autowired
	private UserService userService;

	@Autowired
	private course courseService;
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
	public String profile( Model model) {

		long numberOfUsers = userService.getTotalNumberOfUsers();
//		int numberOfEvents = eventService.getTotalNumberOfEvents();
		long numberOfCourses = courseService.getTotalNumberOfCourses();

		model.addAttribute("numberOfUsers", numberOfUsers);
//		model.addAttribute("numberOfEvents", numberOfEvents);
		model.addAttribute("numberOfEvents", numberOfCourses);


		return "admin/admin_profile";
	}

	@GetMapping("Profile")
	public  String profile_admin(){
		return "admin/profile";
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



//controlling Profile picture

	@PostMapping("/upload")
	public String handleImageUpload(@RequestParam("image") MultipartFile file) throws IOException {
		if (!file.isEmpty()) {
			imageService.saveImage(file);
		}
		return "redirect:/";
	}


//	controlling events
	@GetMapping("/courses")
	public String courses(Model model) {
		List<Course> courses = courseService.getAllCourses();
		model.addAttribute("courses", courses);
		model.addAttribute("newCourse", new Course());
		model.addAttribute("courseData", courses);


		return "admin/courses";
	}

	@GetMapping("/profileadmin")
	@ResponseBody // Indicate that the returned object should be used as the response body
	public ResponseEntity<List<Course>> Cprofile() {
		List<Course> categoryDataList =courseService.getAllCourses();

		// Get the data from your service or repository
		// Populate the categoryDataList with objects containing catName and catCount

		return ResponseEntity.ok(categoryDataList);
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



	// controlling country info


	@PostMapping("/add-country")
	public String addConInfo(@ModelAttribute Country country,
							 @RequestParam("imageFile2") MultipartFile imageFile,
							 @RequestParam("universityImageFile") MultipartFile universityImageFile) {
		if (!imageFile.isEmpty() && !universityImageFile.isEmpty()) {
			try {
				String imageName = StringUtils.cleanPath(Objects.requireNonNull(imageFile.getOriginalFilename()));
				Path imagePath = Paths.get("src/main/resources/static/assets/img").resolve(imageName);
				Files.copy(imageFile.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
				country.setCountryImage("/assets/img/" + imageName);

				String universityImageName = StringUtils.cleanPath(Objects.requireNonNull(universityImageFile.getOriginalFilename()));
				Path universityImagePath = Paths.get("src/main/resources/static/assets/img").resolve(universityImageName);
				Files.copy(universityImageFile.getInputStream(), universityImagePath, StandardCopyOption.REPLACE_EXISTING);
				country.setUniversityImage("/assets/img/" + universityImageName);
			} catch (IOException e) {
				// Handle the exception
			}
		}

		countryInfo.saveCountryInfo(country);
		return "redirect:/admin/country";
	}
	@GetMapping("/country")
	public String country(Model model) {
		List<Country> countries = countryInfo.getAllCountryInfo();
		model.addAttribute("countries", countries);
		model.addAttribute("newCountry", new Country());



		return "admin/country";
	}

	@GetMapping("/delete-country/{id}")
	public String deleteCountryInfo(@PathVariable Long id) {
		countryInfo.deleteCountryInfo(id);
		return "admin/country";
	}


}




