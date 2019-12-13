package com.example.myapplication1210;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class AddActivity extends AppCompatActivity {
    private Spinner IEspinner;
    private Spinner Typespinner;
    private ArrayAdapter<CharSequence> IEAdapter;
    private ArrayAdapter<CharSequence> IncomeAdapter;
    private ArrayAdapter<CharSequence> ExpenseAdapter;
    private EditText Addamount;
    private Button ADDbutton;
    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private String current_user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        storageReference = FirebaseStorage.getInstance().getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        current_user_id = firebaseAuth.getCurrentUser().getUid();

        ADDbutton = findViewById(R.id.add_btn);
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

                if (!Addamount.getText().toString().equals("")){
                    int add = Integer.parseInt(Addamount.getText().toString());
                    String chosenIE = IEspinner.getSelectedItem().toString();
                    String chosenType =Typespinner.getSelectedItem().toString();
                    Map<String,Object> PostMap = new HashMap<>();
                    PostMap.put("Income or Expense",chosenIE);
                    PostMap.put("Type",chosenType);
                    PostMap.put("Amount",add);
                    firebaseFirestore.collection("Posts").document();
                }else{
                    Toast.makeText(AddActivity.this,"Please input an amount!",Toast.LENGTH_LONG).show();
                }

            }
        });



    }
}
