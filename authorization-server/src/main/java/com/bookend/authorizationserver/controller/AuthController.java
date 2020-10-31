package com.bookend.authorizationserver.controller;

import com.bookend.authorizationserver.payload.SignUpRequest;
import com.bookend.authorizationserver.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/oauth")
public class AuthController {

    @Autowired
    UserService userService;
    @ApiOperation(value = "New User Registration", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully registered user"),
            @ApiResponse(code = 400, message = "User name or email is already in use.")
    })
    @PostMapping("sign-up")
    public ResponseEntity signUp(@RequestBody SignUpRequest signUpRequest){
        userService.addUser(signUpRequest);
        return ResponseEntity.ok("user registered. need confirmation");
    }
    @ApiOperation(value = "Confirm User Email", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully confirmation of an email")
    })
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
