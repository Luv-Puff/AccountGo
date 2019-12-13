package com.example.myapplication1214;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private EditText loginemailtxt;
    private EditText loginpwdtxt;
    private Button loginbtn;
    private Button loginreg;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        loginemailtxt = (EditText) findViewById(R.id.loginemail);
        loginpwdtxt = (EditText) findViewById(R.id.loginpwd);
        loginbtn = (Button)findViewById(R.id.logbtn);
        loginreg = (Button)findViewById(R.id.log_regbtn);

        loginreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regIntent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(regIntent);

            }
        });
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String loginemail = loginemailtxt.getText().toString();
                String loginpwd = loginpwdtxt.getText().toString();
                if (!TextUtils.isEmpty(loginemail)&&!TextUtils.isEmpty(loginpwd)){
                    mAuth.signInWithEmailAndPassword(loginemail,loginpwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                sendToMain();

                            }else {
                                String err = task.getException().getMessage();
                                Toast.makeText(LoginActivity.this,"Error: "+err,Toast.LENGTH_LONG).show();

                            }

                        }
                    });
                }
            }
        });

    }
    @Override
    protected void onStart(){
        super.onStart();
        FirebaseUser Currentuser = mAuth.getCurrentUser();
        if (Currentuser != null) {
            sendToMain();
        }
    }
    private void sendToMain(){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }
}
