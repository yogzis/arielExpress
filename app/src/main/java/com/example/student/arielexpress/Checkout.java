package com.example.student.arielexpress;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.annotation.Target;

/**
 * displays checkout and order confirmation
 */

public class Checkout extends AppCompatActivity{

    TextView total;
    EditText adress;
    EditText name;
    EditText phone;
    EditText email;
    EditText credit;
    Button   placeOrder;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        setFields(MainActivity.isConnected);


    }

    private void setFields(boolean isConnected) {
        total.setText(""+getIntent().getIntExtra("total",0));
        if(isConnected) {
            firebaseAuth = FirebaseAuth.getInstance();
            firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                    name.setText(userProfile.getUserName());
                    adress.setText(userProfile.getUserAdress());
                    email.setText(userProfile.getUserEmail());
                    phone.setText(userProfile.getUserPhone());
                    credit.setText(userProfile.getUserCredit());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }
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
        if(fieldsIsValid()){
            Toast.makeText(this,"complete all the details correctly!",Toast.LENGTH_LONG).show();
        }
        else{
            setNotification();
            setAlertDialog();
            ShoppingCart.itemsInBag.clear();
        }

    }

    private void setNotification() {
        String message="An email has been sent to "+email.getText().toString()+" with your order details ";
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel notificationChannel = null;
            notificationChannel = new NotificationChannel("default", "test", NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(notificationChannel);

            Intent intent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

            Notification.Builder builder = new Notification.Builder(this, "default")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Order Confirmed")
                    .setContentText("")
                    .setStyle(new Notification.BigTextStyle().bigText(message).setBigContentTitle("Order Confirmed"))
                    .setContentIntent(pendingIntent);
            manager.notify(0, builder.build());
        }
        else{
            NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Order Confirmed")
                    .setAutoCancel(true)
                    .setContentText(message);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(1, builder.build()
            );
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
