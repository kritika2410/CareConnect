package com.example.careconnect;

public class User {

    private int userId;
    private String name;
    private String email;
    private String password;
    private byte[] image;
    private String description;
    private String education;

    public User(){
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.image = image;
        this.description = description;
        this.education = education;
    }
    public User(int userId, String name, String email, String password, byte[] image, String description, String education) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.image = image;
        this.description = description;
        this.education = education;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }
}
