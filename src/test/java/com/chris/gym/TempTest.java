package com.chris.gym;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chris.gym.Plan.Entity.Plan;
import com.chris.gym.Plan.Entity.PlanRow;
import com.chris.gym.Plan.Entity.PlanSQLModel;
import com.chris.gym.Plan.Entity.PlanSection;
import com.chris.gym.Plan.Mapper.PlanMapper;
import com.chris.gym.Sport.SportMapper;
import com.chris.gym.User.User;
import com.chris.gym.User.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GymApplication.class)
public class TempTest {

    @Autowired
    UserMapper userMapper;

    @Autowired
    PlanMapper planMapper;

    @Autowired
    SportMapper sportMapper;

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

    String body = "{\"id\":26,\"seq\":2,\"user_id\":4,\"name\":\"plan_test_1\",\"sectionList\":[{\"id\":30,\"rowList\":[{\"id\":32,\"plan_section_id\":8,\"seq\":1,\"value\":333,\"times\":3},{\"id\":34,\"plan_section_id\":8,\"seq\":2,\"value\":555,\"times\":5}],\"seq\":1,\"plan_id\":4,\"sport\":{\"id\":4,\"name\":\"sport2\",\"unit\":\"unit2\"}}],\"last_changed\":0}";

    @Test
    public void testUpdatePlan(){

//        String body = "";

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
//            return new ResponseEntity<String>(" missing xxx.", HttpStatus.BAD_REQUEST);
        }

        // 获取user。
//        User user = getUser();
        User user = new User();
        user.setId(4);
        user.setName("user1");
        // 获取user End。

