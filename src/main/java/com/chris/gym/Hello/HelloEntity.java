package com.chris.gym.Hello;

import com.chris.gym.Plan.Entity.PlanSection;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;
import java.util.List;

public class HelloEntity {


    @ApiModelProperty(example = "1")
    @JsonProperty("id")
    private int id;

    @ApiModelProperty(example = "Plan1")
    @JsonProperty("name")
    private String name;

    @ApiModelProperty(example = "1")
    @JsonProperty("seq")
    private int seq;

    @JsonProperty("user_id")
    private int user_id;

    @JsonProperty("sectionList")
    List<HelloSection> sectionList;

    @JsonProperty("last_changed")
    Timestamp last_changed;


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

    public List<HelloSection> getSectionList() {
        return sectionList;
    }

    public void setSectionList(List<HelloSection> sectionList) {
        this.sectionList = sectionList;
    }

    public Timestamp getLast_changed() {
        return last_changed;
    }

    public void setLast_changed(Timestamp last_changed) {
        this.last_changed = last_changed;
    }
}
