package com.example.student.arielexpress;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Login Screen
 */
public class LogIn extends AppCompatActivity {

    private EditText Name;
    private EditText Password;

    private ProgressDialog progressDialog;
    private Button Login;
    private TextView userRegistration;
    private FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);
        init();
    }


    private void init() {
        Name = (EditText)findViewById(R.id.etName);
        Password = (EditText)findViewById(R.id.etPassword);
        Login = (Button)findViewById(R.id.btnLogin);
        userRegistration = (TextView)findViewById(R.id.tvRegister);
        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Name.getText().toString().equals("")&&!Password.getText().toString().equals("")){
                    validate(Name.getText().toString(), Password.getText().toString());
                }
                else{
                    Toast.makeText(LogIn.this,"please fill all the fields",Toast.LENGTH_LONG).show();
                }

            }
        });

        userRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LogIn.this, RegistrationActivity.class));
            }
        });

    }

    private void validate(String userName, String userPassword) {

        progressDialog.setMessage("connecting..");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(userName, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    checkEmailVerification();
                }else{
                    Toast.makeText(LogIn.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                }

        });


    }

    private void checkEmailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        Boolean emailflag = firebaseUser.isEmailVerified();
        Intent intent=new Intent(LogIn.this, MainActivity.class);
        intent.putExtra("isConnected",true);
        startActivity(intent);

    }

}
