package com.project.gymi;

import android.location.Location;
import android.location.LocationListener;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class MyLocationListener implements LocationListener {
    @Override
    public void onLocationChanged(@NonNull Location location) {
        DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference().child("users");
        dataRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("longitude").setValue(location.getLongitude());
        dataRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("latitude").setValue(location.getLatitude());
    }
}
