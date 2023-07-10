package com.kaelesty.chatx;

import java.io.Serializable;

public class User implements Serializable {

    private String id;
    private String name;
    private String lastname;
    private int age;
    private boolean online;

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
        return online;
    }

    public User(String id, String name, String lastname, int age, boolean isOnline) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.age = age;
        this.online = isOnline;
    }

    public User() {
    }

    public void setId(String id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", age=" + age +
                ", isOnline=" + online +
                '}';
    }

    public String toTitle() {
        return name + " " + lastname + ", " + String.valueOf(age);
    }
}
