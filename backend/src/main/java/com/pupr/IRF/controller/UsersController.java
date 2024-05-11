package com.pupr.IRF.controller;

import com.pupr.IRF.model.UsersModel;
import com.pupr.IRF.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UsersController {

    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/register")
    public  String getRegisterPage(Model model){
        model.addAttribute("registerRequest", new UsersModel());
        return "register_page";
    }

    @GetMapping("/login")
    public  String getLoginPage(Model model){
        model.addAttribute("loginRequest", new UsersModel());
        return "login_page";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute UsersModel usersModel, Model model) {
        try {
            // Correct usage of the non-static method via an instance called usersService
            UsersModel registeredUser = usersService.registerUser(usersModel.getUsername(), usersModel.getPassword(), usersModel.getEmail());
            return "redirect:/login";
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("registerRequest", usersModel);
            return "register_page"; // Redirect back to registration page with error message
        } catch (Exception e) {
            model.addAttribute("errorMessage", "An error occurred during registration.");
            return "error_page";
        }
    }

    @PostMapping("/login")
    public String login(@ModelAttribute UsersModel usersModel, Model model){
        System.out.println("login request: " + usersModel);
        UsersModel authenticated = usersService.authenticate(usersModel.getUsername(), usersModel.getPassword());
        if (authenticated != null) {
            model.addAttribute("userLogin", authenticated.getUsername());
            return "personal_page";
        }else {
            return "error_page";
        }
    }
}
