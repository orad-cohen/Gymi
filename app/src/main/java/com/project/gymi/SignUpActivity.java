package com.project.gymi;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);
        mAuth = FirebaseAuth.getInstance();
        String[] options = {"Select role","Trainee","Trainer"};

        ArrayAdapter op= new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,options);
        Spinner sp = findViewById(R.id.spinner);
        sp.setAdapter(op);

    }


    public void Submit(View view) {
        Spinner sp = findViewById(R.id.spinner);
        String role = sp.getSelectedItem().toString();
        if(role=="Select role"){
            Toast.makeText(this, "Please select a role", Toast.LENGTH_SHORT).show();
        }
        else{

            try {
                EditText Email = findViewById(R.id.eSignEmail);
                EditText Password = findViewById(R.id.eSignPassword);
                EditText Name = findViewById(R.id.eSignName);
                String email = Email.getText().toString();
                String password = Password.getText().toString();
                String name = Name.getText().toString();
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    mDatabase = FirebaseDatabase.getInstance().getReference();
                                    Log.d(TAG, "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    mDatabase.child("users").child(user.getUid()).child(name).setValue(role);
                                    Toast.makeText(SignUpActivity.this, "Authentication Succeed.",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    // If sign in fails, display a message to the user.

                                    Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
            }
            catch (Exception e){
                System.out.println(e.toString());

            }


        }

    }
}