package com.example.motherapp;

public class BillModel {
    int id;
    String description;
    byte[] photo;
    int price;
    String date;
    String redate;
    int recprice;


    public BillModel(int id, String description, byte[] photo, int price, String date, String redate, int recprice) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.photo = photo;
        this.date = date;
        this.redate = redate;
        this.recprice = recprice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRedate() {
        return redate;
    }

    public void setRedate(String redate) {
        this.redate = redate;
    }

    public int getRecprice() {
        return recprice;
    }

    public void setRecprice(int recprice) {
        this.recprice = recprice;
    }
}
