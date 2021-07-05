package com.chris.gym.Sport;

import com.alibaba.fastjson.annotation.JSONField;

public class Sport {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    @JSONField(name = "name")
    String name;

    @JSONField(name = "unit")
    String unit;

    @JSONField(name = "id")
    int id;

    @JSONField(name = "user_id")
    int user_id;
}
