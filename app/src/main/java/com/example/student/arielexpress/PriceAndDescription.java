package com.example.student.arielexpress;

//this class only for the HashMap in Items.java
public class PriceAndDescription {
    int price;
    String desc;

    public PriceAndDescription( String desc,int price) {
        this.price = price;
        this.desc = desc;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
