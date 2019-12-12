package edu.mum.sonet.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import edu.mum.sonet.models.User;
import edu.mum.sonet.services.UserService;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user")
public class UserController {

	private UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public @ResponseBody User register(@RequestBody User user) {
		return userService.register(user);
	}

	@RequestMapping(value = "/showProfile", method = RequestMethod.GET)
	public  String showProfile(@RequestParam(value="email") String email, Model model) {
		System.out.println(">>>>> showProfilefor: "+email);
		User user = userService.findByEmail(email);
		if(user != null){
			model.addAttribute("user",user);
			return "user/userProfile";
		}else{
			return "/";
		}
	}

	@PostMapping(value = "/editProfile")
	public  String editProfile(@RequestParam(value="email") String email, Model model) {
		User user = userService.findByEmail(email);
		if(user != null){
			user.setPassword(null);
			model.addAttribute("user",user);
			return "user/editProfile";
		}else{
			return "/";
		}
	}

	@PostMapping(value = "/saveProfileChanges")
	public  String saveProfileChanges(User user, HttpServletRequest request) {
		String rootDirectory = request.getSession().getServletContext().getRealPath("/");
		 user = userService.saveProfileChanges(user,rootDirectory+"profileImages/");
		 return "redirect:/user/showProfile?email="+user.getEmail();
	}
//	@RequestMapping(value = "loginTest", method = RequestMethod.POST)
//	public @ResponseBody String login(@RequestParam("email") String email, @RequestParam("password") String password) {
//		return userService.login(email,password);
//	}
//
//	@RequestMapping(value = "user/getUserById", method = RequestMethod.GET)
//	public @ResponseBody Optional<User> register(@RequestParam("id") Long id) {
//		return userService.findById(id);
//	}
}
