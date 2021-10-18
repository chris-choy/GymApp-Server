package com.chris.gym.MapperTest;


import com.alibaba.fastjson.JSON;
import com.chris.gym.Plan.Entity.Plan;
import com.chris.gym.Plan.Entity.PlanSQLModel;
import com.chris.gym.Plan.Entity.PlanSection;
import com.chris.gym.Plan.Mapper.PlanMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class PlanMapperTest {

    @Autowired
    PlanMapper planMapper;


    @Test
    public void testFindPlanWithUserIdAndSeq(){
       Plan plan = planMapper.findPlanWithUserIdAndSeq(4,1);
       System.out.println(JSON.toJSONString(plan));
    }

    @Test
    public void testFindPlanByPlanId(){
        List<PlanSQLModel> plan = planMapper.findPlanByPlanId(4);



        System.out.println(JSON.toJSONString(plan));
    }

    @Test
    public void testFindPlanSection(){
        PlanSQLModel plan = planMapper.findPlanSection(81,0);

        PlanSection section = new PlanSection(plan);

        System.out.println(JSON.toJSONString(section));
    }

}
