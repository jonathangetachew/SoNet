package edu.mum.sonet.controllers;

import edu.mum.sonet.models.User;
import edu.mum.sonet.models.UserNotification;
import edu.mum.sonet.services.FileService;
import edu.mum.sonet.services.UserNotificationService;
import edu.mum.sonet.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;


@Controller
public class MainController {

    private final UserService userService;
    private final FileService fileService;
    private final UserNotificationService userNotificationService;

    public MainController(UserService userService, FileService fileService, UserNotificationService userNotificationService) {
        this.userService = userService;
        this.fileService = fileService;
        this.userNotificationService = userNotificationService;
    }

    /**
     *
     * Get Mappings
     *
     */
    @GetMapping({"/index", "/", ""})
    public String index() {
        return "index";
    }

    @GetMapping("/user/index")
    public String userIndex(Model model) {
        List<UserNotification> notifications = userNotificationService.findAllOrderByIdDesc();
        model.addAttribute("notifications",notifications);
        model.addAttribute("notificationsNumber",notifications.size());
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
