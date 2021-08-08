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


    @ApiOperation(value = "更新hello")
    @PostMapping("/update")
    public ResponseEntity<HelloEntity> update(@RequestBody HelloEntity plan){

        ObjectMapper objectMapper = new ObjectMapper();

        String str = "";

        try {
            str = objectMapper.writeValueAsString(plan);
        }catch (Exception e){
            System.out.println(e);
        }



//        if(plan.getName().contentEquals("nullname")){
//            return
//        }



        return new ResponseEntity<>(plan,HttpStatus.OK);



    }

}
