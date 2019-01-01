package com.example.student.arielexpress;

import com.google.firebase.storage.StorageReference;

class Item {
    private StorageReference image;
    private int price;
    private String desc;


    public Item(StorageReference image, int price, String desc) {
        this.image = image;
        this.price = price;
        this.desc = desc;
    }

    public StorageReference getImage() {
        return image;
    }

    public void setImage(StorageReference image) {
        this.image = image;
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
