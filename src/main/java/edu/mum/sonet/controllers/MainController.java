package edu.mum.sonet.controllers;

import edu.mum.sonet.models.User;
import edu.mum.sonet.services.FileService;
import edu.mum.sonet.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@Controller
public class MainController {

    private final UserService userService;
    private final FileService fileService;

    public MainController(UserService userService, FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;
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
    public String userIndex() {
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
    public String doSignup(User user, HttpServletRequest request) {
        String rootDirectory = request.getSession().getServletContext().getRealPath("/");
        String imageUrl = fileService.saveFile(user.getImageFile(),rootDirectory+"profileImages/");
        user.setImageUrl(imageUrl);
        User u = userService.register(user);
        return "redirect:/login";
    }
}
