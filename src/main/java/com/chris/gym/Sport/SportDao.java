package com.chris.gym.Sport;


import com.chris.gym.User.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SportDao {

    public SportDao(SportMapper sportMapper) {
        this.sportMapper = sportMapper;
    }

    final SportMapper sportMapper ;

    public List<Sport> getAllSport(int user_id){

        List<Sport> sports =  sportMapper.findAllSportByUserId(user_id);

        return sports;
    }
}
