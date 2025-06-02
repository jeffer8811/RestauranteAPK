package com.RestauranteWeb.restauranteweb.controller;

import com.RestauranteWeb.restauranteweb.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.RestauranteWeb.restauranteweb.service.UserService;


@Controller
public class HomeController {

        private final UserService userService;
    
    public HomeController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }
    
    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }
    
    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {
    // Verifica en consola antes de guardar
        System.out.println("Registrando usuario: " + user.getUsername() + " - " + user.getPassword());
    
     User savedUser = userService.save(user);
    
    // Verifica después de guardar
        System.out.println("Usuario guardado: " + savedUser.getUsername() + " - " + savedUser.getPassword());
    
        return "redirect:/login?success";
    }

    
    
    @GetMapping("/home")
    public String home() {
        return "home";
    }
    
}
