package com.smartwallet.Controller;

import com.smartwallet.model.User;
import com.smartwallet.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
public class Auth {

    private final AuthService authService;

    public Auth(AuthService authService){
        this.authService=authService;
    }

    @PostMapping("/register")
    public User register(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String password
    ){
        return authService.register(name,email,password);
    }
    @PostMapping("/login")
    public User login(
            @RequestParam String email,
            @RequestParam String password
    ){
        return authService.login(email,password);

    }

}
