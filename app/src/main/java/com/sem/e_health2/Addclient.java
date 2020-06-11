package com.sem.e_health2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;

public class Addclient extends AppCompatActivity {
    EditText tnom ;
    EditText tprenom;
    EditText ttelephone;
    EditText tage;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseAuth mAuth;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addclient);
        Intent intent = getIntent();
        String docID = intent.getStringExtra("docid");

        mAuth = FirebaseAuth.getInstance();
        DatabaseReference Ref = database.getReference("E-Health/Doctors/"+docID);
        final DatabaseReference clienRef = Ref.child("Clients");
        tnom = findViewById(R.id.name);
        tprenom = findViewById(R.id.lastname);
        ttelephone = findViewById(R.id.phone);
        tage =findViewById(R.id.tele);

        FloatingActionButton fab = findViewById(R.id.addc);
        fab.setOnClickListener(v ->{
            Client et = new Client();
            et.setName(tnom.getText().toString());
            et.setLastName(tprenom.getText().toString());
            et.setPhone(ttelephone.getText().toString());
            et.setAge(tage.getText().toString());
            clienRef.child(et.getName()+" "+et.getLastName()).setValue(et);
            startActivity(new Intent(Addclient.this,DoctorActivity.class));
            finish();







        });

    }


}
