package com.example.motherapp;

public class CustomModel {
    int id;
    String name;
    int phone;
    public CustomModel(int id, String name, int phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public int getId() {
        return id;}

    public String getName() {
        return name;}

    public int getPhone() {
        return phone;}
}
