package com.bookend.authorizationserver.controller;

import com.bookend.authorizationserver.payload.ConfirmResponse;
import com.bookend.authorizationserver.payload.NewPasswordRequest;
import com.bookend.authorizationserver.payload.ResetPasswordRequest;
import com.bookend.authorizationserver.payload.ResetPasswordResponse;
import com.bookend.authorizationserver.payload.SignUpRequest;
import com.bookend.authorizationserver.payload.SignUpResponse;
import com.bookend.authorizationserver.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
/**
 * AUTHS-AC stands for AuthorizationServer-AuthController
 * CM stands for ControllerMethod
 */
@RestController
@RequestMapping("/api/oauth")
public class AuthController {

    @Autowired
    UserService userService;

    /**
     * AUTHS-AC-1 (CM_5)
     */
    @ApiOperation(value = "New User Registration", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully registered user"),
            @ApiResponse(code = 400, message = "User name or email is already in use.")
    })
    @PostMapping("sign-up")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest signUpRequest){
        SignUpResponse res = userService.addUser(signUpRequest);
        return ResponseEntity.ok(res);
    }

    /**
     * AUTHS-AC-2 (CM_6)
     */
    @ApiOperation(value = "Confirm User Email", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully confirmation of an email")
    })
    @GetMapping("confirm/{token}")
    public ResponseEntity<?> confirm(@PathVariable("token") String token){

        ConfirmResponse confirmResponse = userService.confirm(token);
        return ResponseEntity.ok(confirmResponse);
    }
    /**
     * AUTHS-AC-3 (CM_7)
     */
    @PostMapping("resetPassword")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest){
    	ResetPasswordResponse res = userService.resetPassword(resetPasswordRequest);
    	if (res == null) {
    		throw new ResponseStatusException(HttpStatus.NOT_FOUND,"There is no user for this email");
    	}
        return ResponseEntity.ok(res);
    }
    /**
     * AUTHS-AC-4 (CM_8)
     */
    @PostMapping("confirmPassword/{token}")
    public ResponseEntity<?> confirmResetPassword(@PathVariable("token") String token,@RequestBody NewPasswordRequest newPasswordRequest){
    	ConfirmResponse confirmResponse = userService.confirmPassword(token,newPasswordRequest);
        return ResponseEntity.ok(confirmResponse);
    }
    

}
