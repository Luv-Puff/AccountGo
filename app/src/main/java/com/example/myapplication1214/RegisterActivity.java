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



public class RegisterActivity extends AppCompatActivity {
    private EditText reg_email;
    private EditText reg_pwd;
    private EditText reg_vali_pwd;
    private Button reg_btn;
    private Button return_log_btn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        reg_email = (EditText)findViewById(R.id.regemail);
        reg_pwd = (EditText)findViewById(R.id.regpwd);
        reg_vali_pwd = (EditText)findViewById(R.id.repwd_vali);
        reg_btn = (Button) findViewById(R.id.regbtn);
        return_log_btn = (Button)findViewById(R.id.reg_havacc);

        return_log_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });

        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = reg_email.getText().toString();
                String pwd = reg_pwd.getText().toString();
                String pwd_vali = reg_vali_pwd.getText().toString();
                if (!TextUtils.isEmpty(email)&&!TextUtils.isEmpty(pwd)&&!TextUtils.isEmpty(pwd_vali)){
                    if (pwd.equals(pwd_vali)){
                        mAuth.createUserWithEmailAndPassword(email,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()){
                                    sendToMain();
                                }else {
                                    String err = task.getException().getMessage();
                                    Toast.makeText(RegisterActivity.this,"Error: "+err,Toast.LENGTH_LONG).show();
                                }

                            }
                        });

                    }else{
                        Toast.makeText(RegisterActivity.this,"Confirm Password does not match to Password!",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser cUser = mAuth.getCurrentUser();
        if (cUser != null) {
            sendToMain();
        }
    }
    private void sendToMain(){
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }
}