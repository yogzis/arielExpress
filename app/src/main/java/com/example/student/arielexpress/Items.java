package com.example.student.arielexpress;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Items- displays all the items that related for each category
 */

public class Items extends AppCompatActivity implements View.OnClickListener {
ProgressDialog pDialog;
StorageReference storage = FirebaseStorage.getInstance().getReference();
DatabaseReference database = FirebaseDatabase.getInstance().getReference("items");
 static   ArrayList<Item> items=new ArrayList<Item>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.items);
        pDialog = new ProgressDialog(Items.this);
        pDialog.setMessage("loading");
        pDialog.setCancelable(false);
        pDialog.show();
        String category=getIntent().getStringExtra("category");

        linkImageToDescription(category);
        items.clear();
    }

    /**
     * adds the items and prints to the screen
     * @param category
     */
    private void linkImageToDescription(final String category) {
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int price=0;
                String desc = null;
                StorageReference imageRef = null;
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if(data.getKey().contains(category)){
                        for(DataSnapshot childData : data.getChildren()){
                            if(childData.getKey().toString().contains("desc")) {
                             desc=childData.getValue().toString();
                            }
                            else if(childData.getKey().toString().contains("location")){
                              imageRef=storage.child(childData.getValue().toString());
                            }
                            else if(childData.getKey().toString().contains("price")){
                               price=Integer.parseInt(childData.getValue().toString().trim());
                            }
                        }
                        items.add(new Item(imageRef,price,desc));
                    }

                }
                pDialog.dismiss();
                setItemsToScreen(items);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("initCategoryItems","The read failed: " + databaseError.getCode());
            }

        });
    }





    private void setItemsToScreen(ArrayList<Item> items) {
        for(int j=0;j<items.size();j++){
            Item item=items.get(j);
            ImageView i=getImageFromIndex(j);
            TextView p=getPriceFromIndex(j);
            TextView d=getDescriptionFromIndex(j);
            setViews(item,i,p,d);
        }
    }

    private void setViews(Item item, ImageView image, TextView price, TextView description) {
       price.setText(""+item.getPrice());
       description.setText(item.getDesc());
        downloadDirectImage(item.getImage(),image);
    }

    private TextView getDescriptionFromIndex(int j) {
        String description="d"+(j+1);
        int id = getResources().getIdentifier(description, "id", getPackageName());
        return (TextView) findViewById(id);
    }

    private TextView getPriceFromIndex(int j) {
        String price="p"+(j+1);
        int id = getResources().getIdentifier(price, "id", getPackageName());
        return (TextView) findViewById(id);
    }

    private ImageView getImageFromIndex(int j) {
        String image="i"+(j+1);
        int id = getResources().getIdentifier(image, "id", getPackageName());
        return (ImageView) findViewById(id);
    }




    public void goToShoppingCart(View view){
        Intent intent = new Intent(this, ShoppingCart.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        String id= view.getResources().getResourceName(view.getId()).replace("com.example.student.arielexpress:id/","");
        int index=Integer.parseInt(id.replace("d","").trim())-1;

        ImageView image=getImageFromIndex(index);
        String desc=getDescriptionFromIndex(index).getText().toString();
        int price=Integer.parseInt(getPriceFromIndex(index).getText().toString().trim());
        Intent intent = new Intent(this, ItemScreen.class);
        putIntent(index,image,desc,price,intent);
    }

    private void putIntent(int index,ImageView i,String d,int p,Intent intent) {
        i.buildDrawingCache();
        Bitmap image= (getImageFromIndex(index)).getDrawingCache();
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


}
