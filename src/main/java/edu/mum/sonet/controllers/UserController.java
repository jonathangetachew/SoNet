package edu.mum.sonet.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import edu.mum.sonet.models.Claim;
import edu.mum.sonet.models.Post;
import edu.mum.sonet.models.UserNotification;
import edu.mum.sonet.services.ClaimService;
import edu.mum.sonet.services.UserNotificationService;
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

public class UserController {

	private final UserService userService;
	private final ClaimService claimService;
	private final UserNotificationService userNotificationService;
	private Authentication authentication;

	@Autowired
	public UserController(UserService userService,ClaimService claimService, UserNotificationService userNotificationService) {
		this.userService = userService;
		this.claimService = claimService;
		this.userNotificationService = userNotificationService;
	}

	@RequestMapping(value = "/user/register", method = RequestMethod.POST)
	public @ResponseBody User register(@RequestBody User user) {
		return userService.register(user);
	}

	@RequestMapping(value = "/user/showProfile", method = RequestMethod.GET)
	public  String showProfile(@RequestParam(value="email") String email, Model model) {

		authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		User targetUser = userService.findByEmail(email);
		List<UserNotification> notifications = userNotificationService.getUserNotifications(currentPrincipalName);
		model.addAttribute("notifications",notifications);
		model.addAttribute("notificationsNumber",notifications.size());

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

	@PostMapping(value = "/user/editProfile")
	public  String editProfile(@RequestParam(value="email") String email, Model model) {
		List<UserNotification> notifications = userNotificationService.getUserNotifications(email);
		model.addAttribute("notifications",notifications);
		model.addAttribute("notificationsNumber",notifications.size());
		User user = userService.findByEmail(email);
		if(user != null){
			user.setPassword(null);
			model.addAttribute("user",user);
			return "user/editProfile";
		}else{
			return "/";
		}
	}

	@PostMapping(value = "/user/saveProfileChanges")
	public  String saveProfileChanges(User user, HttpServletRequest request) {
		String rootDirectory = request.getSession().getServletContext().getRealPath("/");
		 user = userService.saveProfileChanges(user,rootDirectory+"profileImages/");
		 return "redirect:/user/showProfile?email="+user.getEmail();
	}

	@GetMapping(value = "/user/follow")
	public String follow(@RequestParam("email") String email){
		authentication = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(">>> follow user: "+email);
		String currentPrincipalName = authentication.getName();
		System.out.println(">>> authenticated user: "+currentPrincipalName);
		userService.follow(currentPrincipalName,email);
		return "redirect:/user/showProfile?email="+email;
	}

	@GetMapping(value = "/user/unfollow")
	public String unfollow(@RequestParam("email") String email){
		authentication = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(">>> unfollow user: "+email);
		String currentPrincipalName = authentication.getName();
		System.out.println(">>> authenticated user: "+currentPrincipalName);
		userService.unfollow(currentPrincipalName,email);
		return "redirect:/user/showProfile?email="+email;
	}

	@GetMapping(value = "/user/blocked")
	public String blockedPage(@RequestParam("num") Long unhealthyContetntNumber, Model model){
		authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		System.out.println(" blocked User: "+currentPrincipalName);

		Boolean canClaim = claimService.userHasAClaim(currentPrincipalName);
		System.out.println("   cain claim:" +canClaim);
		if(canClaim){
			model.addAttribute("message","You calim is already under review, thank you for sending again");
		}else{
			model.addAttribute("number",unhealthyContetntNumber);
		}

		return "/login";
	}

	@GetMapping(value = "/user/banned")
	public String blockedPage( Model model){
		model.addAttribute("banned","your account is banned");
		return "/login";
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
