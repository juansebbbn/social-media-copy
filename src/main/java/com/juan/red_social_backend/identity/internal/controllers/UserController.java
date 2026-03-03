package com.juan.red_social_backend.identity.internal.controllers;

import com.juan.red_social_backend.identity.internal.dto.AuthDto;
import com.juan.red_social_backend.identity.internal.dto.ResponseLogIn;
import com.juan.red_social_backend.identity.internal.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final UserService us;

    public UserController(UserService userService){
        this.us = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthDto auth_dto){
        if(auth_dto.getEmail() == null){
            return ResponseEntity.badRequest().body("Null email");
        }

        if(auth_dto.getPassword() == null){
            return ResponseEntity.badRequest().body("Null password");
        }

        if(auth_dto.getUsername() == null){
            return ResponseEntity.badRequest().body("Null username");
        }

        us.register(auth_dto);

        return ResponseEntity.status(HttpStatus.CREATED).body("User added");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthDto auth_dto){
        if(auth_dto.getEmail() == null){
            return ResponseEntity.badRequest().body("Null email");
        }

        if(auth_dto.getPassword() == null){
            return ResponseEntity.badRequest().body("Null password");
        }

        String token = us.login(auth_dto);

        return ResponseEntity.ok(new ResponseLogIn(auth_dto.getEmail(), token, "Usuario logeado con exito"));
    }

}
