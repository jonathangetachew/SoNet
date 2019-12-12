package edu.mum.sonet.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
	private Authentication authentication;

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
		authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		User targetUser = userService.findByEmail(email);
//		User authenticatedUser = userService.findByEmail(currentPrincipalName);

		if(targetUser != null){
			if(!targetUser.getEmail().equals(currentPrincipalName)){
				Boolean follow = userService.isAuthenticatedUserFollowUser(currentPrincipalName,targetUser);
				model.addAttribute("follow",follow);
			}
			model.addAttribute("user",targetUser);
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

	@GetMapping(value = "/follow")
	public String follow(@RequestParam("email") String email){
		authentication = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(">>> follow user: "+email);
		String currentPrincipalName = authentication.getName();
		System.out.println(">>> authenticated user: "+currentPrincipalName);
		userService.follow(currentPrincipalName,email);
		return "redirect:/user/showProfile?email="+email;
	}

	@GetMapping(value = "/unfollow")
	public String unfollow(@RequestParam("email") String email){
		authentication = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(">>> unfollow user: "+email);
		String currentPrincipalName = authentication.getName();
		System.out.println(">>> authenticated user: "+currentPrincipalName);
		userService.unfollow(currentPrincipalName,email);
		return "redirect:/user/showProfile?email="+email;
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
