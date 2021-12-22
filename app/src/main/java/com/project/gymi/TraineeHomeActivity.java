package com.project.gymi;

import static com.project.gymi.util.getUID;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.project.gymi.util;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TraineeHomeActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.trainee_home);

        button = findViewById(R.id.chooseWorkout);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                chooseWorkout();
            }
        });


    }
    public void chooseWorkout() {
        Intent intent = new Intent(this, Workout.class);
        startActivity(intent);
    }






    public void getName(View view) {
        Toast.makeText(this, "Your name is "+ User.getInstance().username, Toast.LENGTH_SHORT).show();



    }

    public void getRole(View view) {
        Toast.makeText(this, "Your role is "+ User.getInstance().role, Toast.LENGTH_SHORT).show();
    }

    public void newTrainer(View view) {
        Intent intent = new Intent(this,chooseTrainerActivity.class);

        startActivity(intent);
    }

    public void chatTrainer(View view) {
        Intent intent = new Intent(this,ChatActivity.class);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Trainers");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterator i = snapshot.getChildren().iterator();
                while(i.hasNext()){
                    DataSnapshot dNext = (DataSnapshot) i.next();
                    Map<String,String> Details = (Map<String, String>)(dNext).getValue();
                    if(Details.containsKey(mAuth.getUid())){
                        intent.putExtra("Trainee", mAuth.getUid());
                        intent.putExtra("Role","Trainee");
                        intent.putExtra("Trainer",dNext.getKey());
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    //public void startWorkout(View view) {
    //    Intent intent = new Intent(this,WorkoutReport.class);
    //    startActivity(intent);
    //}

    public void workoutReports(View view) {
        Intent intent = new Intent(this,ReportsActivity.class);
        startActivity(intent);
    }



}
