package com.example.manage.pojo;

import android.app.Application;

public class User extends Application {
    private int id;
    //创建 SingleObject 的一个对象


    //让构造函数为 private，这样该类就不会被实例化
    public User(){}

    //获取唯一可用的对象

    private  String part;
    private  String username;
    private  String password;
    private  String sex;
    private  String shuxing;
    private  String position;
    private  String phone;

    public String getPart() { return part; }
    public void setPart(String part) { this.part = part; }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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
    public String getSex() { return sex; }
    public void setSex(String sex) { this.sex = sex; }
    public String getShuxing() { return shuxing; }
    public void setShuxing(String shuxing) { this.shuxing = shuxing; }
    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
//    @Override
//    public String toString() {
//        return "User{" +
//                "id=" + id +
//                ", username='" + username + '\'' +
//                ", password='" + password + '\'' +
//                '}';
//    }
}
