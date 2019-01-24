package com.rabbitmq.producer.entity;

public class DiagnoseInfo {
    private long id;
    private String taskUid;
    private String diagnoseUid;
    private String fileUid;
    private String filePath;
    private String returnUrl;
    private String result;
    private java.sql.Timestamp createTime;
    private java.sql.Timestamp finishTime;
    private long taskStatus;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTaskUid() {
        return taskUid;
    }

    public void setTaskUid(String taskUid) {
        this.taskUid = taskUid;
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


    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }


    public java.sql.Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(java.sql.Timestamp createTime) {
        this.createTime = createTime;
    }


    public java.sql.Timestamp getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(java.sql.Timestamp finishTime) {
        this.finishTime = finishTime;
    }


    public long getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(long taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
