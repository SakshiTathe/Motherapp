package com.example.motherapp;

public class Cust_Measure_model {

    int id;
    String parts;
    float meaures;

    public Cust_Measure_model(int id, String parts, float meaures) {
        this.id = id;
        this.parts = parts;
        this.meaures = meaures;
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getParts() {return parts;}

    public void setParts(String parts) {this.parts = parts;}

    public float getMeaures() {return meaures;}

    public void setMeaures(float meaures) {this.meaures = meaures;}
}
