package com.chris.gym.Hello;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chris.gym.Plan.Entity.Plan;
import com.chris.gym.User.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.xdevapi.JsonString;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/hello")
public class HelloController {

    private User getUser(){

        // 获取User
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
//            LOG.debug("no authentication in security context found");
//            return "no authentication in security context found.";
            return null;
        } else {
            // 获取username
            String username = "";
            if (authentication.getPrincipal() instanceof UserDetails) {
                UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
                username = springSecurityUser.getUsername();
            } else if (authentication.getPrincipal() instanceof String) {
                username = (String) authentication.getPrincipal();
            }
            // 获取user
            User user = userMapper.findByUsername(username);

            return user;

        }

    }


    @ApiOperation(value = "更新hello")
    @PostMapping("/update")
    public HelloEntity update(@RequestBody HelloEntity plan){


        ObjectMapper objectMapper = new ObjectMapper();

        String str = "";

        try {
            str = objectMapper.writeValueAsString(plan);
        }catch (Exception e){
            System.out.println(e);
        }

        return plan;




    }

}
