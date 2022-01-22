package com.chris.gym.controller;


import com.chris.gym.Plan.Entity.Plan;
import com.chris.gym.Record.Entity.Record;
import com.chris.gym.Record.Mapper.RecordMapper;
import com.chris.gym.Record.Service.RecordService;
import com.chris.gym.User.User;
import com.chris.gym.User.UserMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/record")
@Api(tags = "Record管理")
public class RecordRestController {

    @Autowired
    RecordService recordService ;

    @Autowired
    UserMapper userMapper ;


    @ApiOperation("获取所有运动记录")
    @ApiImplicitParam(name="Authorization", value = "JWT认证Token", required = true)
    @GetMapping("/all")
    public ResponseEntity<List<Record>> getAllRecords(){
        List<Record> records = recordService.getAll(User.getUser(userMapper).getId());

        return new ResponseEntity<List<Record>>(records, HttpStatus.OK);
    }


    @ApiOperation("根据id获取某条记录")
    @ApiImplicitParam(name="Authorization", value = "JWT认证Token", required = true)
    @GetMapping("/get")
    public ResponseEntity<Record> getRecord(@RequestBody int id){

        Record record = recordService.get(id);

        return new ResponseEntity<Record>(record, HttpStatus.OK);
    }


    @ApiOperation("创建运动记录")
    @ApiImplicitParam(name="Authorization", value = "JWT认证Token", required = true)
    @PostMapping("/create")
    public ResponseEntity<Record> createRecord(@RequestBody Record record){

        Record result = recordService.create(record, getUser().getId());

        return new ResponseEntity<Record>(result, HttpStatus.OK);
    }

    private User getUser(){

        // 获取User
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
//            LOG.debug("no authentication in security context found");
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

}
