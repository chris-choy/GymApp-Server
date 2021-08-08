package com.chris.gym.Plan.Entity;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;
import java.util.List;

//@Configuration
//@Component


public class Plan {

    @JsonProperty("id")
    private int id;

    @JsonProperty("name")
    private String name;


    // sequence.
    @JsonProperty("seq")
    private int seq;

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }


    // id.
    @JsonProperty("user_id")
    private int user_id;


    // SectionList.
    @JsonProperty("sectionList")
    List<PlanSection> sectionList;
    public List<PlanSection> getSectionList() {
        return sectionList;
    }



    @JsonProperty("last_changed")
    Timestamp last_changed;

    public Timestamp getLast_changed() {
        return last_changed;
    }

    public void setLast_changed(Timestamp last_changed) {
        this.last_changed = last_changed;
    }


    public void setSectionList(List<PlanSection> sectionList) {
        this.sectionList = sectionList;
    }

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

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Plan(PlanSQLModel planSQLModel){
        id = planSQLModel.id;
        name = planSQLModel.name;
        user_id = planSQLModel.user_id;
        seq = planSQLModel.seq;
        last_changed = planSQLModel.last_changed;
    }

//    public Plan(int id, String name, int seq, int user_id) {
//        this.id = id;
//        this.name = name;
//        this.seq = seq;
//        this.user_id = user_id;
//    }

//    public Plan(int id, String name, int seq, Timestamp last_changed) {
//        this.id = id;
//        this.name = name;
//        this.seq = seq;
//        this.last_changed = last_changed;
//    }

//    id,name,seq,user_id,last_changed,

    public Plan(int id, String name, int seq, int user_id, Timestamp last_changed) {
        this.id = id;
        this.name = name;
        this.seq = seq;
        this.user_id = user_id;
        this.last_changed = last_changed;
    }


//    @JsonCreator
//    public Plan(@JsonProperty("id") int id,
//                @JsonProperty("name") String name,
//                @JsonProperty("seq") int seq,
//                @JsonProperty("user_id") int user_id,
//                @JsonProperty("sectionList") List<PlanSection> sectionList,
//                @JsonProperty("last_changed") Timestamp last_changed) {
//        this.id = id;
//        this.name = name;
//        this.seq = seq;
//        this.user_id = user_id;
//        this.sectionList = sectionList;
//        this.last_changed = last_changed;
//    }


    public Plan(int id, String name, int seq, int user_id, List<PlanSection> sectionList, Timestamp last_changed) {
        this.id = id;
        this.name = name;
        this.seq = seq;
        this.user_id = user_id;
        this.sectionList = sectionList;
        this.last_changed = last_changed;
    }

    public Plan() {
    }

    //    public Plan(){};







//    public Plan(List<PlanSQLModel> sqlModels) {
//        Plan plan = null;
//        List<PlanSection> sectionList = new ArrayList<PlanSection>();
//        List<PlanRow> rowList = new ArrayList<PlanRow>();
//
//        for (int i = 0; i< sqlModels.size(); i++){
//            PlanSQLModel p = sqlModels.get(i);
//
//            // 判定为Plan。
//            if(!p.getName().isBlank()){
//
//                id = p.id;
//                name = p.name;
//                user_id = p.user_id;
//                seq = p.seq;
//                last_changed = p.last_changed;
//
//            }
//
//            // 判定为PlanSection。
//            if(p.getSport_id() != 0){
//                PlanSection section = new PlanSection(p);
//                section.setSport(sportMapper.findSportById(p.getSport_id()));
//                sectionList.add(section);
//
//            }
//
//            // 判定为PlanRow。
//            if(p.getValue() != 0){
//                rowList.add(new PlanRow(p));
//            }
//        }
//
//        // sectionList根据seq升序排序。
//        Collections.sort(sectionList);
//
//        // 将PlanRow加进去对应的PlanSection中。
//        for( int i=0; i<rowList.size(); i++){
//            PlanRow row = rowList.get(i);
//
//            for (PlanSection section :
//                    sectionList) {
////                System.out.println("    " + section.getId());
//                if(section.getId() == row.getPlan_section_id()){
//                    if(section.getRowList() != null){
//                        section.getRowList().add(row);
//                    } else {
//                        // 创建rowList。
//                        List<PlanRow> rows = new ArrayList<PlanRow>();
//                        rows.add(row);
//                        section.setRowList(rows);
//                    }
//                    break;
//                }
//            }
//        }
//
//        // 将sectionList添加到plan中。
//        setSectionList(sectionList);
//
//    }



}
