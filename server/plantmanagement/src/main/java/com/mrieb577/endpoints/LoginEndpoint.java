package com.mrieb577.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.mrieb577.database.DatabaseConnection;
import com.mrieb577.database.UsersDB;
import com.mrieb577.login.AuthRequest;
import com.mrieb577.login.JwtUtil;
import com.mrieb577.login.UserInfo;
import com.mrieb577.login.UserInfoService;
import com.mrieb577.responses.LoginRequestResponse;
import com.mrieb577.responses.RequestResponse;

@RestController
@RequestMapping("/account")
public class LoginEndpoint {
    private final UserInfoService userDetailsService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public LoginEndpoint(UserInfoService userDetailsService, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/add-user")
    public String addUser(@RequestBody UserInfo userInfo){
        DatabaseConnection connection = new DatabaseConnection();
        // check that the email does not exist in the database
        if(UsersDB.getUserByEmail(connection, userInfo.getEmail()).email == null)
            return userDetailsService.addUser(userInfo);
        else return "User already exists with this email!";
    }

    @PostMapping("/generate-token") // aka login
    public String generateToken(@RequestBody AuthRequest authRequest){
        Gson gson = new Gson();
        try{
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.username, authRequest.password)
            );
            if(authentication.isAuthenticated()){
                DatabaseConnection connection = new DatabaseConnection();
                UserInfo user = UsersDB.getUserByEmail(connection, authRequest.username);
                String token = jwtUtil.generateToken(authRequest.username);
                return gson.toJson(new LoginRequestResponse(RequestResponse.SUCCESS_CODE, token, user));
            }
            return gson.toJson(new LoginRequestResponse(RequestResponse.ACCESS_DENIED_CODE, "Access denied", null));
        } catch (Exception e){
            return gson.toJson(new LoginRequestResponse(RequestResponse.ACCESS_DENIED_CODE, "Access denied", null));
        }
    }
}
