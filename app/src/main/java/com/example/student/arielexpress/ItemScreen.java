package com.example.student.arielexpress;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


public class ItemScreen extends AppCompatActivity  {
    Bitmap bmp;
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

    public ItemScreen(Bitmap bmp, String desc, String size, String color, int price) {
        this.bmp = bmp;
        this.desc = desc;
        this.size = size;
        this.color = color;
        this.price = price;
    }

    public Bitmap getBmp() {
        return bmp;
    }

    public void setBmp(Bitmap bmp) {
        this.bmp = bmp;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_screen);

        Bundle extras = getIntent().getExtras();

        Bitmap bmp = (Bitmap) extras.getParcelable("imagebitmap");
        if(bmp==null) Log.e("loadProduct","bmp null in item screen");
        this.bmp=bmp;
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
        ShoppingCart.itemsInBag.add(this);

    }

}
