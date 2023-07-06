package com.kaelesty.chatx;

public class EmailInput {

    private String email;

    public EmailInput(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public Boolean validate() {
        if (email.length() < 5) {
            return false;
        }

        return true;
    }
}
