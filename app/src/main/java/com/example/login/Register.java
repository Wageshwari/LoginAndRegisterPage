package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {


    Button Reg;
    EditText email,password,name,phone;
    TextView SignUp;
    FirebaseAuth fAuth;
    FirebaseFirestore Fstore;
    ProgressBar timeToReg;
    String UserId;

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Register.this,Signin.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        name=(EditText)findViewById(R.id.name);
        phone=(EditText)findViewById(R.id.phoneNo);
        Reg=(Button)findViewById(R.id.register);
        fAuth=FirebaseAuth.getInstance();
        Fstore=FirebaseFirestore.getInstance();
        timeToReg=(ProgressBar)findViewById(R.id.timeReq);


    }



    public void RegisterBtn(View view){
        final String emailF=email.getText().toString().trim();
        String passwordF=password.getText().toString().trim();
        final String phoneF=phone.getText().toString().trim();
        final String nameF=name.getText().toString().trim();
        if(emailF.isEmpty())
        {
            email.setError("Email is Required");
            return;
        }
        if(passwordF.isEmpty())
        {
            password.setError("Password is Required");
            return;
        }
        if(passwordF.length()<8)
        {
            password.setError("Password must be greater then 8 character");
            return;
        }

        timeToReg.setVisibility(android.view.View.VISIBLE);
        fAuth.createUserWithEmailAndPassword(emailF,passwordF).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                timeToReg.setVisibility(android.view.View.INVISIBLE);
                if(task.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(),"User Created",Toast.LENGTH_SHORT).show();
                    UserId=fAuth.getCurrentUser().getUid();
                    DocumentReference dr=Fstore.collection("Users").document("UserId");
                    Map<String,Object> user=new HashMap<>();
                    user.put("Fname",nameF);
                    user.put("Email",emailF);
                    user.put("Phone",phoneF);
                    dr.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("Created","User Id is "+UserId);
                        }
                    });
                    startActivity(new Intent(Register.this,MainActivity.class));
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Error !"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }

            }
        });



    }
}