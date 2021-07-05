package com.chris.gym.Sport;

import com.chris.gym.User.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SportMapper {

//    @Select("SELECT * FROM t_user")
//    List<User> findAll();
//
//    @Insert("insert into t_user(username, password, name) values(#{username}, #{password}, #{name})")
//    @Options(useGeneratedKeys = true, keyProperty = "id")
//    int insert(@Param("username") String username,
//               @Param("password") String password,
//               @Param("name") String name);
//
//    @Select("select * from t_user where id = #{id}")
//    User findById(@Param("id") int id);
//
//    @Select("select * from t_user where username = #{username}")
//    User findByUsername(@Param("username") String username);

    @Select("SELECT * FROM t_sport WHERE user_id = #{user_id}")
    public List<Sport> findAllSportByUserId(@Param("user_id") int user_id);

    @Select("SELECT * FROM t_sport WHERE id = #{id}")
    public Sport findSportById(@Param("id") int id);

    @Insert("INSERT INTO t_sport(name,unit,user_id) values(#{name},#{unit},#{user_id})")
    public int insert(@Param("name") String name,
                      @Param("unit") String unit,
                      @Param("user_id") int user_id);

    @Select("SELECT * FROM t_sport WHERE user_id = #{user_id} and name = #{name} ")
    public Sport findSportByName(@Param("user_id") int user_id,
                                      @Param("name") String name);




}
