package com.example.myapplication1210;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DateActivity extends AppCompatActivity {

    private Button back;
    String date;
    private EditText dateview;
    private Button addnew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);
        date = getIntent().getStringExtra("date");
        dateview = findViewById(R.id.date_date);
        if (date != null){
            dateview.setText(date);
        }
        back = findViewById(R.id.date_backbtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendToMain();
            }
        });
        addnew = findViewById(R.id.date_add);
        addnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendToAdd();
            }
        });

    }


    private void sendToMain(){
        Intent intent = new Intent(DateActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }
    private void sendToAdd(){
        Intent intent = new Intent(DateActivity.this, AddActivity.class);
        startActivity(intent);
        finish();
    }
}
