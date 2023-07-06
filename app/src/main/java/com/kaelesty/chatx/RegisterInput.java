package com.kaelesty.chatx;

public class RegisterInput {
    private String name;
    private String lastname;
    private String password;
    private String email;
    private int age;

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

    public RegisterInput(String name, String lastname, String password, String email, int age) {
        this.name = name.trim();
        this.lastname = lastname.trim();
        this.password = password.trim();
        this.email = email.trim();
        this.age = age;
    }

    public boolean validate() {
        if (email.length() < 5) {
            return false;
        }
        if (name.length() == 0 || lastname.length() == 0 || password.length() == 0) {
            return false;
        }
        return true;
    }
}
