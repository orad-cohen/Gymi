package com.project.gymi;

import static com.project.gymi.util.getUID;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
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

public class TraineeHomeActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.trainee_home);

    }

    public void getName(View view) {
        Toast.makeText(this, "Your name is "+ User.getInstance().username, Toast.LENGTH_SHORT).show();



    }

    public void getRole(View view) {
        Toast.makeText(this, "Your role is "+ User.getInstance().role, Toast.LENGTH_SHORT).show();
    }

    public void newTrainer(View view) {
        Intent intent = new Intent(this,FindTrainerActivity.class);
        startActivity(intent);
    }

    public void chatTrainer(View view) {
        Intent intent = new Intent(this,ChatActivity.class);
        intent.putExtra("Trainee", mAuth.getUid());
        intent.putExtra("Role","Trainee");
        intent.putExtra("Trainer","CkorN6Rq6KZtdiIAhMvoh6DgmPp1");
        startActivity(intent);
    }

    public void startWorkout(View view) {
        Intent intent = new Intent(this,WorkoutReport.class);
        startActivity(intent);
    }

    public void workoutReports(View view) {
        Intent intent = new Intent(this,ReportsActivity.class);
        startActivity(intent);
    }
}
