package com.avl.ahendriver;

public class UserDSHelper {
    String name, email, phone, address, dsName;

    public UserDSHelper() {

    }

    public UserDSHelper(String name, String email, String phone, String address, String dsName) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.dsName = dsName;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String password) {
        this.address = password;
    }

    public String getDsName() { return dsName; }
    public void setDsName(String dsName) { this.dsName = dsName; }

}
