package com.example.student.arielexpress;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * displays the shopping cart activity
 */

public class ShoppingCart extends AppCompatActivity {
    int itemsCount;
    int total;
    public static ArrayList<ItemScreen> itemsInBag=new ArrayList<>();
    Bundle savedInstanceState=null;
    boolean created;

    public ShoppingCart(int itemsCount, int total) {
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
        String image="i"+(row+1);
        String desc="desc"+(row+1);
        String price="p"+(row+1);
        String delete="dl"+(row+1);
        int resIDImage = getResources().getIdentifier(image, "id", getPackageName());
        int resIDDesc = getResources().getIdentifier(desc, "id", getPackageName());
        int resIDPrice = getResources().getIdentifier(price, "id", getPackageName());
        int resIDDelete = getResources().getIdentifier(delete, "id", getPackageName());
        loadProduct(row,resIDImage,resIDDesc,resIDPrice,resIDDelete);
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
        final int row=Integer.parseInt(id.replace("dl","").trim())-1;
        final ItemScreen deltedItem=itemsInBag.get(row);
        itemsInBag.remove(row);
        onCreate(savedInstanceState);
        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.root), "item is deleted", Snackbar.LENGTH_LONG)
                .setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       itemsInBag.add(row,deltedItem);
                        onCreate(savedInstanceState);
                        Snackbar.make(view, "item is restored!", Snackbar.LENGTH_LONG).show();
                    }
                });

        snackbar.show();
    }

    public void checkout(View view) {
        Intent intent=new Intent(this, Checkout.class);
        if(total>0){
            intent.putExtra("total",total);
            startActivity(intent);
        }
       else{
            Toast.makeText(this,"There is no itens in the bag!",Toast.LENGTH_LONG).show();
        }
    }
}