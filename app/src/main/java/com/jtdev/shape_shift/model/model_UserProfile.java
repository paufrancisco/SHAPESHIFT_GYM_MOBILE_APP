package com.jtdev.shape_shift.model;

public class model_UserProfile {
    String email;
    String contact;
    String name;

    public model_UserProfile() {
    }

    //UserProfile userProfile = new UserProfile(name, phone, email);
    public model_UserProfile(String name, String contact, String email) {
        this.email = email;
        this.contact = contact;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
