package com.example.sqlite;

public class User {
    int id;
    String name;
    String avatar;
    int de_id;


    public String getDepartname() {
        return departname;
    }

    public void setDepartname(String departname) {
        this.departname = departname;
    }

    String departname;



    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAvatar() {
        return avatar;
    }
    public int getDepartmentId(){ return de_id;}

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
public void setDepartmentId(int de_id) {this.de_id = de_id;}
    public User(int id, String name, String avatar, int de_id){
       this.id=id;
       this.name=name;
       this.avatar=avatar;
       this.de_id = de_id;
    }
    public User(int id, String name, String avatar, int de_id, String de_name){
        this.id=id;
        this.name=name;
        this.avatar=avatar;
        this.de_id = de_id;
        this.departname = de_name;
    }
}
