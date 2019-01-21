package com.rabbitmq.producer.mapper;

import com.rabbitmq.producer.entity.MailInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Component
public interface MailMapper {
    @Select("SELECT * FROM biod_mail WHERE id = #{id}")
    MailInfo getMailInfo(int id);

    @Insert("INSERT INTO biod_mail(mail_uid, mail_address, mail_title, mail_body, return_url, mail_status) " +
            "VALUES(#{mailUid}, #{mailAddress}, #{mailTitle}, #{mailBody}, #{returnUrl}, #{mailStatus})")
    int insert(MailInfo mailInfo);
}
