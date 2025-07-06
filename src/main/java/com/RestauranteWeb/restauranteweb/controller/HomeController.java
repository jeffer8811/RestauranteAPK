package com.RestauranteWeb.restauranteweb.controller;

import com.RestauranteWeb.restauranteweb.model.MenuItem;
import com.RestauranteWeb.restauranteweb.model.User;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.RestauranteWeb.restauranteweb.service.MenuItemService;
import com.RestauranteWeb.restauranteweb.service.UserService;


@Controller
public class HomeController {

        private final UserService userService;
    
      private final MenuItemService menuItemService; 

    public HomeController(UserService userService, MenuItemService menuItemService) {
        this.userService = userService;
        this.menuItemService = menuItemService;
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
    
        System.out.println("Registrando usuario: " + user.getUsername() + " - " + user.getPassword());
    
     User savedUser = userService.save(user);
    
    
        System.out.println("Usuario guardado: " + savedUser.getUsername() + " - " + savedUser.getPassword());
    
        return "redirect:/login?success";
    }
@GetMapping("/empleadosHome")
public String empleados() {
    return "empleados"; 
}

    
     @GetMapping("/home")
    public String home(Model model) {
    long totalPlatos = menuItemService.obtenerTodosLosItems().size(); 
    long disponibles = menuItemService.obtenerTodosLosItems()
                                      .stream()
                                      .filter(MenuItem::isDisponible)
                                      .count(); 

    model.addAttribute("totalMenuItems", totalPlatos);
    model.addAttribute("availableMenuItems", disponibles);

    return "home";
}


}
