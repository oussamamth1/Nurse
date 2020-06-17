package com.sem.e_health2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import static com.sem.e_health2.DoctorActivity.changeStatusBarToWhite;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    TextView registre ;
    ImageView BtLogin ;
    EditText email ;
    EditText password ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        changeStatusBarToWhite(this);
        mAuth = FirebaseAuth.getInstance();
        BtLogin = findViewById(R.id.imageView33);
        email = findViewById(R.id.edt_email);
        password = findViewById(R.id.edt_password);

        BtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login();

            }
        });

        registre = findViewById(R.id.tc_registre);
        registre.setOnClickListener(ls);
    }
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    View.OnClickListener ls = v -> startActivity(new Intent(MainActivity.this,Registre.class));
    private void updateUI(FirebaseUser currentUser) {


        if(currentUser != null){

            startActivity(new Intent(MainActivity.this,DoctorActivity.class));
        }

    }
    public void Login(){

        mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            // Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }


}
