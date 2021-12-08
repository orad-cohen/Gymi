package com.project.gymi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class trainee_home extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainee_home);

        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_trainee_home);

        TextView tvName = (TextView) findViewById(R.id.tvName);
        TextView tvRole = (TextView) findViewById(R.id.tvRole);
        Button btnMeals= (Button) findViewById(R.id.btnMeals);
        Button btnChooseTrainer = (Button) findViewById(R.id.btnTrainerAssociation);
        btnMeals.setText("Meals");

        btnChooseTrainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),chooseTrainerActivity.class);
                startActivity(intent);
            }
        });


        String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.child("users").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                tvName.setText("Hello "+user.username+"!");
                tvRole.setText("You Are A "+user.role+".");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnMeals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), mealsActivity.class);
                startActivity(intent);
            }
        });
    }
}