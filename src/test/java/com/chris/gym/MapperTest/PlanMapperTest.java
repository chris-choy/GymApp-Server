package com.chris.gym.MapperTest;


import com.alibaba.fastjson.JSON;
import com.chris.gym.Plan.Entity.Plan;
import com.chris.gym.Plan.Mapper.PlanMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
}
