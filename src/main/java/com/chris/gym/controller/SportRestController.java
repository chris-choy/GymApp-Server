package com.chris.gym.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chris.gym.Sport.Sport;
import com.chris.gym.Sport.SportDao;
import com.chris.gym.Sport.SportMapper;
import com.chris.gym.User.User;
import com.chris.gym.User.UserMapper;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@RestController
@RequestMapping("/sport")
@Api(tags = "Sport管理")
public class SportRestController {

    final UserMapper userMapper ;
    final SportMapper sportMapper ;

    final SportDao sportDao;

    public SportRestController(UserMapper userMapper, SportMapper sportMapper, SportDao sportDao) {
        this.userMapper = userMapper;
        this.sportMapper = sportMapper;
        this.sportDao = sportDao;
    }


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

    @PostMapping("/create")
    public ResponseEntity<String> createSport(@RequestBody Sport sport){

        // 获取user_id
        User user = getUser();
        if(user == null){
            return new ResponseEntity<>("Error: User was not found.", HttpStatus.BAD_REQUEST);
        }

        // 创建sport。
        try {
            sportDao.createSport(sport.getName(), sport.getUnit(), user.getId());
            return new ResponseEntity<>("OK", HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateSport(@RequestBody Sport sport){

        // 获取user_id
        User user = getUser();
        if(user == null){
            return new ResponseEntity<>("Error: User was not found.", HttpStatus.BAD_REQUEST);
        }

        // 创建sport。
        try {
            sportDao.updateSport(sport.getId(), sport.getName(), sport.getUnit());
            return new ResponseEntity<>("OK", HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Sport>> getSports() {
        // 获取user_id
        User user = getUser();

        List<Sport> sports = sportDao.getAllSport(user.getId());

        return new ResponseEntity<>(sports, HttpStatus.OK);
    }

    @GetMapping("/byName")
    public ResponseEntity<String> getSport(@RequestBody String body) {

        // 获取user_id
        User user = getUser();
        if(user == null){
            return new ResponseEntity<String>("Missing user details.", HttpStatus.BAD_REQUEST);
        }
        int user_id = user.getId();

        JSONObject bodyJsonObject = JSONObject.parseObject(body);
        if(bodyJsonObject == null){
            return new ResponseEntity<String>("Body data can't be parsed to json.", HttpStatus.BAD_REQUEST);
        }
        String name = "";


        // 解析body数据，获取需要创建的 sport信息。
        String unit = "";
        try {
            name = bodyJsonObject.getString("name");
//            unit = bodyJsonObject.getString("unit");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<String>("Missing sport details.", HttpStatus.BAD_REQUEST);
        }

        Sport sport = sportMapper.findSportByName(user.getId(), name);
        return new ResponseEntity<String>(JSON.toJSONString(sport,true), HttpStatus.OK);
    }

}
