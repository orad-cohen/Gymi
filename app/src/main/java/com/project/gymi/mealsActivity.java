package com.project.gymi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class mealsActivity extends AppCompatActivity {

    List<String> mealsName;
    Map<String, List<String>> allmealData;
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meals);
        FloatingActionButton btnAddMeal = findViewById(R.id.btnAddMeal);
        btnAddMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),addMealActivity.class);
                startActivity(intent);
            }
        });
        createGroupList();
    }

    private void createGroupList() {
        DatabaseReference productsRefernce= FirebaseDatabase.getInstance().getReference("Meals");
        mealsName = new ArrayList<>();
        final Meal[] meal = new Meal[1];
        allmealData = new HashMap<String, List<String>>();
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot child : snapshot.getChildren()){
                    meal[0] =child.getValue(Meal.class);
                    mealsName.add(meal[0].getName());
                    String kCal = Double.toString(meal[0].kCal);
                    String Quantity = Integer.toString(meal[0].Quantity);
                    String kCalTotal = Double.toString(meal[0].kCal * meal[0].Quantity);
                    List<String> classDetails=new ArrayList<> ();
                    classDetails.add("kCal Per Serving: "+kCal);
                    classDetails.add("Quantity: "+Quantity);
                    classDetails.add("Total kCal: "+ kCalTotal);

                    allmealData.put(meal[0].getName(),classDetails);

                }
                expandableListView = findViewById(R.id.mealsList);
                expandableListAdapter = new MyExpandableListAdapter(getApplicationContext(), mealsName, allmealData);
                expandableListView.setAdapter(expandableListAdapter);
                expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                    int lastExpandedPosition = -1;

                    @Override
                    public void onGroupExpand(int i) {
                        if (lastExpandedPosition != -1 && i != lastExpandedPosition) {
                            expandableListView.collapseGroup(lastExpandedPosition);
                        }
                        lastExpandedPosition = i;
                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        productsRefernce.addValueEventListener(postListener);

    }


}