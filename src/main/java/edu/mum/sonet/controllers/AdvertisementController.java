package edu.mum.sonet.controllers;

import edu.mum.sonet.models.Advertisement;
import edu.mum.sonet.models.User;
import edu.mum.sonet.services.AdvertisementService;
import edu.mum.sonet.services.FileService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Jonathan on 12/10/2019.
 */

@Controller
@RequestMapping(value = AdvertisementController.BASE_URL)
public class AdvertisementController {

	public static final String BASE_URL = "/advertisements";

	private final AdvertisementService advertisementService;

	private final FileService fileService;

	public AdvertisementController(AdvertisementService advertisementService, FileService fileService) {
		this.advertisementService = advertisementService;
		this.fileService = fileService;
	}

	/**
	 *
	 * Get Mappings
	 *
	 * @param advertisement
	 * @return
	 */

	@GetMapping("/add")
	public String addAdvertisementForm(@ModelAttribute("newAdvertisement") Advertisement advertisement) {
		return "advertisement/add";
	}

	/**
	 *
	 * Post mappings
	 *
	 * @param advertisement
	 * @param request
	 * @return
	 */
	@PostMapping("/add")
	public String addAdvertisement(@ModelAttribute("newAdvertisement") Advertisement advertisement, HttpServletRequest request) {
		String rootDirectory = request.getSession().getServletContext().getRealPath("/");
		String imageUrl = fileService.saveFile(advertisement.getImageFile(),rootDirectory+"adImages/");
		advertisement.setContentUrl(imageUrl);
		advertisementService.save(advertisement);
		return "redirect:" + AdvertisementController.BASE_URL;
	}
}
