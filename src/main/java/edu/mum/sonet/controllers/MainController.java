package edu.mum.sonet.controllers;

import edu.mum.sonet.models.User;
import edu.mum.sonet.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@Controller
public class MainController {

    private UserService userService;

    @Autowired
    MainController(UserService userService){
        this.userService = userService;
    }

    @RequestMapping("/")
    public String root() {
        return "redirect:/index";
    }

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/user/index")
    public String userIndex() {
        return "user/index";
    }

    @RequestMapping("/login")
    public String login(@ModelAttribute("userBean")User user) {
        return "login";
    }

//    @RequestMapping(value="/login", method = RequestMethod.POST)
//    public String approveLogin(@ModelAttribute("userBean")User user) {
//        System.out.println(">>> approve login by modelAtrribute : "+user.getEmail()+ "   -password: "+user.getPassword());
//        String token = userService.login(user.getEmail(), user.getPassword());
////        model.addAttribute("token",token);
//        return  "/user/index";
//    }
//
//    @RequestMapping(value = "/signin", method = RequestMethod.POST)
//    public String doLogin(@RequestParam("username") String email, @RequestParam("password") String password, Model model) {
//        System.out.println(">>> do signin : "+email+ "   -password: "+password);
//        String token = userService.login(email, password);
//        model.addAttribute("token",token);
//        return  "/user/index";
//
//    }
//
//    @RequestMapping(value = "/approvelogin", method = RequestMethod.GET)
//    public String doLogin(Model model) {
//        System.out.println(">>> do login ----> get : without email and password");
////        String token = userService.login(email, password);
////        model.addAttribute("token",token);
//        return  "/user/index";
//
//    }

    @RequestMapping("/login-error")
    public String loginError(Model model) {
        System.out.println(">>>> show /login-error");
        model.addAttribute("loginError", true);
        return "login";
    }
}
