package com.project.gymi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class addMealActivity extends AppCompatActivity {

    String UserUID= FirebaseAuth.getInstance().getCurrentUser().getUid();
    DatabaseReference refNewMeal;
    FirebaseAuth ref=FirebaseAuth.getInstance();

    final int[] ServingsCount = {1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);

        EditText etDishName = (EditText) findViewById(R.id.etDishName);
        EditText etCalories = (EditText) findViewById(R.id.etCalories);
        EditText etUnitsConsumed = (EditText) findViewById(R.id.etUnitsConsumed);

        Button btnSubstractUnit = (Button) findViewById(R.id.btnSubstractUnit);
        Button btnAddUnit = (Button) findViewById(R.id.btnAddUnit);
        Button btnFinishAddMeal = (Button) findViewById(R.id.btnFinishAddMeal);
        Button btnAddMoreMeals = (Button) findViewById(R.id.btnAddMoreMeals);


        final int[] ServingsCount = {1};

        etUnitsConsumed.setText("1");

        btnAddUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ServingsCount[0]++;
                String sCount=Integer.toString(ServingsCount[0]);
                etUnitsConsumed.setText(sCount);

            }
        });

        btnSubstractUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ServingsCount[0]>1){
                    ServingsCount[0]--;
                    String sCount=Integer.toString(ServingsCount[0]);
                    etUnitsConsumed.setText(sCount);
                }
            }
        });

        btnAddMoreMeals.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(etDishName.getText().toString()))
                {
                    etDishName.setError("Dish Name Is Required!");
                    return;
                }

                if (TextUtils.isEmpty(etCalories.getText().toString()))
                {
                    etCalories.setError("Calories are Required!");
                    return;
                }

                String DishName = etDishName.getText().toString();
                Double Calories = Double.parseDouble(etCalories.getText().toString());
                int UnitsConsumed = Integer.parseInt(etUnitsConsumed.getText().toString());

                Double totalCalories = Calories*UnitsConsumed;
                UploadMealToFireBase(DishName,totalCalories,UnitsConsumed);

                etDishName.setText("");
                etCalories.setText("");
                etUnitsConsumed.setText("1");

            }
        });

        btnFinishAddMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(etDishName.getText().toString()))
                {
                    etDishName.setError("Dish Name Is Required!");
                    return;
                }

                if (TextUtils.isEmpty(etCalories.getText().toString()))
                {
                    etCalories.setError("Calories are Required!");
                    return;
                }

                String DishName = etDishName.getText().toString();
                Double Calories = Double.parseDouble(etCalories.getText().toString());
                int UnitsConsumed = Integer.parseInt(etUnitsConsumed.getText().toString());

                Double totalCalories = Calories*UnitsConsumed;

                UploadMealToFireBase(DishName,totalCalories,UnitsConsumed);



            }
        });





    }

    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(),mealsActivity.class);
        startActivity(intent);
    }

    public void UploadMealToFireBase(String DishName,Double Calories,int UnitsConsumed){
        Meal meal = new Meal(UserUID,DishName,Calories,UnitsConsumed);

        refNewMeal = FirebaseDatabase.getInstance().getReference().child("Meals").push();
        refNewMeal.setValue(meal);
        Toast.makeText(addMealActivity.this,"Meal Added To Class Successfully",Toast.LENGTH_SHORT).show();


    }
}