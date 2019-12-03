package edu.mum.sonet.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.mum.sonet.models.User;
import edu.mum.sonet.services.UserService;

@Controller
public class UserController {

	private UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(value = "register", method = RequestMethod.POST)
	public @ResponseBody User register(@RequestBody User user) {
		return userService.register(user);
	}

	@RequestMapping(value = "loginTest", method = RequestMethod.POST)
	public @ResponseBody String login(@RequestParam("email") String email, @RequestParam("password") String password) {
		return userService.login(email,password);
	}

	@RequestMapping(value = "user/getUserById", method = RequestMethod.GET)
	public @ResponseBody Optional<User> register(@RequestParam("id") Long id) {
		return userService.findById(id);
	}
}
