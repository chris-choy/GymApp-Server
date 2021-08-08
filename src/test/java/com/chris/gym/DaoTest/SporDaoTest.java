package com.chris.gym.DaoTest;


import com.chris.gym.GymApplication;
import com.chris.gym.Plan.Dao.PlanDao;
import com.chris.gym.Sport.Sport;
import com.chris.gym.Sport.SportDao;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GymApplication.class)
public class SporDaoTest {

    @Autowired
    SportDao sportDao;

    @Test
    public void testGetAllSports(){

        List<Sport> sports = sportDao.getAllSport(4);

        ObjectMapper mapper = new ObjectMapper();


        try {
            System.out.println(mapper.writeValueAsString(sports));
        } catch (Exception e) {
            System.out.println("err");
        }



    }

}
