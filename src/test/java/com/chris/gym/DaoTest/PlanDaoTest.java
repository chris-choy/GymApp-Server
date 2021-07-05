package com.chris.gym.DaoTest;


import com.chris.gym.GymApplication;
import com.chris.gym.Plan.Dao.PlanDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GymApplication.class)
public class PlanDaoTest {

    @Autowired
    PlanDao planDao;

    @Test
    public void testSavePlan(){
        System.out.println(planDao);
    }
}
