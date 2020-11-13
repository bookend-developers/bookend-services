package com.bookend.authorizationserver.controller;

import com.bookend.authorizationserver.model.AuthUserDetail;
import com.bookend.authorizationserver.model.User;
import com.bookend.authorizationserver.payload.Profile;
import com.bookend.authorizationserver.service.UserDetailServiceImpl;
import com.bookend.authorizationserver.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {
    private UserDetailServiceImpl userDetailService;
    private UserService userService;
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

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
        User user = userService.findByUsername(username);
        return new Profile(user.getId().longValue(),user.getFirstname(),user.getLastname(),user.getUsername(),user.getAboutMe(),user.getEmail());
    }
    @PostMapping("/{username}")
    public User setProfile(@PathVariable("username") String username,@RequestBody Profile profileRequest){
        User user = userService.findByUsername(username);
        if(profileRequest.getAboutMe()!=null){
            user.setAboutMe(profileRequest.getAboutMe());
        }
        if(profileRequest.getFirstname()!=null){
            user.setFirstname(profileRequest.getFirstname());
        }
        if(profileRequest.getLastname()!=null){
            user.setLastname(profileRequest.getLastname());
        }
        return userService.save(user);


    }

}
