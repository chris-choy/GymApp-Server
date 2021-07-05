package com.chris.gym.Entity.Plan;


import com.chris.gym.Plan.Mapper.PlanMapper;
import com.chris.gym.Plan.Entity.PlanSQLModel;
import org.junit.Test;
//import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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

}
