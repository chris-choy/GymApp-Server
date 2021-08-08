package com.chris.gym.Plan.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;

public class PlanA {

    public PlanA(PlanSQLModel planSQLModel){
        id = planSQLModel.id;
        plan_id = planSQLModel.plan_id;
        plan_section_id = planSQLModel.plan_section_id;
        seq = planSQLModel.seq;
        value = planSQLModel.value;
        times = planSQLModel.times;
        restTime = planSQLModel.restTime;
        lastValue = planSQLModel.lastValue;
        last_changed = planSQLModel.last_changed;

        // missing sport.
        // missing rowList.
    }

    public PlanA(int id, int plan_id, int plan_section_id, int seq, Double value, int times, int lastValue, int restTime) {
        this.id = id;
        this.plan_id = plan_id;
        this.plan_section_id = plan_section_id;
        this.seq = seq;
        this.value = value;
        this.times = times;
        this.lastValue = lastValue;
        this.restTime = restTime;
    }

//    public PlanA(){}



    @JsonProperty("id")
    int id;
    @JsonProperty("plan_id")
    int plan_id;
    @JsonProperty("plan_section_id")
    int plan_section_id;
    @JsonProperty("seq")
    int seq;
    @JsonProperty("value")
    Double value;
    @JsonProperty("times")
    int times;

    @JsonProperty("restTime")
    int restTime;
    @JsonProperty("lastValue")
    int lastValue;

    @JsonProperty("last_changed")
    Timestamp last_changed;

    public Timestamp getLast_changed() {
        return last_changed;
    }

    public void setLast_changed(Timestamp last_changed) {
        this.last_changed = last_changed;
    }

    public int getRestTime() {
        return restTime;
    }

    public void setRestTime(int restTime) {
        this.restTime = restTime;
    }

    public int getLastValue() {
        return lastValue;
    }

    public void setLastValue(int lastValue) {
        this.lastValue = lastValue;
    }



    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPlan_section_id() {
        return plan_section_id;
    }

    public void setPlan_section_id(int plan_section_id) {
        this.plan_section_id = plan_section_id;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public int getPlan_id() {
        return plan_id;
    }

    public void setPlan_id(int plan_id) {
        this.plan_id = plan_id;
    }
}
