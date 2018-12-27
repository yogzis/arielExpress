package com.example.student.arielexpress;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<TextView> categories;
    Context context=this;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        categories=new ArrayList<>();
        categories.add((TextView) findViewById(R.id.hoodies));
        categories.add((TextView) findViewById(R.id.jackets));
        categories.add((TextView) findViewById(R.id.jeans));
        categories.add((TextView) findViewById(R.id.pants));
        categories.add((TextView) findViewById(R.id.shirts));
        categories.add((TextView) findViewById(R.id.shorts));




        for(int i=0;i<categories.size();i++){
            final int finalI = i;
            categories.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, Items.class);
                    intent.putExtra("category", finalI);
                    startActivity(intent);

                }
            });



        }
    }



   public void goToShoppingCart(View view){
       Intent intent = new Intent(this, ShoppingCart.class);
       startActivity(intent);
   }


    public void signUp(View view){
        Intent intent = new Intent(this, LogIn.class);
        startActivity(intent);
    }

    public void goToAccount(View view){
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }



}
