package com.rabbitmq.producer.mapper;

import com.rabbitmq.producer.entity.WaveInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Component
public interface MailMapper {
    @Insert("INSERT INTO biod_wave(ecg_uid, file_uid, file_path , return_url, task_status) " +
            "VALUES(#{ecgUid}, #{fileUid}, #{filePath}, #{returnUrl}, #{takStatus})")
    int insert(WaveInfo waveInfo);
}
