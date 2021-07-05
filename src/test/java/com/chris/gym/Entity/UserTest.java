package com.chris.gym.Entity;


import com.alibaba.fastjson.JSON;
//import com.example.hellodruid.demo.entity.User;
//import com.example.hellodruid.demo.mapper.UserMapper;
import com.chris.gym.Plan.Entity.Plan;
import com.chris.gym.Plan.Entity.PlanSection;
import com.chris.gym.User.User;
import com.chris.gym.User.UserMapper;
import org.junit.Test;
//import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class UserTest {

    @Autowired
    private UserMapper userMapper ;

    @Test
    public void getAllTest(){
        List<User> user = userMapper.findAll();

        System.out.println(JSON.toJSONString(user,true));
    }

    @Test
    public void insert(){

//        int resultId = userMapper.insert("ccc", "ccc", "c");

    }

    @Test
    public void findById(){
        User user = userMapper.findById(0);

        System.out.println(JSON.toJSONString(user, true));
    }

    @Test
    public void findByUsername(){
        User user = userMapper.findByUsername("aa");

        System.out.println(JSON.toJSONString(user, true));
    }

    @Test
    public void testConvertTheJsonToPlan(){
        String jsonStr = "{\"id\":4,\"seq\":0,\"user_id\":0,\"name\":\"plan1\",\"sectionList\":[{\"id\":0,\"rowList\":[{\"id\":0,\"plan_section_id\":0,\"seq\":1,\"value\":11}],\"seq\":1,\"plan_id\":4,\"sport\":{\"id\":0,\"name\":\"sport1\",\"unit\":\"unit1\"}},{\"id\":0,\"rowList\":[{\"id\":0,\"plan_section_id\":0,\"seq\":1,\"value\":33},{\"id\":0,\"plan_section_id\":0,\"seq\":2,\"value\":44},{\"id\":0,\"plan_section_id\":0,\"seq\":3,\"value\":55}],\"seq\":2,\"plan_id\":4,\"sport\":{\"id\":0,\"name\":\"sport2\",\"unit\":\"unit2\"}}],\"last_changed\":0}";

        String sectionJson = "{\n" +
                "            \"id\":0,\n" +
                "            \"rowList\":[\n" +
                "                {\n" +
                "                    \"id\":0,\n" +
                "                    \"plan_section_id\":0,\n" +
                "                    \"seq\":1,\n" +
                "                    \"value\":11\n" +
                "                }\n" +
                "            ],\n" +
                "            \"seq\":1,\n" +
                "            \"plan_id\":4,\n" +
                "            \"sport\":{\n" +
                "                \"id\":0,\n" +
                "                \"name\":\"sport1\",\n" +
                "                \"unit\":\"unit1\"\n" +
                "            }\n" +
                "        }";


        Plan plan = JSON.parseObject(jsonStr, Plan.class);


        PlanSection section = JSON.parseObject(sectionJson, PlanSection.class);


//        System.out.println(JSON.toJSONString(plan));
        System.out.println(JSON.toJSONString(section));

    }



}
