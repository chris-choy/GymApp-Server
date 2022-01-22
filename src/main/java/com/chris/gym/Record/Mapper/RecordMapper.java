package com.chris.gym.Record.Mapper;


import com.chris.gym.Record.Entity.Record;
import com.chris.gym.Record.Entity.RecordRow;
import com.chris.gym.Record.Entity.RecordSQLModel;
import com.chris.gym.Record.Entity.RecordSection;
import org.apache.ibatis.annotations.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface RecordMapper {

//    @Insert("insert into t_record " +
//            "(user_id, plan_name, date) " +
//            "values" +
//            "(#{user_id}, #{plan_name}, #{date})")
//    public int createRecord(@Param("user_id") int user_id,
//                             @Param("plan_name") String plan_name,
//                             @Param("date") Timestamp date);

    @Insert("insert into t_record " +
            "(user_id, plan_name, date, total_time) " +
            "values" +
            "(#{user_id}, #{record.plan_name}, #{record.date}, #{record.total_time})")
    @Options(useGeneratedKeys = true, keyProperty = "record.id", keyColumn = "record.id")
    public void createRecord(Record record, @Param("user_id") int user_id);

    @Insert("insert into t_record " +
            "(user_id, record_id, sport, sport_unit) " +
            "values" +
            "(#{user_id}, #{record_id}, #{section.sport_name}, #{section.sport_unit})")
    @Options(useGeneratedKeys = true, keyProperty = "section.id", keyColumn = "id")
    public void createRecordSection(@Param("user_id") int user_id,
                                    @Param("record_id") int record_id,
                                    @Param("section") RecordSection section);



    @Insert("insert into t_record " +
            "(user_id, record_id, record_section_id, value, times, cost_time, date)" +
            "values" +
            "(#{user_id}, #{record_id}, #{record_section_id}, #{row.value}, #{row.times}, #{row.cost_time}, #{row.date})")
    public void createRecordRow(@Param("user_id") int user_id,
                                @Param("record_id") int record_id,
                                @Param("record_section_id") int record_section_id,
                                @Param("row") RecordRow row);


    @Select("select * from t_record where user_id=#{user_id}")
    public List<RecordSQLModel> getAllRecords(@Param("user_id") int user_id);

    @Select("select * from t_record where id=#{id} or record_id=#{id}")
    public List<RecordSQLModel> getRecord(@Param("id") int id);





}
