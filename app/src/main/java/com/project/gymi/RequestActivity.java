package com.project.gymi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class RequestActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference ref;
    LinearLayout scroll;
    FirebaseAuth auth;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.requests_layout);
        database= FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        scroll= findViewById(R.id.reqWin);
        ref = database.getReference();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterator i = snapshot.child("Requests").child(auth.getCurrentUser().getUid()).getChildren().iterator();
                while(i.hasNext()){
                    String uid = ((DataSnapshot)i.next()).getKey();
                    String name =snapshot.child("users").child(uid).child("Name").getValue(String.class);
                    TextView req = new TextView(getApplicationContext());
                    req.setText(name);
                    req.setTextSize(16);
                    req.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(RequestActivity.this,ChatActivity.class);
                            intent.putExtra("Trainer",auth.getUid());
                            intent.putExtra("Trainee",uid);
                            intent.putExtra("Role","Trainer");
                            startActivity(intent);
                        }
                    });
                    scroll.addView(req);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void AddNameToView(DataSnapshot _requests){


    }
}