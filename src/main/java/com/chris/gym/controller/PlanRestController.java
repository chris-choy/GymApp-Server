package com.chris.gym.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chris.gym.Plan.Dao.PlanDao;
import com.chris.gym.Plan.Entity.Plan;
import com.chris.gym.Plan.Entity.PlanRow;
import com.chris.gym.Plan.Entity.PlanSQLModel;
import com.chris.gym.Plan.Entity.PlanSection;
import com.chris.gym.Plan.Mapper.PlanMapper;
import com.chris.gym.Sport.SportMapper;
import com.chris.gym.User.User;
import com.chris.gym.User.UserMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@Api(tags = "Plan管理")
//@ApiOperation(value = "aaa", tags = "Plan管理")
@RestController()
@RequestMapping("/plan")
public class PlanRestController {

    final UserMapper userMapper;
    final PlanMapper planMapper;
    final SportMapper sportMapper ;

    private PlanDao planDao;


    public PlanRestController(UserMapper userMapper,
                              PlanMapper planMapper,
                              SportMapper sportMapper,
                              PlanDao planDao) {
        this.userMapper = userMapper;
        this.planMapper = planMapper;
        this.sportMapper = sportMapper ;

        this.planDao = planDao;
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








    // Plan操作

    @GetMapping("/all")
    public ResponseEntity<String> getPlans(){
        User user = getUser();

        if (user != null ){
            List<Plan> plans = planMapper.findAllPlanWithUserId(user.getId());
            return new ResponseEntity<String>(JSON.toJSONString(plans,true), HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("No user.", HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/id")
    public ResponseEntity<String> getPlans(@RequestBody String body){

        // 解析body数据，获取plan_id。
        JSONObject bodyJsonObject = JSONObject.parseObject(body);

        int plan_id = 0;
        try {
            plan_id = bodyJsonObject.getInteger("plan_id");
//            id = bodyJsonObject.getInteger("id");
        } catch (Exception e){
            return new ResponseEntity<String>(" missing xxx.",HttpStatus.BAD_REQUEST);
        }

        try {

            List<PlanSQLModel> temp = planMapper.findPlanByPlanId(plan_id);

            Plan plan = parseToPlan(temp);

            // 完成，返回结果plan。
            return new ResponseEntity<String>(JSON.toJSONString(plan,true), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<String>("Error.", HttpStatus.BAD_REQUEST);
        }

    }

    private Plan parseToPlan(List<PlanSQLModel> temp) {
        // 将List<PlanSQLModel>转化为一个Plan。
        // 按照Plan-PlanSection-PlanRow层级分开。

        Plan plan = null;
        List<PlanSection> sectionList = new ArrayList<PlanSection>();
        List<PlanRow> rowList = new ArrayList<PlanRow>();

        for (int i = 0; i< temp.size(); i++){
            PlanSQLModel p = temp.get(i);

            // 判定为Plan。
            if(!p.getName().isBlank()){
                plan = new Plan(p);
            }

            // 判定为PlanSection。
            if(p.getSport_id() != 0){
                PlanSection section = new PlanSection(p);
                section.setSport(sportMapper.findSportById(p.getSport_id()));
                sectionList.add(section);

            }

            // 判定为PlanRow。
            if(p.getValue() != 0){
                rowList.add(new PlanRow(p));
            }
        }

        // sectionList根据seq升序排序。
        Collections.sort(sectionList);

        // 将PlanRow加进去对应的PlanSection中。
        for( int i=0; i<rowList.size(); i++){
            PlanRow row = rowList.get(i);

            for (PlanSection section :
                    sectionList) {
//                System.out.println("    " + section.getId());
                if(section.getId() == row.getPlan_section_id()){
                    if(section.getRowList() != null){
                        section.getRowList().add(row);
                    } else {
                        // 创建rowList。
                        List<PlanRow> rows = new ArrayList<PlanRow>();
                        rows.add(row);
                        section.setRowList(rows);
                    }
                    break;
                }
            }

        }

        // 将sectionList添加到plan中。
        plan.setSectionList(sectionList);
        return plan;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createPlan(@RequestBody String body){

        // 获取创建参数
        JSONObject bodyJsonObject = JSONObject.parseObject(body);

        String name = "";
        int seq = 0;
        try {
            name = bodyJsonObject.getString("name");
            seq = bodyJsonObject.getInteger("seq");
        } catch (Exception e){
            return new ResponseEntity<String>(" missing xxx.",HttpStatus.BAD_REQUEST);

        }

        // 获取user
        User user = getUser();
        if (user != null) {

            // 创建
//            planMapper.create()
            try {
                planMapper.insertPlan(name, user.getId(), seq);
            } catch (Exception e){
                return new ResponseEntity<String>(e.getMessage() + seq,HttpStatus.BAD_REQUEST);
            }


            return new ResponseEntity<String>(JSON.toJSONString(user,true) + name +" "+ seq,HttpStatus.OK);

        } else {
            return new ResponseEntity<String>("Can't find the user information." ,HttpStatus.BAD_REQUEST);
        }
    }



    @ApiOperation("更新完整Plan")
    @ApiImplicitParam(name="Authorization", value = "JWT认证Token", required = true)
    @PostMapping("/update")
    public ResponseEntity<Plan> updatePlan(@RequestBody Plan requestPlan){

        // 获取user。
        User user = getUser();

        Plan responsePlan = planDao.updatePlan(user, requestPlan);

        return new ResponseEntity<>(responsePlan, HttpStatus.OK);

    }



//    @ApiOperation("更新Plan")
//    @ApiImplicitParam(name="Authorization", value = "JWT认证Token", required = true)
//    @PostMapping("/update")
    public ResponseEntity<String> updatePlan_bak(@RequestBody String body){

        // 获取创建参数
        JSONObject bodyJsonObject = JSONObject.parseObject(body);

        String name = "";
        int id = 0;
        String planJsonStr = "";
        try {
            name = bodyJsonObject.getString("name");
            id = bodyJsonObject.getInteger("id");
            planJsonStr = bodyJsonObject.getString("plan");
        } catch (Exception e){
            return new ResponseEntity<String>(" missing xxx.",HttpStatus.BAD_REQUEST);
        }

        // 获取user。
        User user = getUser();
        if (user != null) {

            // 将planJsonStr转化成plan对象。
            if(planJsonStr.length() == 0) {
                return new ResponseEntity<String>("Lost plan data.", HttpStatus.BAD_REQUEST);
            }

            // 更新Plan的信息。
            Plan requestPlan = JSON.parseObject(planJsonStr, Plan.class);

            // 检查Plan是否存在，存在则update，不存在则insert。
            Plan responsePlan = planMapper.findPlanWithUserIdAndSeq(user.getId(), requestPlan.getSeq());

            planDao.updatePlan(user, requestPlan);



            return new ResponseEntity<String>("Can't find the user information." ,HttpStatus.BAD_REQUEST);

        } else {
            // 返回错误信息。
            return new ResponseEntity<String>("Can't find the user information." ,HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/complete")
    public ResponseEntity<List<Plan>> getCompletePlans(){
        User user = getUser();

        List<Plan> plans = planDao.getAllPlansComplete(user.getId());
        if (plans != null) {
            return new ResponseEntity<>(plans, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }


//        if (user != null ){
//            List<Plan> plans = planMapper.findAllPlanWithUserId(user.getId());
//
//            List<Integer> id_list = planMapper.findAllPlanId(user.getId());
////            List<int> id_list =
//
//            List<Plan> completePlans = new ArrayList<Plan>();
//
//            for (int plan_id:
//                 id_list) {
//                List<PlanSQLModel> planSQLModels =  planMapper.findPlanByPlanId(plan_id);
//
//                Plan planModel = parseToPlan(planSQLModels);
//                completePlans.add(planModel);
//            }
//
////            return new ResponseEntity<String>(JSON.toJSONString(completePlans), HttpStatus.OK);
//            return new ResponseEntity<>(completePlans,HttpStatus.OK);
//        } else {
////            return new ResponseEntity<String>("No user.", HttpStatus.BAD_REQUEST);
//            return new ResponseEntity<>(null, HttpStatus.OK);
//        }

    }


    // PlanSection操作
    @PostMapping("/plan/section/create")
    public ResponseEntity<String> createSection(@RequestBody String body){

        // 获取用户信息。
        User user = getUser();
        if(user == null){
            return new ResponseEntity<String>("Missing user details.", HttpStatus.BAD_REQUEST);
        }

        // 获取创建Section参数。
        JSONObject bodyJsonObject = JSONObject.parseObject(body);

        int sport_id = 0;
        int seq = 0;
        int plan_id = 0;
        try {
            sport_id = bodyJsonObject.getInteger("sport_id");
            seq = bodyJsonObject.getInteger("seq");
            plan_id = bodyJsonObject.getInteger("plan_id");
        } catch (Exception e){
            return new ResponseEntity<String>(" missing xxx.",HttpStatus.BAD_REQUEST);
        }

        try {
            planMapper.insertPlanSection(user.getId(), plan_id, seq, sport_id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<String>("Missing sport details.", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<String>("OK", HttpStatus.OK);
    }

    @GetMapping("/plan/section/id")
    public ResponseEntity<String> findSection(@RequestBody String body){

        // 获取用户信息。
//        User user = getUser();
//        if(user == null){
//            return new ResponseEntity<String>("Missing user details.", HttpStatus.BAD_REQUEST);
//        }

        // 获取创建Section参数。
        JSONObject bodyJsonObject = JSONObject.parseObject(body);

//        int plan_id = 0;
        int id = 0;
        try {
//            plan_id = bodyJsonObject.getInteger("plan_id");
            id = bodyJsonObject.getInteger("id");
        } catch (Exception e){
            return new ResponseEntity<String>(" missing xxx.",HttpStatus.BAD_REQUEST);
        }

        try {
            PlanSection planSection = planMapper.findPlanSectionById(id);

            planSection.setRowList( planMapper.findPlanRowBySectionId(id));

            return new ResponseEntity<String>(JSON.toJSONString(planSection,true), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<String>("Error.", HttpStatus.BAD_REQUEST);
        }

//        return new ResponseEntity<String>("OK", HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<String> deletePlan(@RequestBody int plan_id){
        planDao.deletePlan(plan_id);
        return new ResponseEntity<>("", HttpStatus.OK);
    }


    // PlanRow 操作
//    @GetMapping("/plan/row/create")
//    public ResponseEntity<String> createRow(@RequestBody String body){
//
//        // 获取用户信息。
//        User user = getUser();
//        if(user == null){
//            return new ResponseEntity<String>("Missing user details.", HttpStatus.BAD_REQUEST);
//        }
//
//        // 获取创建Section参数。
//        JSONObject bodyJsonObject = JSONObject.parseObject(body);
//
//        int seq = 0;
//        int plan_section_id = 0;
//        double value = 0;
//        int plan_id = 0;
//        int times = 0;
//        try {
//            seq = bodyJsonObject.getInteger("seq");
//            plan_section_id = bodyJsonObject.getInteger("plan_section_id");
//            value = bodyJsonObject.getDoubleValue("value");
//            plan_id = bodyJsonObject.getInteger("plan_id");
//            times = bodyJsonObject.getInteger("times");
//        } catch (Exception e){
//            return new ResponseEntity<String>(" missing xxx.",HttpStatus.BAD_REQUEST);
//        }
//
//        try {
//            planMapper.insertPlanRow(user.getId(), plan_id, plan_section_id, seq, value, times);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            return new ResponseEntity<String>("Missing sport details.", HttpStatus.BAD_REQUEST);
//        }
//
//        return new ResponseEntity<String>("OK", HttpStatus.OK);
//    }






}
