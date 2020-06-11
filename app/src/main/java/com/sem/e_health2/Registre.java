package com.sem.e_health2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Registre extends AppCompatActivity {
    private FirebaseAuth mAuth;

    ImageView BtRegistre ;
    EditText user ;
    EditText email ;
    EditText password ;
    TextView login ;
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registre);
        login = findViewById(R.id.tc_login);
        mAuth = FirebaseAuth.getInstance();
        BtRegistre = findViewById(R.id.imageView3);
        email = findViewById(R.id.edt_email2);
        password = findViewById(R.id.edt_password2);
        user= findViewById(R.id.edt_username2);

        login.setOnClickListener(ls);
        BtRegistre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignUP();





            }
        });

    }

    View.OnClickListener ls = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(Registre.this,MainActivity.class)
                                );

        }
    };
    private void updateUI(FirebaseUser currentUser) {


        if(currentUser != null){

            startActivity(new Intent(Registre.this,DoctorActivity.class));
        }

    }
    public void SignUP(){
        mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);


                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Registre.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });



    }







}
