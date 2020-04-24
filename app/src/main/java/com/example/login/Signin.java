package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class Signin extends AppCompatActivity {
    Button LogIn;
    EditText emailSignin,passwordSignin;
    TextView SignUp ,ForgetPasswordTv;
    FirebaseAuth fAuth;
    ProgressBar timeToLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fAuth=FirebaseAuth.getInstance();
        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(Signin.this, MainActivity.class));
            finish();
        }

        setContentView(R.layout.activity_signin);

        emailSignin=(EditText)findViewById(R.id.email);
        passwordSignin=(EditText)findViewById(R.id.password);
        LogIn=(Button)findViewById(R.id.login);
        SignUp=(TextView)findViewById(R.id.registertv);
        timeToLogin=(ProgressBar)findViewById(R.id.timeReq);
        ForgetPasswordTv=(TextView)findViewById(R.id.forgetpasstv);
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Signin.this,Register.class));
                finish();
            }
        });

        ForgetPasswordTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Signin.this,ForgetPasswordActivity.class));

            }
        });
    }


    public void LogInBtn(View view){
        String emailF=emailSignin.getText().toString().trim();
        String passwordF=passwordSignin.getText().toString().trim();
        if(emailF.isEmpty())
        {
            emailSignin.setError("Email is Required");
            return;
        }
        if(passwordF.isEmpty())
        {
            passwordSignin.setError("Password is Required");
            return;
        }
        if(passwordF.length()<8)
        {
            passwordSignin.setError("Password must be greater then 8 character");
            return;
        }

        timeToLogin.setVisibility(android.view.View.VISIBLE);
        fAuth.signInWithEmailAndPassword(emailF,passwordF).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {

                    timeToLogin.setVisibility(View.INVISIBLE);
                    startActivity(new Intent(Signin.this,MainActivity.class));
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Error !"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



}

