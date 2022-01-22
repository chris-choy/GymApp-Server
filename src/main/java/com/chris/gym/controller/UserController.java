package com.chris.gym.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.PropertyPreFilter;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.chris.gym.User.Service.UserService;
import com.chris.gym.User.User;
import com.chris.gym.User.UserMapper;
import com.mysql.cj.xdevapi.JsonString;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;

@Api(tags = "用户管理")
@RestController
@RequestMapping("/user")
public class UserController {

    UserMapper userMapper;

    @Autowired
    UserService userService;

    public UserController(UserMapper userMapper){
        this.userMapper = userMapper;
    }


    @ApiOperation("获取用户信息")
    @ApiImplicitParam(
            name="Authorization",
            value = "JWT认证id_token", required = true)
    @ApiResponse(code=200, message = "{\n" +
            "\"id\": 0,\n" +
            "\"name\": \"string\",\n" +
            "\"username\": \"string\",\n" +
            "\"name\": \"string\",\n" +
            "\"gender\": True,\n" +
            "\"age\": 0\n" +
            "}", response = String.class)
    @GetMapping("/profile")
    public ResponseEntity<String> getProfile(){

        User user = User.getUser(userMapper);
        if (user != null) {

            PropertyFilter filter = new PropertyFilter() {

                public boolean apply(Object source, String name, Object value) {
                    switch (name){
                        case "id":
                        case "username":
                        case "name":
                        case "gender":
                        case "age":
                            return true;
                        default:
                            return false;

                    }
                }
            };

            return new ResponseEntity<String> (JSON.toJSONString(user,filter), HttpStatus.OK);
        } else {
            return new ResponseEntity<String> ("Error.", HttpStatus.BAD_REQUEST);
        }

    }


    @ApiOperation("注册用户")
    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody User user){

        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

        try {
            userService.createUser(user);
        } catch (DataAccessException e){

            System.out.println("DataAccessException:" + e.getMessage());

            String message = "已存在相同的用户名，请更换一个尝试。";
            return new ResponseEntity<>(message, HttpStatus.CONFLICT);

        }

        return new ResponseEntity<>(HttpStatus.OK);

    }

}
