package com.rabbitmq.producer.entity;

import java.sql.Timestamp;
import java.util.Date;

public class WaveInfo {
    private long id;
    private String ecgUid;
    private String fileUid;
    private String filePath;
    private String returnUrl;
    private java.sql.Timestamp createTime;
    private java.sql.Timestamp finishTime;
    private long taskStatus;

    public WaveInfo(String ecgUid, String fileUid, String filePath, String returnUrl){
        this.ecgUid = ecgUid;
        this.fileUid = fileUid;
        this.filePath = filePath;
        this.returnUrl = returnUrl;
        this.taskStatus = 0;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEcgUid() {
        return ecgUid;
    }

    public void setEcgUid(String ecgUid) {
        this.ecgUid = ecgUid;
    }

    public String getFileUid() {
        return fileUid;
    }

    public void setFileUid(String fileUid) {
        this.fileUid = fileUid;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Timestamp finishTime) {
        this.finishTime = finishTime;
    }

    public long getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(long taskStatus) {
        this.taskStatus = taskStatus;
    }
}
