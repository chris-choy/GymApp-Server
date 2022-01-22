package com.chris.gym.controller;

import com.alibaba.fastjson.JSON;
import com.chris.gym.Plan.Service.PlanService;
import com.chris.gym.Plan.Entity.Plan;
import com.chris.gym.Plan.Entity.PlanRow;
import com.chris.gym.Plan.Entity.PlanSQLModel;
import com.chris.gym.Plan.Entity.PlanSection;
import com.chris.gym.Plan.Mapper.PlanMapper;
import com.chris.gym.Sport.SportMapper;
import com.chris.gym.User.User;
import com.chris.gym.User.UserMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@Api(tags = "Plan管理")
@RestController()
@RequestMapping("/plan")
public class PlanRestController {

    final UserMapper userMapper;
    final PlanMapper planMapper;
    final SportMapper sportMapper ;

    private PlanService planService;

    public PlanRestController(UserMapper userMapper,
                              PlanMapper planMapper,
                              SportMapper sportMapper,
                              PlanService planService) {
        this.userMapper = userMapper;
        this.planMapper = planMapper;
        this.sportMapper = sportMapper ;

        this.planService = planService;
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

    @ApiOperation("根据plan_id获取Plan")
    @ApiImplicitParam(name="Authorization", value = "JWT认证Token", required = true)
    @GetMapping("/id")
    public ResponseEntity<String> getPlan(@RequestBody int plan_id){

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

    @ApiOperation("创建或更新Plan")
    @ApiImplicitParam(name="Authorization", value = "JWT认证Token", required = true)
    @PostMapping("/update")
    public ResponseEntity<Plan> updatePlan(@RequestBody Plan requestPlan){

        // 获取user。
        User user = getUser();

        try {
//            Plan responsePlan = planService.updatePlan(user, requestPlan);
            Plan responsePlan = planService.updatePlan(user.getId(),requestPlan);
            return new ResponseEntity<>(responsePlan, HttpStatus.OK);

        }catch (Exception e){
            System.out.println(e);
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

    }

    @ApiOperation("获取用户的所有Plan")
    @ApiImplicitParam(
            name="Authorization",
            value = "JWT认证id_token", required = true)
    @GetMapping("/all")
    public ResponseEntity<List<Plan>> getCompletePlans(){
        User user = getUser();

        List<Plan> plans = planService.getAllPlansComplete(user.getId());
        if (plans != null) {
            return new ResponseEntity<>(plans, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }

    }

    @ApiOperation("根据plan_id删除Plan")
    @ApiImplicitParam(
            name="Authorization",
            value = "JWT认证id_token", required = true)
    @PostMapping("/delete")
    public ResponseEntity<String> deletePlan(@RequestBody int plan_id, @RequestBody int seq){
        planService.deletePlan(plan_id, seq);
        return new ResponseEntity<>("", HttpStatus.OK);
    }

}
