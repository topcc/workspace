package com.rabbitmq.producer.entity;

import java.sql.Timestamp;

public class DiagnoseInfo {
    private long id;
    private String diagnoseUid;
    private String fileUid;
    private String filePath;
    private String userMail;
    private String returnUrl;
    private java.sql.Timestamp createTime;
    private java.sql.Timestamp finishTime;
    private long taskStatus;

    public DiagnoseInfo(String diagnoseUid, String fileUid, String filePath, String userMail, String returnUrl){
        this.diagnoseUid = diagnoseUid;
        this.fileUid = fileUid;
        this.filePath = filePath;
        this.userMail = userMail;
        this.returnUrl = returnUrl;
        this.taskStatus = 0;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDiagnoseUid() {
        return diagnoseUid;
    }

    public void setDiagnoseUid(String diagnoseUid) {
        this.diagnoseUid = diagnoseUid;
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

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
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
