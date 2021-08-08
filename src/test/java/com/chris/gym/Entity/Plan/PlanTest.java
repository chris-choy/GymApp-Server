package com.chris.gym.Entity.Plan;


import com.chris.gym.Plan.Entity.*;
import com.chris.gym.Plan.Mapper.PlanMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
//import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class PlanTest {

    @Autowired
    PlanMapper planMapper;

    @Test
    public void testConstructor(){
        List<PlanSQLModel> sqlModels= planMapper.findPlanByPlanId(4);

//        Plan plan = new Plan(sqlModels);

//        System.out.println(JSON.toJSONString(plan));

    }

    @Test
    public void testJsonToPlan(){
        String str = "{\"id\":4,\"seq\":1,\"last_changed\":1624527051000,\"name\":\"plan_test_1\",\"sectionList\":[{\"id\":7,\"seq\":1,\"rowList\":[{\"id\":9,\"times\":22,\"restTime\":2,\"lastValue\":1,\"value\":1111,\"plan_section_id\":7,\"last_changed\":1626685285000,\"plan_id\":4,\"seq\":1}],\"last_changed\":1622627763000,\"sport\":{\"id\":3,\"user_id\":4,\"name\":\"sport1\",\"unit\":\"unit1\"},\"plan_id\":4},{\"id\":8,\"seq\":2,\"rowList\":[{\"id\":11,\"times\":33,\"restTime\":4,\"lastValue\":3,\"value\":333,\"plan_section_id\":8,\"last_changed\":1626685287000,\"plan_id\":4,\"seq\":1},{\"id\":12,\"times\":44,\"restTime\":6,\"lastValue\":5,\"value\":444,\"plan_section_id\":8,\"last_changed\":1626685287000,\"plan_id\":4,\"seq\":2},{\"id\":13,\"times\":55,\"restTime\":8,\"lastValue\":7,\"value\":555,\"plan_section_id\":8,\"last_changed\":1626685289000,\"plan_id\":4,\"seq\":3}],\"last_changed\":1622627763000,\"sport\":{\"id\":4,\"user_id\":4,\"name\":\"sport2\",\"unit\":\"unit2\"},\"plan_id\":4}],\"user_id\":1}";

        ObjectMapper mapper = new ObjectMapper();

        try {
            Plan plan = mapper.readValue(str,Plan.class);

            System.out.println(plan);

        }catch (Exception e){
            System.out.println(e);
        }
    }


    @Test
    public void testTimeStamp(){
        String str = "1624527051000";


        ObjectMapper mapper = new ObjectMapper();
        try {
            Timestamp timestamp = mapper.readValue(str, Timestamp.class);

            System.out.println(timestamp);

        }catch (Exception e){
            System.out.println(e);
        }
    }

    @Test
    public void testJsonToPlanSection(){
        String str = "{\"id\":7,\"seq\":1,\"rowList\":[{\"id\":9,\"times\":22,\"restTime\":2,\"lastValue\":1,\"value\":1111,\"plan_section_id\":7,\"last_changed\":1626685285000,\"plan_id\":4,\"seq\":1}],\"last_changed\":1622627763000,\"sport\":{\"id\":3,\"user_id\":4,\"name\":\"sport1\",\"unit\":\"unit1\"},\"plan_id\":4}";

        ObjectMapper mapper = new ObjectMapper();

        try {
            PlanSection plan = mapper.readValue(str, PlanSection.class);

            System.out.println(plan);

        }catch (Exception e){
            System.out.println(e);
        }
    }

    @Test
    public void testJsonToPlanRow(){
        String str = "{\"id\":9,\"times\":22,\"restTime\":2,\"lastValue\":1,\"value\":1111,\"plan_section_id\":7,\"last_changed\":1626685285000,\"plan_id\":4,\"seq\":1}";

        ObjectMapper mapper = new ObjectMapper();

        try {
            PlanRow plan = mapper.readValue(str, PlanRow.class);

            System.out.println(plan);

        }catch (Exception e){
            System.out.println(e);
        }
    }

    @Test
    public void testJsonToPlanA(){
//        String str = "{\"id\":9,\"times\":22,\"restTime\":2,\"lastValue\":1,\"value\":1111,\"plan_section_id\":7,\"plan_id\":4,\"seq\":1}";
        String str = "{\"id\":9,\"times\":22,\"restTime\":2,\"lastValue\":1,\"value\":1111,\"plan_section_id\":7,\"last_changed\":1626685285000,\"plan_id\":4,\"seq\":1}";
        ObjectMapper mapper = new ObjectMapper();

        try {
            PlanA plan = mapper.readValue(str, PlanA.class);

            System.out.println(plan);

        }catch (Exception e){
            System.out.println(e);
        }
    }

}
