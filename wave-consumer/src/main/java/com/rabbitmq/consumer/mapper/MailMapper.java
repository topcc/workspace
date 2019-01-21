package com.rabbitmq.consumer.mapper;

import com.rabbitmq.consumer.common.util.MailHelper;
import com.rabbitmq.consumer.entity.MailInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

@Component
public interface MailMapper {
    @Select("SELECT * FROM biod_mail WHERE mail_uid = #{mailUid}")
    MailInfo getMailInfo(String mailUid);

    @Update("UPDATE biod_mail SET send_time = #{sendTime}, mail_status = #{mailStatus} WHERE id =#{id}")
    void update(MailInfo mailInfo);
}
