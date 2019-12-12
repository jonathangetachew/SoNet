package edu.mum.sonet.controllers;

import edu.mum.sonet.models.User;
import edu.mum.sonet.services.FileService;
import edu.mum.sonet.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@Controller
public class MainController {

    private UserService userService;
    private FileService fileService;

    @Autowired
    MainController(UserService userService, FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;
    }

    @RequestMapping("/")
    public String root() {
        return "redirect:/index";
    }

    @RequestMapping(value = {"/index", "", "/"}, method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping("/user/index")
    public String userIndex() {
        return "user/index";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/signup")
    public String signup(@ModelAttribute("user") User user) {
        return "signup";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String doSignup(User user, HttpServletRequest request) {
        String rootDirectory = request.getSession().getServletContext().getRealPath("/");
        String imageUrl = fileService.saveFile(user.getImageFile(),rootDirectory+"profileImages/");
        user.setImageUrl(imageUrl);
        User u = userService.register(user);
        return "redirect:/login";
    }

    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }
}
