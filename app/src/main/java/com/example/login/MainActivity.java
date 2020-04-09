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

public class MainActivity extends AppCompatActivity {

    Button LogIn;
    EditText email,password;
    TextView SignUp;
    FirebaseAuth fAuth;
    ProgressBar timeToLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        LogIn=(Button)findViewById(R.id.login);
        SignUp=(TextView)findViewById(R.id.register);
        fAuth=FirebaseAuth.getInstance();
        timeToLogin=(ProgressBar)findViewById(R.id.timeReq);


      SignUp.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              startActivity(new Intent(MainActivity.this,Register.class));
          }
      });


    }


    public void LogInBtn(View view){
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

        timeToLogin.setVisibility(android.view.View.VISIBLE);
        fAuth.signInWithEmailAndPassword(emailF,passwordF).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
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
