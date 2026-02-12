package com.smartwallet.Controller;

import com.smartwallet.dto.LoginDto;
import com.smartwallet.dto.RegisterDto;
import com.smartwallet.model.User;
import com.smartwallet.service.AuthService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
public class Auth {

    private final AuthService authService;

    public Auth(AuthService authService){
        this.authService=authService;
    }

    @PostMapping("/register")
    public User register(@RequestBody RegisterDto request) {
        return authService.register(
                request.getName(),
                request.getEmail(),
                request.getPassword()
        );
    }
    @PostMapping("/login")
    public User login(@RequestBody LoginDto request){
        return authService.login(
                request.getEmail(),
                request.getPassword()
        );
    }

}
