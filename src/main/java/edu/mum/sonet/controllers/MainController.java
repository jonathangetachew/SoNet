package edu.mum.sonet.controllers;

import edu.mum.sonet.models.Advertisement;
import edu.mum.sonet.models.User;
import edu.mum.sonet.models.UserNotification;
import edu.mum.sonet.models.UserNotificationJoin;
import edu.mum.sonet.services.AdvertisementService;
import edu.mum.sonet.services.FileService;
import edu.mum.sonet.services.UserNotificationService;
import edu.mum.sonet.services.UserService;
import edu.mum.sonet.util.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;


@Controller
public class MainController {

    private final UserService userService;
    private final FileService fileService;
    private final UserNotificationService userNotificationService;
    private final AdvertisementService advertisementService;

    public MainController(UserService userService, FileService fileService, UserNotificationService userNotificationService, AdvertisementService advertisementService) {
        this.userService = userService;
        this.fileService = fileService;
        this.userNotificationService = userNotificationService;
        this.advertisementService = advertisementService;
    }

    private User getCurrentUser() throws UsernameNotFoundException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    /**
     * Get Mappings
     */
    @GetMapping({"/index", "/", ""})
    public String index() {
        return "index";
    }

    @GetMapping("/user/index")
    public String userIndex(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        List<UserNotificationJoin> notifications = userNotificationService.getUserNotifications(email);
        model.addAttribute("notifications", notifications);
        model.addAttribute("notificationsNumber", notifications.size());
        Set<Advertisement> ads = this.advertisementService.getAdsForUser(getCurrentUser(), PageRequest.of(0, 5));
        List<Set<Advertisement>> splitSets = StringUtils.split(ads, 2);
        model.addAttribute("ads_left", splitSets.get(0));
        model.addAttribute("ads_right", splitSets.get(1));
        System.out.println(">>>> show user home page <<<<");
        return "user/index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signup(@ModelAttribute("user") User user) {
        return "signup";
    }

    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

    /**
     *
     * Post Mappings
     *
     */

    @PostMapping("/signup")
    public String doSignup(@Valid User user, BindingResult bindingResult, Model model, HttpServletRequest request) {

        ///> Handle Errors
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "signup";
        }

        String rootDirectory = request.getSession().getServletContext().getRealPath("/");
        String imageUrl = fileService.saveFile(user.getImageFile(),rootDirectory+"profileImages/");
        user.setImageUrl(imageUrl);
        User u = userService.register(user);
        return "redirect:/login";
    }
}
