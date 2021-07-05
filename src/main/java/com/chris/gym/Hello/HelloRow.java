package com.chris.gym.Hello;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;

public class HelloRow {

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

}
