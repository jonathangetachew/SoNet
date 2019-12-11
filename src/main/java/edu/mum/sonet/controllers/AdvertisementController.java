package edu.mum.sonet.controllers;

import edu.mum.sonet.models.Advertisement;
import edu.mum.sonet.services.AdvertisementService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by Jonathan on 12/10/2019.
 */

@Controller
@RequestMapping(value = "/advertisements")
public class AdvertisementController {

	private final AdvertisementService advertisementService;

	public AdvertisementController(AdvertisementService advertisementService) {
		this.advertisementService = advertisementService;
	}

	@GetMapping("/add")
	public String addAdvertisement(@ModelAttribute("newAdvertisement") Advertisement advertisement) {
		return "advertisement/add";
	}
}
