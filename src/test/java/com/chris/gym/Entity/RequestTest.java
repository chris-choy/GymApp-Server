package com.chris.gym.Entity;


import com.alibaba.fastjson.JSON;
import com.chris.gym.GymApplicationTests;
import com.chris.gym.Plan.Entity.Plan;
import com.chris.gym.Plan.Entity.PlanRow;
import com.chris.gym.Plan.Entity.PlanSQLModel;
import com.chris.gym.Plan.Entity.PlanSection;
import com.chris.gym.Plan.Mapper.PlanMapper;
import com.chris.gym.Sport.Sport;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
//@SpringBootTest(classes = {GymApplicationTests.class})
@SpringBootTest()
public class RequestTest {

    @Autowired
    PlanMapper planMapper;

    static final Logger logger = LoggerFactory.getLogger(GymApplicationTests.class);


//    public RequestTest(PlanMapper planMapper){
//        this.planMapper = planMapper;
//    }

    @Test
    public void testConvertTheJsonToPlan(){
        String jsonStr = "{\"id\":4,\"seq\":0,\"user_id\":0,\"name\":\"plan1\",\"sectionList\":[{\"id\":0,\"rowList\":[{\"id\":0,\"plan_section_id\":0,\"seq\":1,\"value\":11}],\"seq\":1,\"plan_id\":4,\"sport\":{\"id\":0,\"name\":\"sport1\",\"unit\":\"unit1\"}},{\"id\":0,\"rowList\":[{\"id\":0,\"plan_section_id\":0,\"seq\":1,\"value\":33},{\"id\":0,\"plan_section_id\":0,\"seq\":2,\"value\":44},{\"id\":0,\"plan_section_id\":0,\"seq\":3,\"value\":55}],\"seq\":2,\"plan_id\":4,\"sport\":{\"id\":0,\"name\":\"sport2\",\"unit\":\"unit2\"}}],\"last_changed\":0}";

        Plan plan = JSON.parseObject(jsonStr, Plan.class);

        System.out.println(JSON.toJSONString(plan));

    }

    @Test
    public void testConvertTheJsonToSecion(){

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


//        Plan plan = JSON.parseObject(jsonStr, Plan.class);


        PlanSection section = JSON.parseObject(sectionJson, PlanSection.class);


//        System.out.println(JSON.toJSONString(plan));
        System.out.println(JSON.toJSONString(section));

    }

    @Test
    public void testConvertTheJsonToSecionList(){

        String sectionJson = "[\n" +
                "        {\n" +
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
                "        },\n" +
                "        {\n" +
                "            \"id\":0,\n" +
                "            \"rowList\":[\n" +
                "                {\n" +
                "                    \"id\":0,\n" +
                "                    \"plan_section_id\":0,\n" +
                "                    \"seq\":1,\n" +
                "                    \"value\":33\n" +
                "                },\n" +
                "                {\n" +
                "                    \"id\":0,\n" +
                "                    \"plan_section_id\":0,\n" +
                "                    \"seq\":2,\n" +
                "                    \"value\":44\n" +
                "                },\n" +
                "                {\n" +
                "                    \"id\":0,\n" +
                "                    \"plan_section_id\":0,\n" +
                "                    \"seq\":3,\n" +
                "                    \"value\":55\n" +
                "                }\n" +
                "            ],\n" +
                "            \"seq\":2,\n" +
                "            \"plan_id\":4,\n" +
                "            \"sport\":{\n" +
                "                \"id\":0,\n" +
                "                \"name\":\"sport2\",\n" +
                "                \"unit\":\"unit2\"\n" +
                "            }\n" +
                "        }\n" +
                "    ]";


//        Plan plan = JSON.parseObject(jsonStr, Plan.class);


        List<PlanSection> section = JSON.parseArray(sectionJson, PlanSection.class);


//        System.out.println(JSON.toJSONString(plan));
        System.out.println(JSON.toJSONString(section));

    }

    @Test
    public void testJsonToSport(){
        String jsonStr = "{\n" +
                "        \"id\":0,\n" +
                "        \"name\":\"sport1\",\n" +
                "        \"unit\":\"unit1\"\n" +
                "    }";

        Sport sport = JSON.parseObject(jsonStr, Sport.class);

        System.out.println(JSON.toJSONString(sport));
    }

    @Test
    public void testJsonToRow(){
        String jsonStr = "{\n" +
                "                    \"id\":0,\n" +
                "                    \"plan_section_id\":0,\n" +
                "                    \"seq\":1,\n" +
                "                    \"value\":33\n" +
                "                }";

        PlanRow row = JSON.parseObject(jsonStr, PlanRow.class);

//        PlanRow row = new PlanRow();

        System.out.println(JSON.toJSONString(row));
    }

    @Test
    public void testFindPlan(){
        Plan plan = null;

        try {
            plan = planMapper.findPlanWithUserIdAndSeq(4,1);
        } catch (Exception e){
            System.out.println(e);
        }
        System.out.println("---------------------------------------------------------------");
        System.out.println(plan);
        System.out.println("---------------------------------------------------------------");
//        List<Plan> plans = planMapper.findAllPlanWithUserId(4);
//        String jsonStr =
//        System.out.printf(String.valueOf(id));
    }

    @Test
    public void testFindPlanWUserIdAndSeq(){
        List<PlanSQLModel> plan = null;

        try {
//            plan = planMapper.findPlan(4,1);
        } catch (Exception e){
            System.out.println(e);
        }
        System.out.println("---------------------------------------------------------------");
        System.out.println(plan);
        System.out.println("---------------------------------------------------------------");
//        List<Plan> plans = planMapper.findAllPlanWithUserId(4);
//        String jsonStr =
//        System.out.printf(String.valueOf(id));
    }

    @Test
    public void testFindPlanSection(){
//        int id = planMapper.getPlanSectionId(4,4,5);

//        System.out.printf(JSON.toJSONString(planSection,true));

//        Integer aa ;

        Integer aa = planMapper.getPlanSectionId(4,4,1);

        if(aa != null){
            System.out.println("section exists." + aa);
        } else {
            System.out.println("section not exists.");
        }

//        try {
//            int id = planMapper.getPlanSectionId(4,4,5);
//        } catch (BindingException e){
//            if(e.)
//        }

//        System.out.println(id);




//        System.out.printf(String.valueOf(planSection));
    }

    @Test
    public void testInsertPlan(){
        // 测试是否能够正常插入，并用SELECT LAST_INSERT_ID()返回新创建的Plan的id。

        try {
            planMapper.insertPlan("plan test",4,2);
        }  catch (Exception e){

        }

        int id = planMapper.get_last_insert_id();
        if (id == 0){
            System.out.println("Error in inserting plan.");
        } else {
            System.out.println("Successful. id=" + id);
        }
    }

    @Test
    public void testGetLastInsertId(){
        int id = planMapper.get_last_insert_id();
        if (id == 0){
            System.out.println("Error in inserting plan.");
        } else {
            System.out.println("Successful. id=" + id);
        }
    }

//    @Test
//    public void testLogger(){
//        logger.info("log.success.");
//    }


}
