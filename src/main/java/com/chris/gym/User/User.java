package com.chris.gym.User;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class User {

    @JSONField(name = "id")
    private int id;

    @JSONField(name = "name")
    private String name;

    @JSONField(name = "username")
    private String username;

    @JSONField(name = "password")
    private String password;

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    @JSONField(name = "gender")
    private boolean gender;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @JSONField(name = "age")
    private int age;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static User getUser(UserMapper userMapper){

        // 获取User
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
//            LOG.debug("no authentication in security context found");
//            return "no authentication in security context found.";
            return null;
        } else {
            // 获取username
            String username = "";
            if (authentication.getPrincipal() instanceof UserDetails) {
                UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
                username = springSecurityUser.getUsername();
            } else if (authentication.getPrincipal() instanceof String) {
                username = (String) authentication.getPrincipal();
            }
            // 获取user
            User user = userMapper.findByUsername(username);

            return user;

        }

    }

}
