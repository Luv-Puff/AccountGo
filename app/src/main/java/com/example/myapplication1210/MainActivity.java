package com.example.myapplication1210;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;


public class MainActivity extends AppCompatActivity {
    private Toolbar maintoolbar;

    private FirebaseAuth mAuth;
    private CalendarView main_Calendar;
    private EditText maindate;
    private Button main_go;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_main);
        maintoolbar = (Toolbar)findViewById(R.id.maintoolbar);
        setSupportActionBar(maintoolbar);
        getSupportActionBar().setTitle("Main");
        main_Calendar = (CalendarView)findViewById(R.id.main_Calender);
        maindate = (EditText)findViewById(R.id.maindate);
        main_Calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                @Override
                public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                    String date = i+"/"+i1+"/"+i2;
                    maindate.setText(date);

                }
        });
        main_go = (Button)findViewById(R.id.main_go);
        main_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendToDate();
            }
        });


        //Log.d("Fcmtest","Token:"+ FirebaseInstanceId.getInstance().getToken());
        //dbvGeDZ0od4:APA91bF-dAPt51GiufSdFDmnGPhsURFjJrFosVmLSlP8VoZB00x9hHp--HzoaHEhgnLZjl7RZqEZGhIvmGwJpTuguNblGA_tpxm9X01KpUZFUpc6QaX4lJbdti3Bzgjr_znYzVsiaJnJ
    }
    @Override
    protected void onStart(){
        super.onStart();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            // No user is signed in

            sendToLogin();
        } else {
            // User is  signed in
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_logout_btn:
                logOut();
                return true;
                default:
                    return false;
        }
        //return super.onOptionsItemSelected(item);

    }

    private  void  logOut(){
        mAuth.signOut();
        sendToLogin();
    }

    private void sendToLogin(){
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();

    }

    private void sendToDate(){
        Intent intent = new Intent(MainActivity.this, DateActivity.class);
        String date =maindate.getText().toString();
        if (!date.equals("")){
            //Toast.makeText(MainActivity.this,date,Toast.LENGTH_LONG).show();
            intent.putExtra("date",date);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(MainActivity.this,"Must choose a dte first!",Toast.LENGTH_LONG).show();
        }

    }
}
