package com.smartwallet.model;

import java.util.UUID;

public class User {
    public String userId;
    public String name;
    public String email;
    public String password;
    public String status;

    public User(String name, String email, String password){
        this.userId= UUID.randomUUID().toString();
        this.name=name;
        this.email=email;
        this.password=password;
        this.status="REGISTERED";
    }

    public String getUserId(){
        return userId;
    }
    public String getEmail(){
        return email;
    }
    public String getPassword(){
        return password;
    }
    public String getStatus(){
        return status;
    }
}
