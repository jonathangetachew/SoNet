package edu.mum.sonet.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Jonathan on 12/11/2019.
 */

@Controller
@RequestMapping(UnhealthyContentController.BASE_URL)
public class UnhealthyContentController {

	public static final String BASE_URL = "admin/dashboard/unhealthy-contents";

	@GetMapping(value = {"/", ""})
	public String index() {
		return "unhealthy-content/list";
	}
}
