package com.rabbitmq.consumer.mapper;

import com.rabbitmq.consumer.entity.DiagnoseInfo;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

@Component
public interface DiagnoseMapper {
    @Select("SELECT * FROM biod_diagnose WHERE task_uid = #{taskUid}")
    DiagnoseInfo getDiagnoseInfo(String taskUid);

    @Update("UPDATE biod_diagnose SET result = #{result} finish_time = #{finishTime}, task_status = #{taskStatus} WHERE id =#{id}")
    void update(DiagnoseInfo diagnoseInfo);
}
