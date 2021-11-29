package com.project.gymi;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class util {

    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();;

    public static String getUID(){
        return mAuth.getUid();
    }




}
