package com.project.gymi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Workout extends AppCompatActivity {

    private Button new_workout_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trainee_choose_workout);

        new_workout_btn = (Button) findViewById(R.id.new_workout);
        new_workout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new_workout();
            }
        });

    }



    public void new_workout() {
        Intent intent = new Intent(this, NewWorkout.class);
        startActivity(intent);
    }





}
