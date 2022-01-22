package com.chris.gym.Record.Entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Record {

    @JsonProperty("id")
    int id;

    @JsonProperty("plan_name")
    String plan_name;

    @JsonProperty("date")
    Timestamp date;

    @JsonProperty("user_id")
    int user_id;

    @JsonProperty("total_time")
    int total_time;

    public int getTotal_time() {
        return total_time;
    }

    public void setTotal_time(int total_time) {
        this.total_time = total_time;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getPlan_name() {
        return plan_name;
    }

    public void setPlan_name(String plan_name) {
        this.plan_name = plan_name;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public List<RecordSection> getRecordSectionList() {
        return recordSectionList;
    }

    public void setRecordSectionList(List<RecordSection> recordSectionList) {
        this.recordSectionList = recordSectionList;
    }

    @JsonProperty("recordSectionList")
    List<RecordSection> recordSectionList = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Record(RecordSQLModel sqlModel) {
        this.id = sqlModel.getId();
        this.plan_name = sqlModel.getPlan_name();
        this.date = sqlModel.getDate();
        this.total_time = sqlModel.getTotal_time();
    }

    public Record(){};

}


