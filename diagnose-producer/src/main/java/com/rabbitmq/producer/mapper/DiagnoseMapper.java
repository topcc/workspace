package com.rabbitmq.producer.mapper;

import com.rabbitmq.producer.entity.DiagnoseInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Component
public interface DiagnoseMapper {
    @Select("SELECT * FROM biod_diagnose WHERE id = #{id}")
    DiagnoseInfo getDiagnoseInfo(int id);
    //String diagnoseUid, String fileUid, String filePath, String userMail, String returnUrl

    @Insert("INSERT INTO biod_diagnose(task_uid, diagnose_uid, file_uid, file_path, user_mail, return_url, task_status) " +
            "VALUES(#{taskUid}, #{diagnoseUid}, #{fileUid}, #{filePath}, #{userMail}, #{returnUrl}, #{taskStatus})")
    int insert(DiagnoseInfo diagnoseInfo);
}
