package com.example.myapplication1214;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DateActivity extends AppCompatActivity {

    private Button back;
    private Button viewPie;
    private String date;
    private EditText dateview;
    private Button addnew;
    private ToggleButton IE_switch;
    private boolean income = true;

    private FirebaseFirestore db ;
    private FirebaseUser currentUser;
    private PostAdapter adapter;
    private ArrayList<Post> postArrayList ;
    private RecyclerView recyclerView;
    private boolean PiePass;



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

        IE_switch=findViewById(R.id.date_switch);
        IE_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (IE_switch.isChecked()){
                    income = false;
                    setupList(income);


                }else{
                    income = true;
                    setupList(income);
                }
            }
        });
        setupList(income);
        viewPie = findViewById(R.id.viewPie);
        PiePass = false;
        viewPie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PiePass){
                    ViewPieGraph();
                }else{
                    Toast.makeText(DateActivity.this,"You should not pass!",Toast.LENGTH_LONG).show();
                }

            }
        });


    }


    private void setupList(boolean isIncome) {
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        recyclerView = findViewById(R.id.date_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        postArrayList = new ArrayList<>();
        adapter = new PostAdapter(this, postArrayList);
        recyclerView.setAdapter(adapter);
        db= FirebaseFirestore.getInstance();
        db.collection("Posts").whereEqualTo("user_id",currentUser.getUid()).whereEqualTo("date",date).
                whereEqualTo("income",isIncome).orderBy("item_name").
                get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    PiePass = true;

                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();

                    for (DocumentSnapshot d : list) {

                        Post p = d.toObject(Post.class);
                        p.setId(d.getId());
                        postArrayList.add(p);

                    }

                    adapter.notifyDataSetChanged();

                }

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
        intent.putExtra("date",date);
        startActivity(intent);
        finish();
    }

    private void ViewPieGraph(){
        Intent intent = new Intent(DateActivity.this, PieGraphActivity.class);
        intent.putExtra("date",date);
        intent.putExtra("Income",income);
        startActivity(intent);
        finish();

    }
    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }


}
