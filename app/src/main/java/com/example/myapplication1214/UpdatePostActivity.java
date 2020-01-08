package com.example.myapplication1214;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UpdatePostActivity extends AppCompatActivity {
    private Post post;
    private FirebaseFirestore db;

    private String update_date,update_item_name,update_type;
    private boolean update_isIncome;
    private double update_amount;
    private Spinner IEspinner;
    private Spinner Typespinner;
    private ArrayAdapter<CharSequence> IEAdapter;
    private ArrayAdapter<CharSequence> IncomeAdapter;
    private ArrayAdapter<CharSequence> ExpenseAdapter;
    private TextView dateview;
    private EditText amount,item;
    private String chosenDate;
    private DatePickerDialog.OnDateSetListener setListener;
    private Button update_button;
    private Button delete_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_post);
        post = (Post) getIntent().getSerializableExtra("post");
        db = FirebaseFirestore.getInstance();
        IEspinner = (Spinner) findViewById(R.id.update_IE_spin);
        Typespinner = findViewById(R.id.update_type_spin);
        IEAdapter  = ArrayAdapter.createFromResource(
                this, R.array.add_IE, android.R.layout.simple_spinner_item );
        IEAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        IncomeAdapter  = ArrayAdapter.createFromResource(
                this, R.array.add_incomelist, android.R.layout.simple_spinner_item );
        ExpenseAdapter = ArrayAdapter.createFromResource(
                this, R.array.add_expenselist, android.R.layout.simple_spinner_item );
        IEspinner.setAdapter(IEAdapter);
        if (post.isIncome()){
            IEspinner.setSelection(0);
        }else{
            IEspinner.setSelection(1);
        }

        IEspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (IEAdapter.getItem(i).equals("Income")){

                    IncomeAdapter.setDropDownViewResource(
                            android.R.layout.simple_spinner_dropdown_item);
                    Typespinner.setAdapter(IncomeAdapter);
                    switch (post.getType()){
                        case "Salary":
                            Typespinner.setSelection(0);break;
                        case "Allowance":
                            Typespinner.setSelection(1);break;
                        case "Award/Bonus":
                            Typespinner.setSelection(2);break;
                            default:
                                Typespinner.setSelection(3);break;

                    }
                }else{
                    ExpenseAdapter.setDropDownViewResource(
                            android.R.layout.simple_spinner_dropdown_item);
                    Typespinner.setAdapter(ExpenseAdapter);
                    switch (post.getType()){
                        case "Food":
                            Typespinner.setSelection(0);break;
                        case "Clothing":
                            Typespinner.setSelection(1);break;
                        case "Housing":
                            Typespinner.setSelection(2);break;
                        case "Transportation":
                            Typespinner.setSelection(3);break;
                        case "Education":
                            Typespinner.setSelection(4);break;
                        case "Entertainment":
                            Typespinner.setSelection(5);break;
                        default:
                            Typespinner.setSelection(6);break;

                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        amount = findViewById(R.id.update_amount_text);
        item= findViewById(R.id.update_item);
        dateview = findViewById(R.id.update_date);
        amount.setText(Double.toString(post.getAmount()));
        item.setText(post.getItem_name());
        dateview.setText(post.getDate());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date chosendate = null;
        try {
             chosendate = sdf.parse(post.getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal = Calendar.getInstance();
        if (chosendate!=null){
            cal.setTime(chosendate);
        }

        final int year = cal.get(Calendar.YEAR);
        final int month = cal.get(Calendar.MONTH);
        final int day = cal.get(Calendar.DAY_OF_MONTH);
        dateview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        UpdatePostActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth
                        , setListener,year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = year + "/" + month + "/" + dayOfMonth;
                chosenDate = date;dateview.setText(chosenDate);
            }
        };
        update_button = findViewById(R.id.button_update);
        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePost();

            }
        });
        delete_button = findViewById(R.id.button_delete);
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdatePostActivity.this);
                builder.setTitle("Are you sure about it?");
                builder.setMessage("I don't feel so good Mr.Stark...");
                builder.setPositiveButton("Yes Yes Yes Yes... YES!!!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deletePost();
                    }
                });

                builder.setNegativeButton("但是我拒絕!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog ad = builder.create();
                ad.show();
            }
        });

    }

    private void deletePost(){
        db.collection("Posts").document(post.getId()).delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(UpdatePostActivity.this, "Post deleted", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(UpdatePostActivity.this, DateActivity.class);
                            String date =dateview.getText().toString();
                            intent.putExtra("date",date);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }

    private void updatePost() {
        update_date = dateview.getText().toString();
        update_isIncome = IEspinner.getSelectedItem().toString().equals("Income");
        update_type = Typespinner.getSelectedItem().toString();
        update_item_name = item.getText().toString();
        if (amount.getText().toString()!=null){
            update_amount = Double.parseDouble(amount.getText().toString());
            db = FirebaseFirestore.getInstance();
            FirebaseUser current_user= FirebaseAuth.getInstance().getCurrentUser();
            Post p = new Post(update_item_name,update_type,current_user.getUid(),update_date,update_isIncome,update_amount);
            db.collection("Posts").document(post.getId()).update(
                    "item_name", p.getItem_name(),
                    "amount", p.getAmount(),
                    "date", p.getDate(),
                    "income", p.isIncome(),
                    "type", p.getType()
            ).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(UpdatePostActivity.this,"Post was updated!",Toast.LENGTH_LONG).show();
                    sendToDate();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(UpdatePostActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();

                }
            });
        }else{
            Toast.makeText(UpdatePostActivity.this,"Please input an amount!",Toast.LENGTH_LONG).show();
        }


    }

    private void sendToDate() {
        Intent intent = new Intent(UpdatePostActivity.this, DateActivity.class);
        String date =dateview.getText().toString();
        intent.putExtra("date",date);
        startActivity(intent);
        finish();
    }
}
