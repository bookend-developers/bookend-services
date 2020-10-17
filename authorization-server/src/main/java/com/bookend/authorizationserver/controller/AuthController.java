package com.bookend.authorizationserver.controller;

import com.bookend.authorizationserver.payload.SignUpRequest;
import com.bookend.authorizationserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/oauth")
public class AuthController {

    @Autowired
    UserService userService;

    @PostMapping("sign-up")
    public ResponseEntity signUp(@RequestBody SignUpRequest signUpRequest){
        userService.addUser(signUpRequest);
        return ResponseEntity.ok("user registered. need confirmation");
    }

    @GetMapping("confirm/{token}")
    public ResponseEntity confirm(@PathVariable("token") String token){

        userService.confirm(token);
        return ResponseEntity.ok("Confirmation completed");
    }
    @GetMapping("test")
    public String test() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            return currentUserName;
        }
        return "hata";
    }
}
