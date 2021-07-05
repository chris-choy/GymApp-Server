package com.chris.gym.User;

//import com.example.hellodruid.demo.entity.User;
import org.apache.ibatis.annotations.*;
//import org.springframework.security.core.userdetails.User;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM t_user")
    List<User> findAll();

    @Insert("insert into t_user(username, password, name, gender, age) values(" +
            "#{username}, #{password}, #{name}, #{gender}, #{age} )")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(@Param("username") String username,
               @Param("password") String password,
               @Param("name") String name,
               @Param("gender") Boolean gender,
               @Param("age") int age);

    @Select("select * from t_user where id = #{id}")
    User findById(@Param("id") int id);

    @Select("select * from t_user where username = #{username}")
    User findByUsername(@Param("username") String username);

}
