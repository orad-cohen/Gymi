package com.project.gymi;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyWorkout extends AppCompatActivity {

    TextView abs_sets, abs_returns, backhand_sets,
            backhand_returns, chest_sets,chest_returns,hand_sets,hand_returns;
    Button send_report_btn;

    ImageButton abs_btn,bhand_btn,chest_btn,hand_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_workout);

        abs_sets = findViewById(R.id.num_of_sets_abs);
        abs_returns = findViewById(R.id.num_of_returns_abs);
        backhand_sets =  findViewById(R.id.num_of_sets_bhand);
        backhand_returns = findViewById(R.id.num_of_returns_bhand);
        chest_sets = findViewById(R.id.num_of_sets_chest);
        chest_returns = findViewById(R.id.num_of_returns_chest);
        hand_sets = findViewById(R.id.num_of_sets_hand);
        hand_returns = findViewById(R.id.num_of_returns_hand);
        send_report_btn =findViewById(R.id.send_report_btn);

        abs_btn = findViewById(R.id.abs_link);
        bhand_btn = findViewById(R.id.bhand_link);
        chest_btn = findViewById(R.id.chest_link);
        hand_btn = findViewById(R.id.hand_link);


        abs_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAbs();
            }
        });
        bhand_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToBhand();
            }
        });
        chest_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAbs();
            }
        });
        hand_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToBhand();
            }
        });



    }




    public void getMsg(View view) {
        Toast.makeText(this, "well done! your trainer will get the data and contact you soon", Toast.LENGTH_SHORT).show();
    }
    public void goToAbs(){
        goToUrl ("https://www.youtube.com/watch?v=vkKCVCZe474");
    }
    public void goToBhand(){
        goToUrl ("https://www.youtube.com/watch?v=rj0eWvZ4Deo&ab_channel=VShred");
    }


    private void goToUrl(String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW,uriUrl);
        startActivity(launchBrowser);
    }
}
