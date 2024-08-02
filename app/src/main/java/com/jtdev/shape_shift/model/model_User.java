package com.jtdev.shape_shift.model;

public class model_User {

    private String program;
    private String name;
    private String contact;
    private String email;
    private String password;
    private String gender;
    private int age;
    private float height;
    private float weight;
    private float bmi;

    public model_User() {

    }

    //UserModel userModel = new UserModel(name, phone, email);


    public model_User(String name, String contact, String email) {
        this.name = name;
        this.contact = contact;
        this.email = email;
    }


    public model_User(float weight, float height) {
        this.height = height;
        this.weight = weight;
    }

    public model_User(String gender, int age, float height, float weight, float bmi, String program) {
        this.gender = gender;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.bmi = bmi;
        this.program = program;
    }

    public model_User(String name, String contact, String email, String password, String gender, int age, int height, int weight, float bmi, String program) {
        this.name = name;
        this.contact = contact;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.bmi = bmi;
        this.program = program;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public float getBmi() {
        return bmi;
    }

    public void setBmi(float bmi) {
        this.bmi = bmi;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }
}
