package com.chris.gym.Record.Entity;

import java.sql.Timestamp;
import java.util.Date;

public class RecordSQLModel {

    int id;

    String plan_name;

    Timestamp date;

    String sport;

    String sport_unit;

    int cost_time;
    int total_time;

    public int getTotal_time() {
        return total_time;
    }

    public void setTotal_time(int total_time) {
        this.total_time = total_time;
    }

    int times;

    Double value;

    int user_id;

    int record_id;
    int record_section_id;

    public int getRecord_id() {
        return record_id;
    }

    public void setRecord_id(int record_id) {
        this.record_id = record_id;
    }

    public int getRecord_section_id() {
        return record_section_id;
    }

    public void setRecord_section_id(int record_section_id) {
        this.record_section_id = record_section_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public String getSport_unit() {
        return sport_unit;
    }

    public void setSport_unit(String sport_unit) {
        this.sport_unit = sport_unit;
    }

    public int getCost_time() {
        return cost_time;
    }

    public void setCost_time(int cost_time) {
        this.cost_time = cost_time;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
