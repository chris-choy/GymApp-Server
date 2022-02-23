package com.chris.gym.Record.Service;

import com.chris.gym.Plan.Entity.Plan;
import com.chris.gym.Plan.Entity.PlanRow;
import com.chris.gym.Plan.Entity.PlanSQLModel;
import com.chris.gym.Plan.Entity.PlanSection;
import com.chris.gym.Record.Entity.Record;
import com.chris.gym.Record.Entity.RecordRow;
import com.chris.gym.Record.Entity.RecordSQLModel;
import com.chris.gym.Record.Entity.RecordSection;
import com.chris.gym.Record.Mapper.RecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class RecordService {

    @Autowired
    RecordMapper recordMapper;

    public List<Record> getAll(int user_id){

        List<RecordSQLModel> result = recordMapper.getAllRecords(user_id);

        // 1. 所有Record分组。
        List<List<RecordSQLModel>> sqlModels = new ArrayList<>();
        List<Integer> indexOfId = new ArrayList<>();

        for (RecordSQLModel s :
                result) {
            if(s.getRecord_id() == 0 && s.getRecord_section_id() == 0){
                indexOfId.add(s.getId());
                List<RecordSQLModel> arr = new ArrayList<>();
                arr.add(s);
                sqlModels.add(arr);
            } else {
                int index = indexOfId.indexOf(s.getRecord_id());
                sqlModels.get(index).add(s);
            }
        }

        // 2. 逐个解析成Record对象。
        List<Record> records = new ArrayList<>();

        for (List<RecordSQLModel> s :
                sqlModels) {
            records.add(parseToRecord(s));
        }

        return records;
    }

    public Record get(int id){

        List<RecordSQLModel> sqlModels = recordMapper.getRecord(id);

        Record record = parseToRecord(sqlModels);

        return record;
    }


    private Record parseToRecord(List<RecordSQLModel> temp) {
        Record record = null;
        List<RecordSection> sectionList = new ArrayList();
        List<RecordRow> rowList = new ArrayList();

        int i;
        for(i = 0; i < temp.size(); ++i) {
            RecordSQLModel p = (RecordSQLModel)temp.get(i);

            // 1. 判断为Record.
            if (p.getRecord_id() == 0 && p.getRecord_section_id() == 0) {
                record = new Record(p);
            }


            // 2. 判断为RecordSection.
            if (p.getRecord_id() != 0 && p.getRecord_section_id() == 0) {
                RecordSection section = new RecordSection(p);
                sectionList.add(section);
            }

            // 3. 判断为RecordRow.
            if (p.getRecord_id() != 0 && p.getRecord_section_id() != 0) {
                rowList.add(new RecordRow(p));
            }
        }

        // 4. 将RecordRow放进对应的RecordSection的rowList中。
        for(i = 0; i < rowList.size(); ++i) {
            RecordRow row = (RecordRow)rowList.get(i);
            Iterator var10 = sectionList.iterator();

            while(var10.hasNext()) {
                RecordSection section = (RecordSection)var10.next();
                if (section.getId() == row.getRecord_section_id()) {
                    section.getRowList().add(row);
                    break;
                }
            }
        }

        record.setRecordSectionList(sectionList);

        return record;
    }

    public Record create( Record record, int user_id){

        // 1. 创建Record.
        recordMapper.createRecord(record, user_id);

        // 2. 创建RecordSection.
        List<RecordSection> sectionList = new ArrayList<>();
        for (RecordSection section:
             record.getRecordSectionList()) {

            recordMapper.createRecordSection(
                    user_id,
                    record.getId(),
                    section);

//            // 3. 创建RecordRow.
            List<RecordRow> rowList = new ArrayList<>();
            for (RecordRow row :
                    section.getRowList()) {

                recordMapper.createRecordRow(user_id,
                        record.getId(),
                        section.getId(),
                        row);

                rowList.add(row);
            }
            section.setRowList(rowList);

            sectionList.add(section);
        }


        record.setRecordSectionList(sectionList);

        return record;
    }



}
