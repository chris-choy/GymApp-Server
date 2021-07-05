package com.chris.gym.Plan.Entity;

import com.alibaba.fastjson.annotation.JSONField;

public class PlanRow {

    public PlanRow(PlanSQLModel planSQLModel){
        id = planSQLModel.id;
        plan_id = planSQLModel.plan_id;
        plan_section_id = planSQLModel.plan_section_id;
        seq = planSQLModel.seq;
        value = planSQLModel.value;
        times = planSQLModel.times;

        // missing sport.
        // missing rowList.
    }

    public PlanRow(int id, int plan_id, int plan_section_id, int seq, Double value, int times) {
        this.id = id;
        this.plan_id = plan_id;
        this.plan_section_id = plan_section_id;
        this.seq = seq;
        this.value = value;
        this.times = times;
    }

    @JSONField(name = "id")
    int id;
    @JSONField(name = "plan_id")
    int plan_id;
    @JSONField(name = "plan_section_id")
    int plan_section_id;
    @JSONField(name = "seq")
    int seq;
    @JSONField(name = "value")
    Double value;
    @JSONField(name = "times")
    int times;

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
