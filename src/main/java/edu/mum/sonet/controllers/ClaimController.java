package edu.mum.sonet.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Jonathan on 12/11/2019.
 */

@Controller
@RequestMapping(ClaimController.BASE_URL)
public class ClaimController {

	public static final String BASE_URL = "/admin/dashboard/claims";

	@GetMapping(value = {"/", ""})
	public String index() {
		return "claim/list";
	}
}
