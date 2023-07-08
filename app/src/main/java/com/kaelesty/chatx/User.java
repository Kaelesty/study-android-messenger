package com.kaelesty.chatx;

public class User {

    private String id;
    private String name;
    private String lastname;
    private int age;
    private boolean isOnline;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public int getAge() {
        return age;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public User(String id, String name, String lastname, int age, boolean isOnline) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.age = age;
        this.isOnline = isOnline;
    }
}
