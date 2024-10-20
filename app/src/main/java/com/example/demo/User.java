package com.example.demo;

public class User {
    String email;
    String password;
    public User(String email, String password)
    {
        this.email = email;
        this.password = password;
    }
    public String getEmail()
    {
        return this.email;
    }
    public String getPassword(){
        return this.password;
    }
}
