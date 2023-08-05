package com.example.careconnect;

import java.io.Serializable;

public class Parent implements Serializable {
        private int parentId;
        private String name;
        private String email;
        private String password;
        private byte[] image;
        private String description;

        public Parent() {
            this.name = name;
            this.email = email;
            this.password = password;
            this.image = image;
            this.description = description;
        }

        public Parent(String name, String email, String password, byte[] image, String description) {
            this.name = name;
            this.email = email;
            this.password = password;
            this.image = image;
            this.description = description;
        }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
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
    }



