package com.chris.gym.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.PropertyPreFilter;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.chris.gym.User.User;
import com.chris.gym.User.UserMapper;
import com.mysql.cj.xdevapi.JsonString;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    UserMapper userMapper;

    public UserController(UserMapper userMapper){
        this.userMapper = userMapper;
    }

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

}
