package com.laacompany.travelplanner.Storage;

public class RegisterData {
    public String uname,pass,pnumb,gender,bdate;

    public RegisterData() {

    }

    public RegisterData(String uname, String pass, String pnumb, String gender, String bdate) {
        this.uname = uname;
        this.pass = pass;
        this.pnumb = pnumb;
        this.gender = gender;
        this.bdate = bdate;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPnumb() {
        return pnumb;
    }

    public void setPnumb(String pnumb) {
        this.pnumb = pnumb;
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
