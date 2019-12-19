package edu.mum.sonet.controllers;

import edu.mum.sonet.models.Advertisement;
import edu.mum.sonet.services.AdvertisementService;
import edu.mum.sonet.services.FileService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Created by Jonathan on 12/10/2019.
 */

@Controller
@RequestMapping(value = AdvertisementController.BASE_URL)
public class AdvertisementController {

	public static final String BASE_URL = "admin/dashboard/advertisements";

	private final AdvertisementService advertisementService;

	private final FileService fileService;

	public AdvertisementController(AdvertisementService advertisementService, FileService fileService) {
		this.advertisementService = advertisementService;
		this.fileService = fileService;
	}
	/**
	 * Get Mappings
	 */

	@GetMapping(value = {"/", ""})
	public String getAllAdvertisements(Model model) {
		model.addAttribute("advertisements", advertisementService.findAll());

		return "advertisement/list";
	}

	@GetMapping("/add")
	public String addAdvertisementForm(@ModelAttribute("newAdvertisement") Advertisement advertisement) {
		return "advertisement/add";
	}

	@GetMapping("/{id}/delete")
	public String deleteAdvertisementById(@PathVariable @Valid Long id) {
		advertisementService.deleteById(id);

		return "redirect:/" + AdvertisementController.BASE_URL;
	}

	/**
	 * Post mappings
	 */
	@PostMapping("/add")
	public String addAdvertisement(@ModelAttribute("newAdvertisement") @Valid Advertisement advertisement,
								   BindingResult bindingResult, HttpServletRequest request, Model model) {
		///> Handle Errors
		if (bindingResult.hasErrors()) {
			model.addAttribute("errors", bindingResult.getAllErrors());
			return "advertisement/add";
		}

		String rootDirectory = request.getSession().getServletContext().getRealPath("/");
		String imageUrl = fileService.saveFile(advertisement.getImageFile(), rootDirectory + "adImages/");
		advertisement.setContentUrl(imageUrl);
		advertisementService.save(advertisement);
		return "redirect:/" + AdvertisementController.BASE_URL;
	}
}
