package com.chris.gym.Plan.Mapper;

import com.chris.gym.Plan.Entity.Plan;
import com.chris.gym.Plan.Entity.PlanRow;
import com.chris.gym.Plan.Entity.PlanSQLModel;
import com.chris.gym.Plan.Entity.PlanSection;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PlanMapper {

    @Select("SELECT id,name,seq,user_id,last_changed FROM t_plan where " +
            "user_id = #{user_id} and " +
            "seq = #{seq} and " +
            "plan_id = 0")
    public Plan findPlanWithUserIdAndSeq(@Param("user_id") int user_id,
                                         @Param("seq") int seq);

    @Select("SELECT id,name,seq,user_id,last_changed FROM t_plan where user_id = #{user_id} and name != \"\"")
    public List<Plan> findAllPlanWithUserId(@Param("user_id") int user_id);

    @Select("SELECT id FROM t_plan where user_id = #{user_id} and name != \"\"")
    public List<Integer> findAllPlanId(@Param("user_id") int id);

    @Select("select * from t_plan where plan_id = #{plan_id} or id = #{plan_id}" )
    public List<PlanSQLModel> findPlanByPlanId(@Param("plan_id") int plan_id);

//    @Select("select * from t_plan where " +
//            "(user_id = #{user_id} and seq = #{seq} and plan_id = 0) or " +
//            "plan_id in ( select id from t_plan where user_id = #{user_id} and plan_id = 0 and seq = #{seq})" )
//    public List<PlanSQLModel> findPlan(@Param("user_id") int user_id, @Param("seq") int seq);


    @Insert("INSERT INTO t_plan( name, user_id, seq ) VALUES(#{name},#{user_id},#{seq})")
    public void insertPlan(@Param("name") String name,
                           @Param("user_id") int user_id,
                           @Param("seq") int seq);

    @Update("update t_plan set name = #{name} where id = #{plan_id}")
    public long updatePlan(@Param("plan_id") int id,
                           @Param("name") String name);



    @Update("update t_plan set sport_id = #{sport_id} where id = #{id}")
    public void updatePlanSectionSportId(@Param("id") int id,
                                         @Param("sport_id") int sport_id);


    @Update("update t_plan set value = #{value}, times=#{times}, restTime=#{restTime} where " +
            "id = #{id}")
    public void updatePlanRow(@Param("id") int id,
                              @Param("value") double value,
                              @Param("times") int times,
                              @Param("restTime") int restTime);


//    public long updatePlanRow(@Param("id") int id);

    @Insert("INSERT INTO t_plan( user_id, plan_id, seq, sport_id ) VALUES(#{user_id}, #{plan_id},#{seq}, #{sport_id})")
    public long insertPlanSection(@Param("user_id") int user_id,
                                  @Param("plan_id") int plan_id,
                                  @Param("seq") int seq,
                                  @Param("sport_id") int sport_id);

    @Insert("INSERT INTO t_plan( user_id, plan_id ,plan_section_id, seq, value, times, restTime ) VALUES(#{user_id}, #{plan_id}, #{plan_section_id}, #{seq}, #{value}, #{times}, #{restTime})")
    public long insertPlanRow(@Param("user_id") int user_id,
                              @Param("plan_id") int plan_id,
                              @Param("plan_section_id") int plan_section_id,
                              @Param("seq") int seq,
                              @Param("value") double value,
                              @Param("times") int times,
                              @Param("restTime") int restTime);

    /*
    *  ?????????????????????id???
    *  ????????????????????????0??? ????????????null???
    *  ??????????????????????????????id???
    * */
    @Select("SELECT LAST_INSERT_ID()")
    public int
    get_last_insert_id();

//    @Select("")
//    public long findPlanSectionByName(@Param("user_id") int user_id,
//                               @Param("name") String name);

    @Select("SELECT * FROM t_plan WHERE " +
//            "user_id = #{user_id} and " +
//            "plan_id = #{plan_id} and " +
            "id = #{id}")
    public PlanSection findPlanSectionById(
//                                    @Param("user_id") int user_id,
//                                    @Param("plan_id") int plan_id,
                                    @Param("id") int id);



    /*
    * ???????????????plan_id, seq, plan.
    * ??????????????? PlanSQLModel
    * */
//    @Select("SELECT * FROM t_plan WHERE " +
//            "plan_id = #{plan_id} and " +
//            "seq = #{seq}")
//    public PlanSQLModel findPlanSection(@Param("plan_id") int plan_id,
//                                       @Param("seq") int seq);


    /*
     * ???????????????plan_id, seq, plan.
     * ??????????????? PlanSQLModel
     * */
    @Select("SELECT * FROM t_plan WHERE " +
            "plan_id = #{plan_id} and " +
            "seq = #{seq} and +" +
            "plan_section_id = 0")
    public PlanSection findPlanSection(@Param("plan_id") int plan_id,
                                       @Param("seq") int seq);

    // this.id = id;
    //        this.plan_id = plan_id;
    //        this.plan_section_id = plan_section_id;
    //        this.seq = seq;
    //        this.value = value;
    @Select("SELECT id,plan_id,plan_section_id,seq,value,times FROM t_plan WHERE " +
            "plan_section_id = #{plan_section_id} and " +
            "seq = #{seq}")
    public PlanRow findPlanRow(@Param("plan_section_id") int plan_section_id,
                               @Param("seq") int seq);


    @Select("SELECT * FROM t_plan WHERE " +
//            "user_id = #{user_id} and " +
//            "plan_id = #{plan_id} and " +
            "plan_section_id = #{plan_section_id} ORDER BY 'seq'")
    public List<PlanRow> findPlanRowBySectionId(@Param("plan_section_id") int plan_section_id);




    /*
    *  getXXXId(...) ???????????????????????????
    * */

    @Select("select id from t_plan where " +
            "user_id=#{user_id} and " +
            "plan_id=#{plan_id} and " +
            "seq=#{seq} and " +
            "plan_section_id=#{plan_section_id}")
    public Integer getPlanRowId(@Param("user_id") int user_id,
                               @Param("plan_id") int plan_id,
                               @Param("plan_section_id") int plan_section_id,
                               @Param("seq") int seq);

    @Select("select id from t_plan where " +
            "user_id=#{user_id} and " +
            "plan_id=#{plan_id} and " +
            "seq=#{seq} and " +
            "plan_section_id=0")
    public Integer getPlanSectionId(@Param("user_id") int user_id,
                               @Param("plan_id") int plan_id,
                               @Param("seq") int seq);

    @Select("select id from t_plan where " +
            "user_id=#{user_id} and " +
            "plan_id=0 and " +
            "seq=#{seq}")
    public Integer getPlanId(@Param("user_id") int user_id,
                                   @Param("seq") int seq);



    // Delete
    @Delete("delete from t_plan where id = #{id}")
    public void deletePlanRow(@Param("id") int id);


    // ?????????
    // ??????PlanSection?????????????????????PlanRow??????????????????
    @Delete("delete from t_plan where " +
            "id = #{id} or " +
            "plan_section_id = #{id}")
    public void deletePlanSection(@Param("id") int id);

    @Delete("delete from t_plan where " +
            "id = #{id} or " +
            "plan_id = #{id}")
    public void deletePlan(@Param("id") int id);

    /**
     * ??????Plan?????????????????????Plan??????seq??????1???
     * @param seq
     */
    @Update("update t_plan set seq=seq-1 where " +
            "user_id = 4 and " +
            "plan_id = 0 and " +
            "seq>#{seq}")
    public void decreasePlanSeq(@Param("seq") int seq);

}
