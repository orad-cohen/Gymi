package com.project.gymi;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class User {
    private static User instance=null;

    public String username;
    public String role;
    private static FirebaseAuth mAuth;
    private User() {
        final FirebaseDatabase Database = FirebaseDatabase.getInstance();
        String Uid = util.getUID();
        DatabaseReference red = Database.getReference("users/"+Uid+"/Name");
        red.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                username = snapshot.getValue(String.class);
                System.out.println("inner "+ username);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        DatabaseReference rl = Database.getReference("users/"+Uid+"/Role");
        rl.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                role = snapshot.getValue(String.class);
                System.out.println("inner "+ username);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public User (User user){
        this.username= user.username;
        this.role = user.role;

    }

     public User (String name,String role){
        this.username= name;
        this.role = role;

    }

    public static User getInstance()
    {
        if (instance == null)
            instance = new User();

        return instance;
    }



}