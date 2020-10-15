package com.bookend.authorizationserver.controller;

import com.bookend.authorizationserver.model.AuthUserDetail;
import com.bookend.authorizationserver.payload.Profile;
import com.bookend.authorizationserver.service.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    private UserDetailServiceImpl userDetailService;
    @Autowired
    public void setUserDetailService(UserDetailServiceImpl userDetailService) {
        this.userDetailService = userDetailService;
    }
    @GetMapping("/{username}")
    public Profile getProfile(@PathVariable("username") String username){
        AuthUserDetail details = (AuthUserDetail) userDetailService.loadUserByUsername(username);
        return new Profile(details.getFirstname(),details.getLastname(),details.getUsername(),details.getEmail());
    }
}
