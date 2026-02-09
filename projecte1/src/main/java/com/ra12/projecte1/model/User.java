package com.ra12.projecte1.model;


public class User {
    private long id;
    private String username;
    private String password;
    private long sparks;

    public User(){}
    
    public User(String username, String password, long sparks) {
        this.username = username;
        this.password = password;
        this.sparks = sparks;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
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
    public long getSparks() {
        return sparks;
    }
    public void setSparks(long sparks) {
        this.sparks = sparks;
    }
    
}
