package com.chris.gym.User.Service;
import com.chris.gym.User.User;
import com.chris.gym.User.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLIntegrityConstraintViolationException;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class UserService {

    @Autowired
    UserMapper userMapper;



    public void createUser(User user) throws DataAccessException {
        userMapper.insert(
                user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.isGender(),
                user.getAge());
    }
}
