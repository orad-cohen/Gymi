package com.project.gymi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


public class chooseTrainerActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
                    protected void onCreate(Bundle savedInstanceState) {
                        super.onCreate(savedInstanceState);
                        setContentView(R.layout.activity_choose_trainer);
                        mAuth = FirebaseAuth.getInstance(); //connected to database
                        HashMap<String,String> NametoUID=new HashMap<>();

                        ListView lvTrainerList = findViewById(R.id.lstTrainersView);
                        TextView helloMessage = findViewById(R.id.tvWelcomeMessage);

                        helloMessage.setText("Hello: "+User.getInstance().username+"\nPlease Choose a Trainer:");
                        ArrayList<String> userName= new ArrayList<>();
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                        databaseReference.child("users").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                                    HashMap<String,String> userNode= (HashMap<String, String>) postSnapshot.getValue();
                    String trainerUID= postSnapshot.getKey();
                    if(userNode.get("Role").equals("Trainer") && !(userName.contains(userNode.get("Name")))){
                        userName.add(userNode.get("Name"));
                        NametoUID.put(userNode.get("Name"),trainerUID);
                    }
                }
                //Collections.sort(userName);
                ArrayAdapter<String> itemsAdapter =new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, userName);//כל מאמן יקבל שורה בפני עצמו ברשימה
                lvTrainerList.setAdapter(itemsAdapter);

                lvTrainerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(chooseTrainerActivity.this);
                        builder.setMessage("Do you want to Train With \n"+userName.get(i)+"?");
                        builder.setCancelable(true);
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int id) {
                                Intent intent = new Intent(getApplicationContext(),ChatActivity.class);//מועבר לצ'אט
                                intent.putExtra("Trainee", mAuth.getUid());
                                intent.putExtra("Role","Trainee");
                                intent.putExtra("Trainer",NametoUID.get(userName.get(i)));
                                startActivity(intent);//עוברים למסך הבא ומה שרואים זה בעצם את השמות של המאמן והמתאמן ב - צ'אט
                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}