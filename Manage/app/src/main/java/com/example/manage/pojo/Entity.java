package com.example.manage.pojo;

//每日考勤表的实体类，上班和下班共用
public class Entity {
    //第一列表头
    private String name;
    //第二列表头
    private String phone;
    //第三列表头
    private String status;

    private String time;

    private String local;

    private String apartment;

    public Entity() {
    }

    public Entity(String name, String phone, String status, String time, String local, String apartment) {
        this.name = name;
        this.phone = phone;
        this.status = status;
        this.time = time;
        this.local = local;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }
}

