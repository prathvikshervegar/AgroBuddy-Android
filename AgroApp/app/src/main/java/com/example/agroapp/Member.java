package com.example.agroapp;

public class Member {

    private String name;
    private String email;
    private String usertype;

    public Member(String name,String email,String usertype){
        this.name=name;
        this.email=email;
        this.usertype=usertype;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getUsertype() {
        return usertype;
    }
}
