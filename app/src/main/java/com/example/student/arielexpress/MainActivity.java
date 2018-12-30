package com.example.student.arielexpress;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<TextView> categories;
    Context context=this;
    static boolean isConnected;
    Button  login;
    Button  logout;
    Button  account;
    Bundle savedInstanceState;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        this.savedInstanceState=savedInstanceState;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login=(Button) findViewById(R.id.sign);
        logout=(Button) findViewById(R.id.logout);
        account=(Button) findViewById(R.id.account);
        categories=new ArrayList<>();
        categories.add((TextView) findViewById(R.id.hoodies));
        categories.add((TextView) findViewById(R.id.jackets));
        categories.add((TextView) findViewById(R.id.jeans));
        categories.add((TextView) findViewById(R.id.pants));
        categories.add((TextView) findViewById(R.id.shirts));
        categories.add((TextView) findViewById(R.id.shorts));
    }


    @Override
    protected void onStart() {
        super.onStart();
        init();
    }

    private void init() {
        if(getIntent().getBooleanExtra("isConnected",false)==true)isConnected=true;

        if(isConnected==true){
            login.setVisibility(View.INVISIBLE);
            logout.setVisibility(View.VISIBLE);
            account.setVisibility(View.VISIBLE);
        }
        else{
            login.setVisibility(View.VISIBLE);
            logout.setVisibility(View.INVISIBLE);
            account.setVisibility(View.INVISIBLE);
        }
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



    public void goToAccount(View view){
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }


    public void login(View view) {
        Intent intent = new Intent(MainActivity.this, LogIn.class);
        startActivity(intent);
    }

    public void logout(View view) {
        isConnected = false;
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
