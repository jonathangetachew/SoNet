package edu.mum.sonet.controllers;

import edu.mum.sonet.models.Claim;
import edu.mum.sonet.models.User;
import edu.mum.sonet.models.AdminNotification;
import edu.mum.sonet.services.AdminNotificationService;
import edu.mum.sonet.services.ClaimService;
import edu.mum.sonet.services.UserService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

/**
 * Created by Jonathan on 12/11/2019.
 */

@Controller
public class ClaimController {

	private UserService userService;
	private ClaimService claimService;
	private Authentication authentication;
	private AdminNotificationService adminNotificationService;


	public ClaimController(UserService userService, ClaimService claimService, AdminNotificationService adminNotificationService) {
		this.userService = userService;
		this.claimService = claimService;
		this.adminNotificationService = adminNotificationService;
	}


	@GetMapping(value = "/claim")
	public String claim(@RequestParam("text") String text, Model model) {
		authentication = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(">>> asking for a claim:  " + authentication.getName());

		User user = userService.findByEmail(authentication.getName());
		if (user != null) {
			Claim claim = new Claim();
			claim.setClaimDate(LocalDate.now());
			claim.setMessage(text);
			claim.setUser(user);
			if(claimService.userHasAClaim(user.getEmail())){
				model.addAttribute("message","You calim is already under review, thank you for sending again");
			}else{
				claimService.save(claim);
				model.addAttribute("message","Your claim was sent Successfully! It might take upto two business days so be patient. Thank you!");
				AdminNotification adminNotification = new AdminNotification("Claim",claim.getMessage(),user);
				adminNotificationService.notifyAdmin(adminNotification);
			}

		}else{
			model.addAttribute("message","Something went wrong please try to login again");
		}
		return "/login";
	}

}