        if (user != null) {


//            if(planJsonStr.length() == 0) {
////                return new ResponseEntity<String>("Lost plan data.", HttpStatus.BAD_REQUEST);
//            }


            // 将planJsonStr转化成plan对象。
            Plan requestPlan = null;
            try {
                requestPlan = JSON.parseObject(body, Plan.class);
            } catch (Exception e) {
                System.out.println("Error：json转换到plan出错。");
            }


            // 更新Plan。

            ResponseEntity res = updatePlan(user, requestPlan);

            System.out.println(res);

        }
    }

    private ResponseEntity<String> updatePlan(User user,Plan requestPlan){

        // 更新Plan的信息。
        // 检查Plan是否存在，存在则update，不存在则insert。
        Plan responsePlan = null;

        // id等于0，则是新的Plan，直接创建就好。
        if (requestPlan.getId() != 0){
            // 先尝试获取服务器上的Plan内容。
            try {

                List<PlanSQLModel> sqlModels = planMapper.findPlanByPlanId(requestPlan.getId());

//            responsePlan = planMapper.findPlanWithUserIdAndSeq(user.getId(), requestPlan.getSeq());
                if (sqlModels != null) {
                    responsePlan = parseToPlan(sqlModels);
                }
            } catch (Exception e){
                System.out.println("Error: 检查Plan是否存在中," + e + "." );
                return new ResponseEntity<String>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }




        if( responsePlan == null ){
            // 创建plan。
            try {
                planMapper.insertPlan(requestPlan.getName(),user.getId(), requestPlan.getSeq());
                responsePlan = planMapper.findPlanWithUserIdAndSeq(user.getId(), requestPlan.getSeq());
            } catch (Exception e){
                System.out.println(e);
            }

        } else {
            // 更新plan。
            if(responsePlan.getName().contentEquals(requestPlan.getName()) == false){
                // 需要修改的plan和数据库中的plan名称不一致。
                // 故update修改，否则不作任何改动。
                try {
                    planMapper.updatePlan(responsePlan.getId(), requestPlan.getName());
                } catch (Exception e){
                    System.out.println(e);
                }
            }
        }

        // 下一步骤，对PlanSection和PlanRow进行检查修改。
//        for (PlanSection requestSection:
//                requestPlan.getSectionList()) {
//            updateSection(responsePlan, ,requestSection);
//        }
        for(int i = 0; i<requestPlan.getSectionList().size(); i++){
            if ( i < responsePlan.getSectionList().size()){
                updateSection(
                        responsePlan,
                        responsePlan.getSectionList().get(i),
                        requestPlan.getSectionList().get(i));
            } else {
                updateSection(
                        responsePlan,
                        null,
                        requestPlan.getSectionList().get(i));
            }

        }

        // 删除多余的section。
        int difference = responsePlan.getSectionList().size() - requestPlan.getSectionList().size();
        if(difference > 0){
            // 从后往前删除。
            // 比如目标数量是2，当前数量是4。
            // i从2-3（3为4-1）。
            for (int i = requestPlan.getSectionList().size() ;
                 i < responsePlan.getSectionList().size() ;
                 i++ ){
                try {
                    planMapper.deletePlanSection(responsePlan.getSectionList().get(i).getId());
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }



//            return new ResponseEntity<String>(JSON.toJSONString(user,true) ,HttpStatus.OK);

        return ResponseEntity.ok("");
    }

    public void updateSection(Plan plan, PlanSection currentSection , PlanSection requestSection){
        // 检查section本身是否需要改动。


        PlanSection responseSection = currentSection;
//        try {
//            // 获取服务器上的section版本。
//            PlanSQLModel sqlModel = planMapper.findPlanSection(plan.getId(), requestSection.getSeq());
//            if (sqlModel != null){
//                responseSection = new PlanSection(sqlModel);
//            }
////            responseSection = planMapper.findPlanSection(plan.getId(), requestSection.getSeq());
//
//
//        } catch (Exception e){
//            System.out.println("Error: 检查PlanSection是否存在时," + e + "." );
//        }

        if( responseSection == null ){
            // 数据库中不存在。
            // 创建planSection。
            try {
                planMapper.insertPlanSection(plan.getUser_id(), plan.getId(), requestSection.getSeq(), requestSection.getSport().getId());

                PlanSQLModel sqlModel = planMapper.findPlanSection(plan.getId(), requestSection.getSeq());
                if (sqlModel != null){
                    responseSection = new PlanSection(sqlModel);
                }

            } catch (Exception e){
                System.out.println(e);
            }

        } else {
            // 更新planSection。
            // 对比需要修改的和已经存在的"运动信息Sport"是否一致，否则update。
            if(responseSection.getSport().getName().contentEquals(requestSection.getSport().getName()) == false){
                try {
                    // 检查sport_id。
                    planMapper.updatePlanSectionSportId(responseSection.getId(), requestSection.getSport().getId());
                } catch (Exception e){
                    System.out.println(e);
                }
            }
        }

        // 处理更新Row。
//        for (PlanRow requestRow:
//             responseSection.getRowList()) {
//            updateRow(responseSection, requestRow);
//        }

        for(int i=0; i<requestSection.getRowList().size(); i++){
            if(i < responseSection.getRowList().size()) {
                updateRow(
                        responseSection,
                        responseSection.getRowList().get(i),
                        requestSection.getRowList().get(i));
            } else {
                // 当前数据库中不存在。
                updateRow(
                        responseSection,
                        null,
                        requestSection.getRowList().get(i));
            }
        }

        // 删除多余的row。
        int difference = responseSection.getRowList().size() - requestSection.getRowList().size();
        if(difference > 0){
            for (int i = requestSection.getRowList().size() ;
                 i < responseSection.getRowList().size() ;
                 i++){
                try {
                    planMapper.deletePlanRow(responseSection.getRowList().get(i).getId());
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }

    }

    public void updateRow(PlanSection responseSection, PlanRow currentRow, PlanRow requestRow){
        // 查看数据库中是否存在。
        PlanRow responseRow = currentRow;

//        try {
//            responseRow = planMapper.findPlanRow(responseSection.getId(), requestRow.getSeq());
//        } catch (Exception e){
//            System.out.println(e);
//        }

        if( responseRow == null ){
            // 数据库中不存在。
            // 创建planRow。
            try {
                planMapper.insertPlanRow(
                        responseSection.getSport().getUser_id(),
                        responseSection.getPlan_id(),
                        responseSection.getId(),
                        requestRow.getSeq(),
                        requestRow.getValue(),
                        requestRow.getTimes());

                responseRow = planMapper.findPlanRow(responseSection.getId(), requestRow.getSeq());
            } catch (Exception e){
                System.out.println(e);
            }

        } else {
            // 更新planRow。
            // 对比需要修改的和已经存在的"value"是否一致，否则update。

            if ((responseRow.getValue().compareTo(requestRow.getValue()) != 0)
                    ||
                    (responseRow.getTimes() != requestRow.getTimes())){
                // 不相等，则更新value数值和次数times。

                try {
                    planMapper.updatePlanRow(responseRow.getId(), requestRow.getValue(), requestRow.getTimes());
                } catch (Exception e){
                    System.out.println(e);
                }

            }

        }
    }


    @Test
    public void testFindPlanSection(){

        Integer aa = planMapper.getPlanSectionId(4,4,1);

        if(aa != null){
            System.out.println("section exists." + aa);
        } else {
            System.out.println("section not exists.");
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
                section.setRowList(new ArrayList<PlanRow>());
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

                if(section.getId() == row.getPlan_section_id()){
                    section.getRowList().add(row);
                    break;
                }
            }

        }

        // 将sectionList添加到plan中。
        plan.setSectionList(sectionList);
        return plan;
    }




}
