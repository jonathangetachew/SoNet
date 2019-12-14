package edu.mum.sonet.controllers;

import edu.mum.sonet.models.Claim;
import edu.mum.sonet.models.Comment;
import edu.mum.sonet.models.User;
import edu.mum.sonet.models.enums.UserStatus;
import edu.mum.sonet.services.ClaimService;
import edu.mum.sonet.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

/**
 * Created by Jonathan on 12/11/2019.
 */

@Controller
@RequestMapping(AdminController.BASE_URL)
public class AdminController {

	public static final String BASE_URL = "/admin";

	private final ClaimService claimService;

	private final UserService userService;

	public AdminController(ClaimService claimService, UserService userService) {
		this.claimService = claimService;
		this.userService = userService;
	}

	@GetMapping("/dashboard")
	public String index() {
		return "admin/index";
	}

	@GetMapping(value = {"/dashboard/claims"})
	public String getClaims(Model model) {

		model.addAttribute("claims", claimService.findAllActiveClaims());

		return "claim/list";
	}

	@GetMapping("/dashboard/claims/{id}/accept")
	public String allowClaim(@PathVariable @Valid Long id) {
		Claim claim = claimService.findById(id);
		User user = claim.getUser();

		///> Reset the user's unhealthyContentCount and re-activate account
		user.setUnhealthyContentCount(0);
		user.setUserStatus(UserStatus.ACTIVE);

		///> Update User
		userService.save(user);

		///> Remove Claim from database
		claimService.deleteById(id);

		return "redirect:/admin/dashboard/claims";
	}

	@GetMapping("/dashboard/claims/{id}/reject")
	public String rejectClaim(@PathVariable @Valid Long id) {
		Claim claim = claimService.findById(id);
		User user = claim.getUser();

		///> Mark Claim as read
		claim.setIsActive(false);

		///> Ban the User Account
		user.setUserStatus(UserStatus.BANNED);

		///> Update User
		userService.save(user);

		return "redirect:/admin/dashboard/claims";
	}
}
