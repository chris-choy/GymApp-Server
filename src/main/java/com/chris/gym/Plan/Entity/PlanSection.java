package com.chris.gym.Plan.Entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.chris.gym.Sport.Sport;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class PlanSection implements Comparable<PlanSection> {

    public PlanSection(PlanSQLModel planSQLModel){
        id = planSQLModel.id;
        rowList = new ArrayList<PlanRow>();
        seq = planSQLModel.seq;
        plan_id = planSQLModel.plan_id;
        last_changed = planSQLModel.last_changed;
        // missing sport.
        sport = new Sport();
        sport.setId(planSQLModel.getSport_id());
        sport.setUser_id(planSQLModel.getUser_id());
        // missing rowList.
    }

    public PlanSection() {
    }

    //    public PlanSection(int id, int seq, int plan_id, Sport sport, List<PlanRow> rowList) {
//        this.id = id;
//        this.seq = seq;
//        this.plan_id = plan_id;
//        this.sport = sport;
//        this.rowList = rowList;
//    }




    public PlanSection(int id, int seq, int plan_id, Sport sport, List<PlanRow> rowList, Timestamp last_changed) {
        this.id = id;
        this.seq = seq;
        this.plan_id = plan_id;
        this.sport = sport;
        this.rowList = rowList;
        this.last_changed = last_changed;
    }

    // id,seq,plan_id,sport_id,last_changed
//    public PlanSection(int id, int seq, int plan_id, Sport sport, Timestamp last_changed) {
//        this.id = id;
//        this.seq = seq;
//        this.plan_id = plan_id;
//        this.sport = sport;
//        this.last_changed = last_changed;
//    }

    @JsonProperty("id")
    int id;
    @JsonProperty("seq")
    int seq;
    @JsonProperty("plan_id")
    int plan_id;
    @JsonProperty("sport")
    Sport sport;
    @JsonProperty("rowList")
    List<PlanRow> rowList;

    @JsonProperty("last_changed")
    Timestamp last_changed;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public int getPlan_id() {
        return plan_id;
    }

    public void setPlan_id(int plan_id) {
        this.plan_id = plan_id;
    }

    public Sport getSport() {
        return sport;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
    }

    public List<PlanRow> getRowList() {
        return rowList;
    }

    public void setRowList(List<PlanRow> rowList) {
        this.rowList = rowList;
    }



    public Timestamp getLast_changed() {
        return last_changed;
    }

    public void setLast_changed(Timestamp last_changed) {
        this.last_changed = last_changed;
    }


    @Override
    public int compareTo(PlanSection o) {

        // 正整数表示大于。
        // 负整数表示小于。
        // 0表示等于。

        // 假设this.seq=1, o.seq=2, 需要升序。
        // 故this需要小于o，也就是需要返回负整数，this.seq-o.seq<0。
        // 故返回值应该为this.seq-o.seq

        return this.seq - o.seq;
    }
}

