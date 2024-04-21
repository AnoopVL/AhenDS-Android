package com.avl.ahendriver;

public class RegisteredStudent {
    private String requestId;
    private String dsName;
    private String userName;
    private String userPhone;
    private String timeSlot;
    private String status; // pending, accepted, rejected

    public RegisteredStudent() {
        // Default constructor required for Firebase
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getDsName() {
        return dsName;
    }

    public void setDsName(String dsName) {
        this.dsName = dsName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Constructor
    public RegisteredStudent(String requestId, String dsName, String userName, String userPhone, String timeSlot, String status) {
        this.requestId = requestId;
        this.dsName = dsName;
        this.userName = userName;
        this.userPhone = userPhone;
        this.timeSlot = timeSlot;
        this.status = status;
    }


}
