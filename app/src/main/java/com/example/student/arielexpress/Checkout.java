package com.example.student.arielexpress;

import android.support.v7.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Student on 19/12/2018.
 */

public class Checkout extends AppCompatActivity{

    TextView total;
    EditText adress;
    EditText name;
    EditText phone;
    EditText email;
    EditText credit;
    Button   placeOrder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();


    }

    private void init() {
        setContentView(R.layout.checkout);
        total=(TextView)findViewById(R.id.total);
        adress=(EditText)findViewById(R.id.adress);
        name=(EditText)findViewById(R.id.name);
        phone=(EditText)findViewById(R.id.phone);
        email=(EditText)findViewById(R.id.email);
        credit=(EditText)findViewById(R.id.credit);
        placeOrder=(Button)findViewById(R.id.place_order);
    }

    public void placeOrder(View view) {
        if(!fieldsIsValid()){
            Toast.makeText(this,"complete all the details correctly!",Toast.LENGTH_LONG).show();
        }
        else{
            setAlertDialog();
        }

    }

    private void setAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("this about your shopping");
        builder.setMessage("thank you! see you next time :)");
        builder.setPositiveButton("I have enjoyed this shopping", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent=new Intent(Checkout.this, MainActivity.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("not", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private boolean fieldsIsValid() {
        return adress.getText().toString().equals("")||name.getText().toString().equals("")||phone.getText().toString().equals("")||
                email.getText().toString().equals("")||credit.getText().toString().equals("");
    }
}
