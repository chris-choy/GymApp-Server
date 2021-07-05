package com.chris.gym.controller;

import com.alibaba.fastjson.JSONObject;
import com.chris.gym.jwt.JWTFilter;
import com.chris.gym.jwt.TokenProvider;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AuthenticationRestController {


    private final TokenProvider tokenProvider;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

//    private final BeanCeshi beanCeshi;

    public AuthenticationRestController(TokenProvider tokenProvider,
                                        AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;


    }




//    @PostMapping("/login")
//    public ResponseEntity<JWTToken> login(@RequestParam("username") String username,
//          @RequestParam("password") String password) {
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody String body) {

        // 解析body，获取username和password。
        JSONObject bodyJsonObject = JSONObject.parseObject(body);

        String username = "";
        String password = "";
        int seq = 0;
        try {
            username = bodyJsonObject.getString("username");
            password = bodyJsonObject.getString("password");
        } catch (Exception e){
            return new ResponseEntity<String>(" Missing username and password.",HttpStatus.BAD_REQUEST);
        }




        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);


        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

//        boolean rememberMe = (loginDto.isRememberMe() == null) ? false : loginDto.isRememberMe();


        // 创建token
//        String jwt = tokenProvider.createToken(authentication, rememberMe);
        String jwt = tokenProvider.createToken(authentication, false);


        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);


//        System.out.println(jwtResponse(jwt));

        return new ResponseEntity<String>(jwtResponse(jwt), httpHeaders, HttpStatus.OK);
    }

    private String jwtResponse(String idToken){


        return "{\"id_token\": \"" + idToken + "\"}";
    }

    /**
     * Object to return as body in JWT Authentication.
     */
    static class JWTToken {

        private String idToken;

        JWTToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }


}
