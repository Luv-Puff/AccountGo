package com.example.myapplication1214;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddActivity extends AppCompatActivity {
    private Spinner IEspinner;
    private Spinner Typespinner;
    private ArrayAdapter<CharSequence> IEAdapter;
    private ArrayAdapter<CharSequence> IncomeAdapter;
    private ArrayAdapter<CharSequence> ExpenseAdapter;
    private EditText Addamount;
    private Button ADDbutton;
    private Button Add_backbtn;
    private TextView Add_dateview;
    //private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private String current_user_id;
    private String Add_date;
    private String Add_itemname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        //storageReference = FirebaseStorage.getInstance().getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        current_user_id = firebaseAuth.getCurrentUser().getUid();

        ADDbutton = findViewById(R.id.add_btn);
        Add_backbtn = findViewById(R.id.add_backbtn);
        Add_date  = getIntent().getStringExtra("date");
        Add_dateview = findViewById(R.id.add_dateview);
        Add_dateview.setText(Add_date);
        IEspinner = (Spinner) findViewById(R.id.add_IEtype);
        Typespinner = findViewById(R.id.add_typespinner);
        IEAdapter  = ArrayAdapter.createFromResource(
                this, R.array.add_IE, android.R.layout.simple_spinner_item );
        IEAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        IncomeAdapter  = ArrayAdapter.createFromResource(
                this, R.array.add_incomelist, android.R.layout.simple_spinner_item );
        ExpenseAdapter = ArrayAdapter.createFromResource(
                this, R.array.add_expenselist, android.R.layout.simple_spinner_item );
        IEspinner.setAdapter(IEAdapter);
        IEspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (IEAdapter.getItem(i).equals("Income")){

                    IncomeAdapter.setDropDownViewResource(
                            android.R.layout.simple_spinner_dropdown_item);
                    Typespinner.setAdapter(IncomeAdapter);
                }else{
                    ExpenseAdapter.setDropDownViewResource(
                            android.R.layout.simple_spinner_dropdown_item);
                    Typespinner.setAdapter(ExpenseAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ADDbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Addamount = findViewById(R.id.add_numtext);
                Add_itemname= ((EditText)findViewById(R.id.add_editItem)).getText().toString();
                if (!Addamount.getText().toString().equals("")){
                    //String id = UUID.randomUUID().toString();
                    int add = Integer.parseInt(Addamount.getText().toString());
                    boolean chosenIE = IEspinner.getSelectedItem().toString().equals("Income");
                    String chosenType =Typespinner.getSelectedItem().toString();
                    Post newpost = new Post(Add_itemname,chosenType,current_user_id,Add_date,true,add);

//                    Map<String,Object> PostMap = new HashMap<>();
//                    PostMap.put("Id",id);
//                    PostMap.put("Income or Expense",chosenIE);
//                    PostMap.put("Type",chosenType);
//                    PostMap.put("Item_name",Add_itemname);
//                    PostMap.put("Amount",add);
//                    PostMap.put("User_id",current_user_id);
//                    PostMap.put("Date",Add_date);

                    firebaseFirestore.collection("Posts").add(newpost)
//                            .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    Toast.makeText(AddActivity.this,"Post was added!",Toast.LENGTH_LONG).show();
//                                    sendToDate();
//                                }
//                            })
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(AddActivity.this,"Post was added!",Toast.LENGTH_LONG).show();
                                    sendToDate();

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(AddActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                                }
                            });

                }else{
                    Toast.makeText(AddActivity.this,"Please input an amount!",Toast.LENGTH_LONG).show();
                }

            }
        });
        Add_backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToDate();
            }
        });


    }
    private void sendToDate(){
        Intent intent = new Intent(AddActivity.this, DateActivity.class);
        if (!Add_date.equals("")){
            //Toast.makeText(MainActivity.this,date,Toast.LENGTH_LONG).show();
            intent.putExtra("date",Add_date);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(AddActivity.this,"Must choose a dte first!",Toast.LENGTH_LONG).show();
        }

    }
}
