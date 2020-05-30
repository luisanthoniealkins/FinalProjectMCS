package com.laacompany.travelplanner.Storage;

public class RegisterData {
    public String username,password,phone,gender,bdate;


    public RegisterData(String username, String password, String phone, String gender, String bdate) {
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.gender = gender;
        this.bdate = bdate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBdate() {
        return bdate;
    }

    public void setBdate(String bdate) {
        this.bdate = bdate;
    }
}
