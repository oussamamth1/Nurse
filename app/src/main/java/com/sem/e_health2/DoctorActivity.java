package com.sem.e_health2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

public class DoctorActivity extends AppCompatActivity implements ContactAdapter.ItemClickListener {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference delRf ;
    DatabaseReference Ref ;
    FloatingActionButton plus ;
    FirebaseAuth mAuth;
    ContactAdapter Adapter ;
    RecyclerView recyclerview ;
    List<Client> listData= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        recyclerview = findViewById(R.id.RC);
        enableSwipeToDeleteAndUndo();
        plus = findViewById(R.id.add);
        FloatingActionButton signOut = findViewById(R.id.signOut);
        Adapter = new ContactAdapter(this,listData) ;
       Adapter.setClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        DatabaseReference myRef = database.getReference("E-Health/Doctors/"+Sub()+"/Clients");
        delRf = database.getReference("E-Health/Doctors/"+Sub());
        Ref = database.getReference("E-Health/Client name");

        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        ((SimpleItemAnimator) recyclerview.getItemAnimator()).setSupportsChangeAnimations(false);
        recyclerview.setAdapter(Adapter);
        recyclerview.setHasFixedSize(true);
        myRef.addValueEventListener(vel);
        signOut.setOnClickListener(v ->{

            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(DoctorActivity.this,MainActivity.class)
            );



        });
        plus.setOnClickListener(v ->{

            Intent intent = new Intent(DoctorActivity.this,Addclient.class);

            intent.putExtra("docid",Sub());
            startActivity(intent);



        });



    }
            ValueEventListener vel = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Client client ;
                    listData.clear();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        client = ds.getValue(Client.class);
                        if (client != null) {
                            listData.add(client);

                        }

                    }
                    Adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };

    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(this) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {


                final int position = viewHolder.getAdapterPosition();
                Adapter.removeItem(position,delRf);

            }

        };
        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(recyclerview);
    }

    @Override
    public void onItemClickListener(View view, int position) {
        Intent intent = new Intent(DoctorActivity.this,Addtest.class);
        Ref.setValue(listData.get(position).getName()+" "+listData.get(position).getLastName());
                        intent.putExtra("name",listData.get(position).getName());
                        intent.putExtra("lastname",listData.get(position).getLastName());
                        intent.putExtra("docid",Sub());
                        startActivity(intent);

    }
    public String Sub(){

        String filename = (mAuth.getCurrentUser().getEmail());
        int iend = filename.indexOf("@");

        String subString;
        if (iend != -1)
        {
            subString= filename.substring(0 , iend); //this will give abc
            return subString ;
        }
        return null ;
    }




}
