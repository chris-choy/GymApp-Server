package com.chris.gym.Plan.Service;


import com.chris.gym.Plan.Entity.Plan;
import com.chris.gym.Plan.Entity.PlanRow;
import com.chris.gym.Plan.Entity.PlanSQLModel;
import com.chris.gym.Plan.Entity.PlanSection;
import com.chris.gym.Plan.Mapper.PlanMapper;
import com.chris.gym.Sport.Sport;
import com.chris.gym.Sport.SportMapper;
import com.chris.gym.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class PlanService {

    @Autowired
    PlanMapper planMapper;

    @Autowired
    SportMapper sportMapper;

    public void tTransaction() throws Exception{
        sportMapper.insert("tt2", "unit", 4);
        throw new Exception("failed");
    }

    /**
     *
     * @param user_id
     * @param requestPlan：需要更新的Plan模型对象。
     * @throws Exception
     */
    public Plan updatePlan(int user_id, Plan requestPlan) throws Exception {

        // 1. 获取Plan。
        // 检查Plan是否存在，存在则update，不存在则insert。
        Plan responsePlan = null;

        // 1.1 id不等于0，则数据库中存在。
        if (requestPlan.getId() != 0){

            // 1.1.1 先尝试获取服务器上的Plan内容。
            responsePlan = getPlan(requestPlan.getId());

            // 1.1.2 修改Plan信息。
            if(responsePlan.getName().contentEquals(requestPlan.getName()) == false){
                // 需要修改的plan和数据库中的plan名称不一致。
                // 故update修改，否则不作任何改动。
                try {
                    planMapper.updatePlan(responsePlan.getId(), requestPlan.getName());
                } catch (Exception e){
                    throw new Exception("Error in modifying the plan's name: "+ e);
                }
            }

        } else {
            // 1.2 创建新的plan。
            try {
                planMapper.insertPlan(requestPlan.getName(),user_id, requestPlan.getSeq());
                responsePlan = planMapper.findPlanWithUserIdAndSeq(user_id, requestPlan.getSeq());
            } catch (Exception e){
                throw new Exception("Error: Failed to create Plan");
            }
        }



        // 2. 更新PlanSection。
        try {
            updateSections(
                    user_id,
                    responsePlan.getId(),
                    responsePlan.getSectionList(),
                    requestPlan.getSectionList());
        }catch (Exception e){
            throw new Exception("Error in updateSections: " + e);
        }

        // 3. Return the result.
        Plan result = getPlan(responsePlan.getId());
        return result;
    }

    private Plan getPlan(int id){

        // 因为从数据库获取的数据不是按照Plan-PlanSection-PlanRow结构分层，
        // 所以借助PlanSQLModel，将数据按照以上层级构建Plan数据实体。
        List<PlanSQLModel> sqlModels = planMapper.findPlanByPlanId(id);

        if (sqlModels != null) {
            return parseToPlan(sqlModels);
        } else {
            return null;
        }
    }

    public void deletePlan(int plan_id, int seq){

        // 1. 删除指定的Plan。
        planMapper.deletePlan(plan_id);

        // 2. 调整删除的计划后面的计划的seq值。
        planMapper.decreasePlanSeq(seq);

    }


    /**
     *
     * @param user_id: 用户user_id。
     * @param plan_id：PlanSection所属的plan_id。
     * @param currentSectionArray: 当前数据库中存在的PlanSection对象数组。
     * @param requestSectionArray: 需要构建的PlanSection目标结果对象数组。
     * @return
     * @throws Exception
     */
    private void updateSections(int user_id,
                                       int plan_id,
                                       List<PlanSection> currentSectionArray,
                                       List<PlanSection> requestSectionArray) throws Exception{

        int currentCount = currentCount = currentSectionArray.size();
        int requestCount = requestSectionArray.size();

        PlanSection[] newSectionA = new PlanSection[requestCount];
        // 1. 遍历当前的PlanSection数组（currentSection），并对当前存在的PlanSection对象进行修改。
        for (int index = 0; index<currentCount && index<requestCount; index++){
            // 1.1 对现PlanSection进行修改。

            if (!requestSectionArray.get(index).getSport().getName().equals(
                    currentSectionArray.get(index).getSport().getName())) {
                // 1.1.1 如果PlanSection对应的运动不一样，则修改。
                planMapper.updatePlanSectionSportId(
                        currentSectionArray.get(index).getId(),
                        currentSectionArray.get(index).getSport().getId());
            }

            // 1.1.2 对PlanSection中的PlanRow进行修改。
            updateRows(user_id,
                    plan_id,
                    currentSectionArray.get(index).getId(),
                    currentSectionArray.get(index).getRowList(),
                    requestSectionArray.get(index).getRowList()
                    );

        }

        // 2. 增加PlanSection.
        for(int index = currentCount; index < requestCount; index++){

            // 2.1 获取对应的sport_id。
            Sport sport = sportMapper.findSportByName(
                    user_id,
                    requestSectionArray.get(index).getSport().getName());


            // 2.2 创建PlanSection。
            planMapper.insertPlanSection(
                    user_id,
                    plan_id,
                    index+1,
                    sport.getId());

            PlanSection responsePlanSection = planMapper.findPlanSection(plan_id, index+1);

            // 2.3 插入PlanRows到新建的PlanSection中。
            updateRows(
                    user_id,
                    plan_id,
                    responsePlanSection.getId(),
                    responsePlanSection.getRowList(),
                    requestSectionArray.get(index).getRowList()
                    );

        }

        // 3. 删除多余的PlanSection.
        for(int index = requestCount; index < currentCount; index++){
            planMapper.deletePlanSection(currentSectionArray.get(index).getId());
        }

    }


    /**
     *
     * @param user_id: 用户user_id。
     * @param plan_id：PlanRow所属的plan_id。
     * @param plan_section_id：PlanRow所属的plan_section_id。
     * @param currentRowsArray: 当前数据库中存在的PlanRow对象数组。
     * @param requestRowsArray: 需要构建的PlanRow目标结果对象数组。
     */
    private void updateRows(
            int user_id,
            int plan_id,
            int plan_section_id,
            List<PlanRow> currentRowsArray,
            List<PlanRow> requestRowsArray) {

        int currentCount = currentRowsArray.size();
        int requestCount = requestRowsArray.size();

        // 1. 遍历当前的PlanRow数组（currentRow），并对当前存在的PlanRow对象进行修改。
        for (int index = 0; index < currentCount && index < requestCount; index++) {
            // 1.1 对现PlanRow进行修改。
            PlanRow current = currentRowsArray.get(index);
            PlanRow request = requestRowsArray.get(index);

            if (current.getValue() != (request.getValue()) ||
                    current.getTimes() != request.getTimes() ||
                    current.getValue() != request.getRestTime()) {

                // 1.1.1 如果PlanRow对应的value，times，restTime不一样，则修改。
                planMapper.updatePlanRow(
                        currentRowsArray.get(index).getId(),
                        request.getValue(),
                        request.getTimes(),
                        request.getRestTime());

            }
        }

        // 2. 增加PlanRow.
        for(int index = currentCount; index < requestCount; index++){
            planMapper.insertPlanRow(
                    user_id,
                    plan_id,
                    plan_section_id,
                    index+1,
                    requestRowsArray.get(index).getValue(),
                    requestRowsArray.get(index).getTimes(),
                    requestRowsArray.get(index).getRestTime());
        }

        // 3. 删除PlanRow.
        for(int index = requestCount; index < currentCount; index++){
            planMapper.deletePlanRow(currentRowsArray.get(index).getId());
        }
    }

    /**
     * 将PlanSQLModel转化成Plan对象。
     * @param temp：PlanMapper从数据库获取到的PlanSQLModel对象数组。
     * @return：解析出的Plan对象。
     */
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
