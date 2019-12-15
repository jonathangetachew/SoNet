package edu.mum.sonet.controllers;

import edu.mum.sonet.models.AdminNotification;
import edu.mum.sonet.services.AdminNotificationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by Jonathan on 12/11/2019.
 */

@Controller
@RequestMapping(AdminController.BASE_URL)
public class AdminController {

	public static final String BASE_URL = "/admin";

	private AdminNotificationService adminNotificationService;

	public AdminController(AdminNotificationService adminNotificationService) {
		this.adminNotificationService = adminNotificationService;
	}

	@GetMapping("/dashboard")
	public String index(Model model) {
		List<AdminNotification> notifications = adminNotificationService.findAllOrderByIdDesc();
		model.addAttribute("notifications",notifications);
		return "admin/index";
	}

	@GetMapping(value = {"/dashboard/claims"})
	public String claims() {
		return "claim/list";
	}

}
