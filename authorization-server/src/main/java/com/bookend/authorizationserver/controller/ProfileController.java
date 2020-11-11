package com.bookend.authorizationserver.controller;

import com.bookend.authorizationserver.model.AuthUserDetail;
import com.bookend.authorizationserver.model.User;
import com.bookend.authorizationserver.payload.Profile;
import com.bookend.authorizationserver.repository.UserDetailRepository;
import com.bookend.authorizationserver.service.UserDetailServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {
    private UserDetailServiceImpl userDetailService;
    @Autowired
    private UserDetailRepository userDetailRepository;
    @Autowired
    public void setUserDetailService(UserDetailServiceImpl userDetailService) {
        this.userDetailService = userDetailService;
    }
    @ApiOperation(value = "Get user profile", response = Profile.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved profile "),
            @ApiResponse(code = 400, message = "User name or email is already in use.")
    })
    @GetMapping("/{username}")
    public Profile getProfile(@PathVariable("username") String username){
        User user =userDetailRepository.findByUsername(username).get();
        return new Profile(user.getId(),user.getUsername(),user.getEmail());
    }

}
