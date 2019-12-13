package edu.mum.sonet.controllers;

import edu.mum.sonet.models.Claim;
import edu.mum.sonet.models.User;
import edu.mum.sonet.services.ClaimService;
import edu.mum.sonet.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

	public ClaimController(UserService userService,ClaimService claimService) {
		this.userService = userService;
		this.claimService = claimService;
	}


	@GetMapping(value = "/claim")
	public String claim(@RequestParam("text")String text, Model model){
		authentication = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(">>> asking for aclaim:  "+authentication.getName());

		User user = userService.findByEmail(authentication.getName());
		if(user != null){
			Claim claim = new Claim();
			claim.setClaimDate(LocalDate.now());
			claim.setMessage(text);
			claim.setUser(user);
			claimService.save(claim);
			model.addAttribute("message","it will take about two days");
		}else{
			model.addAttribute("message","something went wrong please try to login again");
		}
		return "/login";
	}

}
