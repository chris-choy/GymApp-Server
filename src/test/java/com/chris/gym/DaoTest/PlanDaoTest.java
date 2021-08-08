package com.chris.gym.DaoTest;


import com.chris.gym.GymApplication;
import com.chris.gym.Plan.Dao.PlanDao;
import com.chris.gym.Plan.Entity.Plan;
import com.chris.gym.User.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
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


    @Test
    public void testFindPlan(){
        Plan plan = planDao.getCompletePlan(4);

        ObjectMapper mapper = new ObjectMapper();

        try {
            String jsonStr = mapper.writeValueAsString(plan);
            System.out.println(jsonStr);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUpdatePlan(){
        String str = "{\"id\":4,\"seq\":1,\"last_changed\":1624527051000,\"name\":\"一二三四五六七八九十一\",\"sectionList\":[{\"id\":7,\"seq\":1,\"rowList\":[{\"id\":9,\"times\":33,\"restTime\":128,\"lastValue\":1,\"value\":1111,\"plan_section_id\":7,\"last_changed\":1628173992000,\"plan_id\":4,\"seq\":1}],\"last_changed\":1622627763000,\"sport\":{\"id\":3,\"user_id\":4,\"name\":\"sport1\",\"unit\":\"unit1\"},\"plan_id\":4},{\"id\":8,\"seq\":2,\"rowList\":[{\"id\":11,\"times\":66,\"restTime\":4,\"lastValue\":3,\"value\":333,\"plan_section_id\":8,\"last_changed\":1628147954000,\"plan_id\":4,\"seq\":1},{\"id\":12,\"times\":44,\"restTime\":6,\"lastValue\":5,\"value\":444,\"plan_section_id\":8,\"last_changed\":1626685287000,\"plan_id\":4,\"seq\":2},{\"id\":13,\"times\":55,\"restTime\":8,\"lastValue\":7,\"value\":555,\"plan_section_id\":8,\"last_changed\":1626685289000,\"plan_id\":4,\"seq\":3}],\"last_changed\":1622627763000,\"sport\":{\"id\":4,\"user_id\":4,\"name\":\"sport2\",\"unit\":\"unit2\"},\"plan_id\":4}],\"user_id\":1}";

        ObjectMapper mapper = new ObjectMapper();

        User user = new User();
        user.setId(4);

        try{
            Plan plan = mapper.readValue(str,Plan.class);

            Plan result = planDao.updatePlan(user, plan);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

}
