package com.example.student.arielexpress;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

/**
 * Created by Student on 19/12/2018.
 */

public class Items extends AppCompatActivity implements View.OnClickListener {
ImageView image;
int price;
String desc;
String PATH;



    StorageReference storage = FirebaseStorage.getInstance().getReference();
    DatabaseReference database = FirebaseDatabase.getInstance().getReference("items");



    public void goToShoppingCart(View view){
        Intent intent = new Intent(this, ShoppingCart.class);
        startActivity(intent);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.items);
        int category=getIntent().getIntExtra("category",0);
        loadDescriptionAndImage(category);

    }


    private void loadDescriptionAndImage(final int category) {
        ImageView i=(ImageView) findViewById(R.id.i1);
        StorageReference imageRef=getImageRefernce(category);
        downloadDirectImage(imageRef,i);
        final TextView d=(TextView)findViewById(R.id.d1);
        final TextView p=(TextView)findViewById(R.id.p1);

        database.addValueEventListener(new ValueEventListener() {
            int childIndex=0;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int price=0;
                String desc = null;
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if(childIndex==category){
                        for(DataSnapshot childData : data.getChildren()){

                            if(childData.getKey().toString().contains("desc")) {
                                d.setText(""+childData.getValue());
                            }
                           else {
                                p.setText(""+childData.getValue());
                            }

                        }
                    }
                    childIndex++;

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("initCategoryItems","The read failed: " + databaseError.getCode());
            }
        });

    }

    private StorageReference getImageRefernce(int category) {
        StorageReference ref = null;
        switch (category){
            case 0:
                ref=(storage.child("Products/Hoodies/white_hoodie.jpg"));
                break;
            case 1:
                ref=(storage.child("Products/Jackets/north_face_white.jpg"));
                break;
            case 2:
                ref=(storage.child("Products/Jeans/blue_jeans.jpg"));
                break;
            case 3:
                ref=(storage.child("Products/Pants/red_pants.jpg"));
                break;
            case 4:
                ref=(storage.child("Products/Shirts/boohoo.jpg"));
                break;
            case 5:
                ref=(storage.child("Products/Shorts/training_shorts.jpg"));
                break;

        }
        PATH=ref.toString();
        return ref;
    }


    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, ItemScreen.class);
      switch(view.getId()){
          case R.id.d1:
              putIntent((ImageView)findViewById(R.id.i1), ((TextView)findViewById(R.id.d1)).getText().toString(),
                      Integer.parseInt(((String)((TextView)findViewById(R.id.p1)).getText().toString()).trim())
                      ,intent);
              break;
         /* case R.id.d2:
              putIntent((ImageView)findViewById(R.id.i2), ((TextView)findViewById(R.id.d2)).getText().toString(),
                      Integer.getInteger(((TextView)findViewById(R.id.p2)).getText().toString()),intent);
              break;
          case R.id.d3:
              putIntent((ImageView)findViewById(R.id.i3), ((TextView)findViewById(R.id.d3)).getText().toString(),
                      Integer.getInteger(((TextView)findViewById(R.id.p3)).getText().toString()),intent);
              break;
          case R.id.d4:
              putIntent((ImageView)findViewById(R.id.i4), ((TextView)findViewById(R.id.d4)).getText().toString(),
                      Integer.getInteger(((TextView)findViewById(R.id.p4)).getText().toString()),intent);
              break;
          case R.id.d5:
              putIntent((ImageView)findViewById(R.id.i5), ((TextView)findViewById(R.id.d5)).getText().toString(),
                      Integer.getInteger(((TextView)findViewById(R.id.p5)).getText().toString()),intent);
              break;*/
      }
    }

    private void putIntent(ImageView i,String d,int p,Intent intent) {
        i.buildDrawingCache();
        Bitmap image= ((ImageView)findViewById(R.id.i1)).getDrawingCache();
        //TODO GET THE IMAGE DIRECTLY
        Bundle extras = new Bundle();
        extras.putParcelable("imagebitmap", image);
        intent.putExtras(extras);
        intent.putExtra("price",p);
        intent.putExtra("desc",d);
        startActivity(intent);
    }

    public void downloadDirectImage(StorageReference imageRef, ImageView imageView) {

        try {
            if (imageRef != null) {
                try {
                    Glide.with(this)
                            .load(imageRef)
                            .into(imageView);
                    Log.e("downloadDirect", "success");
                }catch (Exception ex){
                    Log.e("downloadDirect", ex.toString());
                }
            } else {
                Log.e("downloadDirect", "Null image storage reference!");
            }
        }catch (Exception ex){
            Log.e("downloadDirect", ex.toString());
        }
    }
    public String getPath(){
        return this.PATH;
    }

}
