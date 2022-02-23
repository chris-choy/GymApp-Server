package com.chris.gym.Record.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class RecordSection {


    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @JsonProperty("sport_name")
    String sport_name;

    public String getSport_unit() {
        return sport_unit;
    }

    public void setSport_unit(String sport_unit) {
        this.sport_unit = sport_unit;
    }

    @JsonProperty("sport_unit")
    String sport_unit;

    List<RecordRow> rowList = new ArrayList<>();

    public String getSport_name() {
        return sport_name;
    }

    public void setSport_name(String sport_name) {
        this.sport_name = sport_name;
    }

    public List<RecordRow> getRowList() {
        return rowList;
    }

    public void setRowList(List<RecordRow> rowList) {
        this.rowList = rowList;
    }

    public RecordSection(RecordSQLModel sqlModel) {
        this.id = sqlModel.id;
        this.sport_name = sqlModel.getSport();
        this.sport_unit = sqlModel.getSport_unit();
    }

    public RecordSection(){}
}
