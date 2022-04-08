package com.example.manage.pojo;


//查询往期考勤记录用的实体类
public class AllRecord {

    private String name;
    private String phone;
    private String startstatus;
    private String endstatus;
    private String starttime;
    private String startlocal;
    private String endtime;
    private String endlocal;
    private String apartment;

    public AllRecord() {
    }

    public AllRecord(String name, String phone, String startstatus, String endstatus, String starttime, String startlocal, String endtime, String endlocal, String apartment) {
        this.name = name;
        this.phone = phone;
        this.startstatus = startstatus;
        this.endstatus = endstatus;
        this.starttime = starttime;
        this.startlocal = startlocal;
        this.endtime = endtime;
        this.endlocal = endlocal;
        this.apartment = apartment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStartstatus() {
        return startstatus;
    }

    public void setStartstatus(String startstatus) {
        this.startstatus = startstatus;
    }

    public String getEndstatus() {
        return endstatus;
    }

    public void setEndstatus(String endstatus) {
        this.endstatus = endstatus;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getStartlocal() {
        return startlocal;
    }

    public void setStartlocal(String startlocal) {
        this.startlocal = startlocal;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getEndlocal() {
        return endlocal;
    }

    public void setEndlocal(String endlocal) {
        this.endlocal = endlocal;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }
}
