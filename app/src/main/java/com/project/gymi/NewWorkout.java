package com.project.gymi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NewWorkout extends AppCompatActivity {

    //variables - TextView = text input
    TextView abs_sets, abs_returns, backhand_sets,
            backhand_returns, chest_sets,chest_returns,hand_sets,hand_returns;
    ImageButton abs_btn,backhand_btn,chest_btn,hand_btn;
    Button build_all_btn;
    //firebase
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    FirebaseAuth mAuth;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.builed_new_workout);
        mAuth = FirebaseAuth.getInstance();
        abs_sets = findViewById(R.id.abs_sets);
        abs_returns = findViewById(R.id.abs_returns);
        backhand_sets =  findViewById(R.id.backhand_sets);
        backhand_returns = findViewById(R.id.backhand_returns);
        chest_sets = findViewById(R.id.chest_sets);
        chest_returns = findViewById(R.id.chest_returns);
        hand_sets = findViewById(R.id.hand_sets);
        hand_returns = findViewById(R.id.hand_returns);

        abs_btn = findViewById(R.id.abs_btn);
        backhand_btn = findViewById(R.id.backhand_btn);
        chest_btn = findViewById(R.id.chest_btn);
        hand_btn = findViewById(R.id.hand_btn);
        build_all_btn = findViewById(R.id.send_btn);


        build_all_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("full_exercise_plan");

                //getting all values
                String abs_set =  abs_sets.getText().toString();
                String abs_return = abs_returns.getText().toString();
                String backhand_set = backhand_sets.getText().toString();
                String backhand_return = backhand_returns.getText().toString();
                String chest_set = chest_sets.getText().toString();
                String chest_return = chest_returns.getText().toString();
                String hand_set = hand_sets.getText().toString();
                String hand_return = hand_returns.getText().toString();

                WorkoutHelperClass helperClass = new WorkoutHelperClass(abs_set,abs_return,backhand_set,backhand_return,chest_set,chest_return, hand_set,hand_return);
               reference.child(mAuth.getCurrentUser().getUid()).setValue(helperClass);

               myWorkout();
            }
        });




    }

    private void myWorkout() {
        Intent intent = new Intent(this, MyWorkout.class);
        startActivity(intent);
    }


}
