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
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPasswordActivity extends AppCompatActivity {
TextView forgetpasstv,showinstv,backtv;
Button submitBtn;
EditText emailEt;
ProgressBar timetaken;
FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        forgetpasstv=(TextView)findViewById(R.id.forpastv);
        showinstv=(TextView)findViewById(R.id.instructiontv);
        backtv=(TextView)findViewById(R.id.back);
        submitBtn=(Button)findViewById(R.id.ResetPassBtn);
        emailEt=(EditText)findViewById(R.id.emailED);
        timetaken=(ProgressBar)findViewById(R.id.progressBar);
        auth=FirebaseAuth.getInstance();

        backtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ForgetPasswordActivity.this,MainActivity.class));
                finish();
            }
        });
    }

    public void ResetBtn(View view){
      String emailE=emailEt.getText().toString().trim();
      if(emailE.isEmpty()){
          Toast.makeText(getApplication(), "Enter your registered email id", Toast.LENGTH_SHORT).show();
          return;
      }
        timetaken.setVisibility(View.VISIBLE);
        auth.sendPasswordResetEmail(emailE).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ForgetPasswordActivity.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ForgetPasswordActivity.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                        }

                        timetaken.setVisibility(View.GONE);
                    }
                });
    }
}
