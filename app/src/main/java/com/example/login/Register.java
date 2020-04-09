package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    Button Register;
    EditText email,password,name,phone;
    TextView SignUp;
    FirebaseAuth fAuth;
    ProgressBar timeToReg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        name=(EditText)findViewById(R.id.name);
        phone=(EditText)findViewById(R.id.phoneNo);
        Register=(Button)findViewById(R.id.register);
        fAuth=FirebaseAuth.getInstance();
        timeToReg=(ProgressBar)findViewById(R.id.timeReq);
        if(fAuth.getCurrentUser()!=null)
        {
            startActivity(new Intent(Register.this,MainActivity.class));
            finish();
        }

    }


    public void RegisterBtn(View View){
        String emailF=email.getText().toString().trim();
        String passwordF=password.getText().toString().trim();
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
                if(task.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(),"User Created",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Error !"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}