package com.mrieb577.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mrieb577.database.DatabaseConnection;
import com.mrieb577.database.UsersDB;
import com.mrieb577.user.AuthRequest;
import com.mrieb577.user.JwtUtil;
import com.mrieb577.user.UserInfo;
import com.mrieb577.user.UserInfoService;

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
        if(UsersDB.getUserByEmail(connection, userInfo.getEmail()) == null)
            return userDetailsService.addUser(userInfo);
        else return "User already exists with this email!";
    }

    @PostMapping("/generate-token") // aka login
    public String generateToken(@RequestBody AuthRequest authRequest){
        try{
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.username, authRequest.password)
            );
            if(authentication.isAuthenticated()){
                return jwtUtil.generateToken(authRequest.username);
            }
            return "Access denied";
        } catch (Exception e){
            return "Access denied";
        }
    }
}
