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




}
