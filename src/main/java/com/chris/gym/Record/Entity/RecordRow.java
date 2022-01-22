package com.chris.gym.Record.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;

public class RecordRow {

    @JsonProperty("cost_time")
    int cost_time;

    @JsonProperty("value")
    Double value;

    @JsonProperty("times")
    int times;

    @JsonProperty("date")
    Timestamp date;

    int record_section_id;

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public int getRecord_section_id() {
        return record_section_id;
    }



    public void setRecord_section_id(int record_section_id) {
        this.record_section_id = record_section_id;
    }

    public int getCost_time() {
        return cost_time;
    }

    public void setCost_time(int cost_time) {
        this.cost_time = cost_time;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public RecordRow(RecordSQLModel sqlModel){
        this.value = sqlModel.getValue();
        this.times = sqlModel.getTimes();
        this.cost_time = sqlModel.getCost_time();
        this.record_section_id = sqlModel.getRecord_section_id();
    }

    public RecordRow() {
    }
}
