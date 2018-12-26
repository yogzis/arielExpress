package com.example.student.arielexpress;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.Serializable;

/**
 * Created by Student on 19/12/2018.
 */

public class ItemScreen extends AppCompatActivity  {
    ImageView image;
    String desc;
    String size;
    String color;
    int price;
    RadioGroup radioGroup1;
    RadioGroup radioGroup2;
    RadioButton sizeButton;
    RadioButton colorButton;

    public ItemScreen(){

    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public ItemScreen( String desc, int price) {
        this.desc = desc;
        this.price = price;
    }

    public ItemScreen(ImageView image, String desc, String size, String color, int price) {
        this.image = image;
        this.desc = desc;
        this.size = size;
        this.color = color;
        this.price = price;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_screen);

        Bundle extras = getIntent().getExtras();

        Bitmap bmp = (Bitmap) extras.getParcelable("imagebitmap");
        ImageView image=new ImageView(this);
        image.setImageBitmap(bmp);

        setImage(image);
        if(this.image==null)Log.i("onCreate","no image");
        ((ImageView) findViewById(R.id.image)).setImageBitmap(bmp );
        ((TextView) findViewById(R.id.desc)).setText(getIntent().getStringExtra("desc"));
        setDesc(getIntent().getStringExtra("desc"));
        ((TextView) findViewById(R.id.price)).setText(String.valueOf(getIntent().getIntExtra("price",0)));
        setPrice(getIntent().getIntExtra("price",0));
        radioGroup2=(RadioGroup)findViewById(R.id.radioGroup2);


    }

   public void setSize(View view){
       radioGroup1=(RadioGroup)findViewById(R.id.radioGroup1);
        int radioId=radioGroup1.getCheckedRadioButtonId();
       Log.i("setSize",""+radioId);
       Log.i("setSize",""+R.id.m);
        this.sizeButton=(RadioButton) findViewById(radioId);
        setSize(""+sizeButton.getText());
   }

    public void setColor(View view){
        int radioId=radioGroup2.getCheckedRadioButtonId();
        this.colorButton=findViewById(radioId);
        setColor((String)colorButton.getText());
    }

    public void addToBag(View view){
        /*Intent intent = new Intent(this, ShoppingCart.class);
        //image
        getImage().buildDrawingCache();
        Bitmap image= findViewById(R.id.i1).getDrawingCache();
        Bundle extras = new Bundle();
        extras.putParcelable("image", image);
        intent.putExtras(extras);
        //desc
        intent.putExtra("desc",getDesc());
        //size
        intent.putExtra("size",getSize());
        //color
        intent.putExtra("color",getColor());
        //price
        intent.putExtra("price",getPrice());

        startActivity(intent);*/

        ShoppingCart.itemsInBag.add(this);

    }

}
