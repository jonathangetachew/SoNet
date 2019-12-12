package edu.mum.sonet.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Jonathan on 12/11/2019.
 */

@Controller
@RequestMapping(AdminController.BASE_URL)
public class AdminController {

	public static final String BASE_URL = "/admin";

	@GetMapping("/dashboard")
	public String index() {
		return "admin/index";
	}

}
