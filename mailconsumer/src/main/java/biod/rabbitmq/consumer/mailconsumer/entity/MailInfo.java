package biod.rabbitmq.consumer.mailconsumer.entity;

public class MailInfo {
    private long id;
    private String mailUid;
    private String mailAddress;
    private String mailTitle;
    private String mailBody;
    private String returnUrl;
    private java.sql.Timestamp createTime;
    private java.sql.Timestamp sendTime;
    private long mailStatus;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getMailUid() {
        return mailUid;
    }

    public void setMailUid(String mailUid) {
        this.mailUid = mailUid;
    }


    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }


    public String getMailTitle() {
        return mailTitle;
    }

    public void setMailTitle(String mailTitle) {
        this.mailTitle = mailTitle;
    }


    public String getMailBody() {
        return mailBody;
    }

    public void setMailBody(String mailBody) {
        this.mailBody = mailBody;
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


    public java.sql.Timestamp getSendTime() {
        return sendTime;
    }

    public void setSendTime(java.sql.Timestamp sendTime) {
        this.sendTime = sendTime;
    }


    public long getMailStatus() {
        return mailStatus;
    }

    public void setMailStatus(long mailStatus) {
        this.mailStatus = mailStatus;
    }

}
