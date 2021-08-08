package com.chris.gym.Plan.Dao;


import com.chris.gym.Plan.Entity.Plan;
import com.chris.gym.Plan.Entity.PlanRow;
import com.chris.gym.Plan.Entity.PlanSQLModel;
import com.chris.gym.Plan.Entity.PlanSection;
import com.chris.gym.Plan.Mapper.PlanMapper;
import com.chris.gym.Sport.SportMapper;
import com.chris.gym.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Repository
public class PlanDao {

    @Autowired
    PlanMapper planMapper;

    @Autowired
    SportMapper sportMapper;



    public Plan updatePlan(User user,Plan requestPlan){
        // 更新Plan的信息。

        // 1. 检查Plan是否存在，存在则update，不存在则insert。
        Plan responsePlan = null;

        // 1.1 id不等于0，则数据库中可能存在。
        if (requestPlan.getId() != 0){
            // 1.1.1 先尝试获取服务器上的Plan内容。
            try {

                // 因为从数据库获取的数据不是按照Plan-PlanSection-PlanRow结构分层，
                // 所以借助PlanSQLModel，将数据按照以上层级构建Plan数据实体。
                List<PlanSQLModel> sqlModels = planMapper.findPlanByPlanId(requestPlan.getId());

                if (sqlModels != null) {
                    responsePlan = parseToPlan(sqlModels);
                }
            } catch (Exception e){
                System.out.println("Error: 检查Plan是否存在中," + e + "." );
                return null;
//                return new ResponseEntity<String>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        if( responsePlan == null ){
            // 1.2 创建plan。
            try {
                planMapper.insertPlan(requestPlan.getName(),user.getId(), requestPlan.getSeq());
                responsePlan = planMapper.findPlanWithUserIdAndSeq(user.getId(), requestPlan.getSeq());
            } catch (Exception e){
                System.out.println(e);
            }

        } else {
            // 1.2 更新plan。
            if(responsePlan.getName().contentEquals(requestPlan.getName()) == false){
                // 需要修改的plan和数据库中的plan名称不一致。
                // 故update修改，否则不作任何改动。
                try {
                    planMapper.updatePlan(responsePlan.getId(), requestPlan.getName());
                } catch (Exception e){
                    System.out.println(e);
                }
            }
        }

        // 2. 下一步骤，对PlanSection和PlanRow进行检查修改。
        //      如果数据库已存在这个Plan的某个Section，则直接修改参数。
        for(int i = 0; i<requestPlan.getSectionList().size(); i++){
            if ( i < responsePlan.getSectionList().size()){
                // 2.1 如果数据库已存在这个Plan的某个Section，
                //       则直接传入对应的Section修改参数。
                updateSection(
                        responsePlan,
                        responsePlan.getSectionList().get(i),
                        requestPlan.getSectionList().get(i));
            } else {
                // 2.2 如果不存在，直接传入null，自动创建。
                updateSection(
                        responsePlan,
                        null,
                        requestPlan.getSectionList().get(i));
            }

        }

        // 3. 删除多余的section。
        int difference = responsePlan.getSectionList().size() - requestPlan.getSectionList().size();
        if(difference > 0){
            // 从后往前删除。
            // 比如目标数量是2，当前数量是4。
            // i从2-3（3为4-1）。
            for (int i = requestPlan.getSectionList().size() ;
                 i < responsePlan.getSectionList().size() ;
                 i++ ){
                try {
                    planMapper.deletePlanSection(responsePlan.getSectionList().get(i).getId());
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }

        return responsePlan;
    }

    private void updateSection(Plan plan, PlanSection currentSection , PlanSection requestSection){
        // 检查section本身是否需要改动。


        PlanSection responseSection = currentSection;

        if( responseSection == null ){
            // 数据库中不存在。
            // 创建planSection。
            try {
                planMapper.insertPlanSection(plan.getUser_id(), plan.getId(), requestSection.getSeq(), requestSection.getSport().getId());

                PlanSQLModel sqlModel = planMapper.findPlanSection(plan.getId(), requestSection.getSeq());
                if (sqlModel != null){
                    responseSection = new PlanSection(sqlModel);
                }

            } catch (Exception e){
                System.out.println(e);
            }

        } else {
            // 更新planSection。
            // 对比需要修改的和已经存在的"运动信息Sport"是否一致，否则update。
            if(responseSection.getSport().getName().contentEquals(requestSection.getSport().getName()) == false){
                try {
                    // 检查sport_id。
                    planMapper.updatePlanSectionSportId(responseSection.getId(), requestSection.getSport().getId());
                } catch (Exception e){
                    System.out.println(e);
                }
            }
        }

        // 处理更新Row。
        for(int i=0; i<requestSection.getRowList().size(); i++){
            if(i < responseSection.getRowList().size()) {
                updateRow(
                        responseSection,
                        responseSection.getRowList().get(i),
                        requestSection.getRowList().get(i));
            } else {
                // 当前数据库中不存在。
                updateRow(
                        responseSection,
                        null,
                        requestSection.getRowList().get(i));
            }
        }

        // 删除多余的row。
        int difference = responseSection.getRowList().size() - requestSection.getRowList().size();
        if(difference > 0){
            for (int i = requestSection.getRowList().size() ;
                 i < responseSection.getRowList().size() ;
                 i++){
                try {
                    planMapper.deletePlanRow(responseSection.getRowList().get(i).getId());
                } catch (Exception e) {

                    System.out.println(e);
                }
            }
        }

    }

    private void updateRow(PlanSection responseSection, PlanRow currentRow, PlanRow requestRow){

        // 查看数据库中是否存在。
        PlanRow responseRow = currentRow;

        if( responseRow == null ){
            // 数据库中不存在。
            // 创建planRow。
            try {
                planMapper.insertPlanRow(
                        responseSection.getSport().getUser_id(),
                        responseSection.getPlan_id(),
                        responseSection.getId(),
                        requestRow.getSeq(),
                        requestRow.getValue(),
                        requestRow.getTimes(),
                        requestRow.getRestTime());

                responseRow = planMapper.findPlanRow(responseSection.getId(), requestRow.getSeq());
            } catch (Exception e){
                // 应该要输出错误过去客户端。
                System.out.println(e);
            }

        } else {
            // 更新planRow。
            // 对比需要修改的和已经存在的"value"是否一致，否则update。

            if ((responseRow.getValue().compareTo(requestRow.getValue()) != 0)
                    ||
                    (responseRow.getTimes() != requestRow.getTimes())
                    ||
                    (responseRow.getRestTime() != requestRow.getRestTime())
            ){
                // 不相等，则更新value数值和次数times。
                try {
                    planMapper.updatePlanRow(
                            responseRow.getId(),
                            requestRow.getValue(),
                            requestRow.getTimes(),
                            requestRow.getRestTime());
                } catch (Exception e){
                    System.out.println(e);
                }

            }

        }
    }

    private Plan parseToPlan(List<PlanSQLModel> temp) {
        Plan plan = null;
        List<PlanSection> sectionList = new ArrayList();
        List<PlanRow> rowList = new ArrayList();

        int i;
        for(i = 0; i < temp.size(); ++i) {
            PlanSQLModel p = (PlanSQLModel)temp.get(i);
            if (!p.getName().isBlank()) {
                plan = new Plan(p);
            }

            if (p.getSport_id() != 0) {
                PlanSection section = new PlanSection(p);
                section.setRowList(new ArrayList());
                section.setSport(this.sportMapper.findSportById(p.getSport_id()));
                sectionList.add(section);
            }

            if (p.getValue() != 0.0D) {
                rowList.add(new PlanRow(p));
            }
        }

        Collections.sort(sectionList);

        for(i = 0; i < rowList.size(); ++i) {
            PlanRow row = (PlanRow)rowList.get(i);
            Iterator var10 = sectionList.iterator();

            while(var10.hasNext()) {
                PlanSection section = (PlanSection)var10.next();
                if (section.getId() == row.getPlan_section_id()) {
                    section.getRowList().add(row);
                    break;
                }
            }
        }

        plan.setSectionList(sectionList);
        return plan;
    }

    public Plan getCompletePlan(int id){
        List<PlanSQLModel> sqlModels = planMapper.findPlanByPlanId(id);
        Plan result = parseToPlan(sqlModels);
        return result;
    }

    public List<Plan> getAllPlansComplete(int user_id){

//        List<Plan> plans = planMapper.findAllPlanWithUserId(user_id);

        List<Integer> id_list = planMapper.findAllPlanId(user_id);

        List<Plan> completePlans = new ArrayList<Plan>();

        for (int plan_id:
                id_list) {
            List<PlanSQLModel> planSQLModels =  planMapper.findPlanByPlanId(plan_id);

            Plan planModel = parseToPlan(planSQLModels);
            completePlans.add(planModel);
        }

        return completePlans;

    }

}
