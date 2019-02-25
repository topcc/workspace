package com.rabbitmq.producer.mapper;

import com.rabbitmq.producer.entity.Model;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Component
public interface ModelMapper {
    @Select("SELECT * FROM biod_model WHERE model_id = #{modelId}")
    Model getModelByMId(String modelId);
}
