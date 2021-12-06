package com.bookend.authorizationserver.controller;

import com.bookend.authorizationserver.model.AuthUserDetail;
import com.bookend.authorizationserver.model.User;
import com.bookend.authorizationserver.payload.Profile;
import com.bookend.authorizationserver.repository.UserDetailRepository;
import com.bookend.authorizationserver.service.UserDetailServiceImpl;
import com.bookend.authorizationserver.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * AUTHS-PC stands for AuthorizationServer-ProfileController
 * CM stands for ControllerMethod
 */
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
    private UserDetailRepository userDetailRepository;
    @Autowired
    public void setUserDetailService(UserDetailServiceImpl userDetailService) {
        this.userDetailService = userDetailService;
    }
    /**
     * AUTHS-PC-1 (CM_9)
     */
    @ApiOperation(value = "Get user profile", response = Profile.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved profile "),
            @ApiResponse(code = 400, message = "User name or email is already in use.")
    })
    @GetMapping("/full/{username}")
    public Profile getFullProfile(@PathVariable("username") String username){
        User user = userService.findByUsername(username);
        return new Profile(user.getFirstname(),user.getLastname(),user.getUsername(),user.getAboutMe(),user.getEmail());
    }
    /**
     * AUTHS-PC-2 (CM_10)
     */
    @GetMapping("/{username}")
    public Profile getProfile(@PathVariable("username") String username){

        User user =userDetailRepository.findByUsername(username).get();
        return new Profile(user.getId(),user.getUsername(),user.getEmail());
    }
    /**
     * AUTHS-PC-3 (CM_11)
     */
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
