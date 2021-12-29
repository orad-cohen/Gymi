package com.project.gymi;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class TrainerHomeActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private Button requests;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startService(new Intent(this,NotificationService.class));
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.trainer_home);


        Button btnMeals= (Button) findViewById(R.id.btnMeals);//מוציא מצביע למיקום בזיכרון של הכפתור שנמצא ב - layout
        requests = findViewById(R.id.requests);
        btnMeals.setText("Meals");

        String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();//מזהה הייחודי של המשתמש שמחובר
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();//מצביע על השורש של הדאטאבייס
        LinearLayout trainee= findViewById(R.id.traineeList);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterator i = snapshot.child("Trainers").child(mAuth.getCurrentUser().getUid()).getChildren().iterator();
                while(i.hasNext()){
                    String uid = ((DataSnapshot)i.next()).getKey();
                    String name =snapshot.child("users").child(uid).child("Name").getValue(String.class);//מביא את השם של המתאמן
                    TextView req = new TextView(getApplicationContext());
                    req.setText(name);
                    req.setTextSize(24);
                    Button bt = new Button(getApplicationContext());
                    bt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(TrainerHomeActivity.this,ChatActivity.class);
                            intent.putExtra("Trainer",mAuth.getUid());
                            intent.putExtra("Trainee",uid);
                            intent.putExtra("Role","Trainer");
                            startActivity(intent);
                        }
                    });
                    Button bt1 = new Button(getApplicationContext());
                    bt1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(TrainerHomeActivity.this,WorkoutReport.class);
                            startActivity(intent);
                        }
                    });
                    Button bt2 = new Button(getApplicationContext());
                    bt2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(TrainerHomeActivity.this,NewWorkout.class);
                            startActivity(intent);
                        }
                    });
                    LinearLayout  ly=new LinearLayout(getApplicationContext());
                    ly.setOrientation(LinearLayout.HORIZONTAL);
                    bt.setText("Chat");
                    int greenColorValue = Color.parseColor("#fccb7c");
                    bt.setBackgroundColor(greenColorValue);
                    bt.setTextColor(Color.WHITE);
                    bt.setTextSize(12);

                    bt1.setText("Previous Workouts");
                    bt1.setBackgroundColor(greenColorValue);
                    bt1.setTextColor(Color.WHITE);
                    bt1.setTextSize(12);

                    bt2.setText("New Workouts");
                    bt2.setBackgroundColor(greenColorValue);
                    bt2.setTextColor(Color.WHITE);
                    bt2.setTextSize(12);

                    ly.addView(bt);
                    ly.addView(bt1);
                    ly.addView(bt2);
                    trainee.addView(req);
                    trainee.addView(ly);



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnMeals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TrainerHomeActivity.this, mealsActivity.class);
                startActivity(intent);
            }
        });
        requests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrainerHomeActivity.this, RequestActivity.class);
                startActivity(intent);
            }
        });




    }
}
