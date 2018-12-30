package com.example.student.arielexpress;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Student on 19/12/2018.
 */

public class ShoppingCart extends AppCompatActivity {
    int itemsCount;
    int total;
    public static ArrayList<ItemScreen> itemsInBag=new ArrayList<>();
    Bundle savedInstanceState=null;
    boolean created;

    public ShoppingCart(int itemsCount, int total, ItemScreen[] products) {
        this.itemsCount = itemsCount;
        this.total = total;
    }

    public ShoppingCart() {
    }

    public int getItemsCount() {
        return itemsCount;
    }

    public void setItemsCount(int itemsCount) {
        ((TextView)findViewById(R.id.items_count)).setText(""+itemsCount);
        this.itemsCount = itemsCount;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        ((TextView)findViewById(R.id.total)).setText(""+total);
        this.total = total;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        if(!created){
            super.onCreate(savedInstanceState);
            this.savedInstanceState=savedInstanceState;
            created=true;
        }
        //TODO 5
            setContentView(R.layout.shopping_cart);
            init();


    }

public void init(){
    setItemsCount(itemsInBag.size());
    int t = 0;
    for (int i = 0; i < itemsInBag.size(); i++) t += itemsInBag.get(i).getPrice();
    setTotal(t);
    for (int i = 0; i < itemsInBag.size(); i++) setItems(i);
}


    private void setItems(int row) {
        switch (row) {
            case 0:
                loadProduct(row,R.id.i1,R.id.desc1,R.id.p1,R.id.dl1);
                break;
            case 1:
                loadProduct(row,R.id.i2,R.id.desc2,R.id.p2,R.id.dl2);
                break;
            case 2:
                loadProduct(row,R.id.i3,R.id.desc3,R.id.p3,R.id.dl3);
                break;
            case 3:
                loadProduct(row,R.id.i4,R.id.desc4,R.id.p4,R.id.dl4);
                break;
            case 4:
                loadProduct(row,R.id.i5,R.id.desc5,R.id.p5,R.id.dl5);
                break;
            case 5:
                loadProduct(row,R.id.i6,R.id.desc6,R.id.p6,R.id.dl6);
                break;
            case 6:
                loadProduct(row,R.id.i7,R.id.desc7,R.id.p7,R.id.dl7);
                break;
            case 7:
                loadProduct(row,R.id.i8,R.id.desc8,R.id.p8,R.id.dl8);
                break;
            case 8:
                loadProduct(row,R.id.i9,R.id.desc9,R.id.p9,R.id.dl9);
                break;
            case 9:
                loadProduct(row,R.id.i10,R.id.desc10,R.id.p10,R.id.dl10);
                break;



        }


    }

    public void loadProduct(int row,int image,int desc,int price,int del) {
            Bitmap bmp=itemsInBag.get(row).getBmp();
            ((ImageView) findViewById(image)).setImageBitmap(bmp);

        ((TextView) findViewById(desc)).setText(itemsInBag.get(row).getDesc());
         ((TextView) findViewById(price)).setText(""+itemsInBag.get(row).getPrice());
        ((ImageView) findViewById(del)).setVisibility(View.VISIBLE);
    }

    public void deleteItem(View view){
        String id= view.getResources().getResourceName(view.getId()).replace("com.example.student.arielexpress:id/","");
        int row=Integer.parseInt(id.replace("dl","").trim())-1;
        itemsInBag.remove(row);
        onCreate(savedInstanceState);


    }

    public void checkout(View view) {
        Intent intent=new Intent(this, Checkout.class);
        intent.putExtra("total",total);
        startActivity(intent);
    }
}