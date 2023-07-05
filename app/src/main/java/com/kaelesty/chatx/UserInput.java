package com.kaelesty.chatx;

public class UserInput {
    private String email;
    private String password;

    public UserInput(String email, String password) {
        this.email = email.trim();
        this.password = password.trim();
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean validate() {
        if (email.length() < 5) {
            return false;
        }
        if (password.length() == 0) {
            return false;
        }
        return true;
    }
}
