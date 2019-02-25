package com.rabbitmq.producer.entity;

public class Model {

    private long id;
    private String modelId;
    private String modelPath;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }


    public String getModelPath() {
        return modelPath;
    }

    public void setModelPath(String modelPath) {
        this.modelPath = modelPath;
    }
}
