package com.example.careconnect;

import java.util.Calendar;

public class UserContactUs {
    private int contactId;
    private String name;
    private String email;
    private String phone;
    private String message;

    public UserContactUs(){
        this.contactId = contactId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.message = message;
    }
    public UserContactUs(int contactId, String name, String email, String phone, String message) {
        this.contactId = contactId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.message = message;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
