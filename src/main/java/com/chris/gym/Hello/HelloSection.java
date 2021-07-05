package com.chris.gym.Hello;

import com.alibaba.fastjson.annotation.JSONField;
import com.chris.gym.Plan.Entity.PlanRow;
import com.chris.gym.Sport.Sport;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;
import java.util.List;

public class HelloSection {

    @JsonProperty("id")
    int id;
    @JsonProperty("seq")
    int seq;
    @JsonProperty("plan_id")
    int plan_id;
    @JsonProperty("sport")
    Sport sport;
    @JsonProperty("rowList")
    List<HelloRow> rowList;
    @JsonProperty("last_changed")
    Timestamp last_changed;


}
