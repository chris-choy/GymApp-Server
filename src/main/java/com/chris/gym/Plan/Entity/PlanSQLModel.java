package com.chris.gym.Plan.Entity;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class PlanSQLModel {
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

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getPlan_id() {
        return plan_id;
    }

    public void setPlan_id(int plan_id) {
        this.plan_id = plan_id;
    }

    public int getPlan_section_id() {
        return plan_section_id;
    }

    public void setPlan_section_id(int plan_section_id) {
        this.plan_section_id = plan_section_id;
    }

    public int getSport_id() {
        return sport_id;
    }

    public void setSport_id(int sport_id) {
        this.sport_id = sport_id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    int id;
    String name;
    int seq;
    int user_id;
    int plan_id;
    int plan_section_id;
    int sport_id;
    double value;
    int times;
    Timestamp last_changed;

    public Timestamp getLast_changed() {
        return last_changed;
    }

    public void setLast_changed(Timestamp last_changed) {
        this.last_changed = last_changed;
    }
}
