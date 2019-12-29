package com.example.myapplication1214;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.achartengine.GraphicalView;

import java.util.ArrayList;
import java.util.List;

public class PieGraphActivity extends AppCompatActivity {
    private boolean income;
    private String date;
    private Button back;
    private FirebaseFirestore db ;
    private FirebaseUser currentUser;
    private ArrayList<Post> postArrayList ;
    private ProgressBar progressBar;
    private LinearLayout chartContainer;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_graph);
      income =  getIntent().getBooleanExtra("Income",true);
      date = getIntent().getStringExtra("date");
      progressBar = findViewById(R.id.PieProgress);
      progressBar.setVisibility(View.VISIBLE);
      context = this;
      db= FirebaseFirestore.getInstance();
      currentUser = FirebaseAuth.getInstance().getCurrentUser();
      postArrayList = new ArrayList<Post>();
      chartContainer = findViewById(R.id.pielayout);
      //postArrayList = getList(db,currentUser,date,income);
        db.collection("Posts").whereEqualTo("user_id",currentUser.getUid()).whereEqualTo("date",date).
                whereEqualTo("income",income).orderBy("item_name").
                get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot d : list) {
                        Post p = d.toObject(Post.class);
                        p.setId(d.getId());
                        postArrayList.add(p);
                    }
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(context,"Pie is ready!",Toast.LENGTH_LONG).show();
                    GraphicalView chartView  = PieChartView.getNewInstance(context, postArrayList );

                    //GraphicalView chartView  = PieChartView.getNewInstance(this, 100,60 );
                    chartContainer.addView(chartView);

                }
            }
        });





        back = findViewById(R.id.pie_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToDate();
            }
        });
    }
    private void sendToDate(){
        Intent intent = new Intent(PieGraphActivity.this, DateActivity.class);
        if (!date.equals("")){
            //Toast.makeText(MainActivity.this,date,Toast.LENGTH_LONG).show();
            intent.putExtra("date",date);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(PieGraphActivity.this,"Must choose a dte first!",Toast.LENGTH_LONG).show();
        }

    }

//    private static ArrayList<Post> getList (FirebaseFirestore db,FirebaseUser currentUser,String date,boolean income) {
//        final ArrayList postList = new ArrayList<Post>();
//
//        db.collection("Posts").whereEqualTo("user_id",currentUser.getUid()).whereEqualTo("date",date).
//                whereEqualTo("income",income).orderBy("item_name").
//                get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                if (!queryDocumentSnapshots.isEmpty()) {
//                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
//                    for (DocumentSnapshot d : list) {
//                        Post p = d.toObject(Post.class);
//                        p.setId(d.getId());
//                        postList.add(p);
//                    }
//                }
//            }
//        });
//        return postList;
//
//    }

}
