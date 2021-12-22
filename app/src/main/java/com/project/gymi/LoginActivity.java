package com.project.gymi;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.*;

import java.security.Permission;
import java.util.Date;
import java.util.Iterator;

public class LoginActivity extends AppCompatActivity{
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    private DatabaseReference ref = firebaseDatabase.getReference("users/");
    private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        setContentView(R.layout.login_layout);
        /*if(user!=null){
            signIn();
        }
        else{

        }*/


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void signUp(View view) {
        Intent intent = new Intent(this,SignUpActivity.class);
        startActivity(intent);
    }
    public void login(View view){
        //get Email and Password from view
        String email = ((EditText) findViewById(R.id.eEmail)).getText().toString();
        String password = ((EditText) findViewById(R.id.ePassword)).getText().toString();
        //avoid sending empty data to FireBase
        if(email.equals("")||password.equals("")){
            Toast.makeText(this, "Missing credentials", Toast.LENGTH_SHORT).show();
            return;
        }
        //Logging in to FireBase
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success");
                        user = mAuth.getCurrentUser();
                        User.getInstance(); //Update singleton user so it would be ready for the app;
                        signIn();


                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();

                    }
                });

    }
    public void signIn(){
        updateTimestamp();
        updateLocation();
        ref.child(user.getUid()).child("Role").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.getValue(String.class).equals("Trainer")){
                    Intent intent = new Intent(LoginActivity.this,TrainerHomeActivity.class);
                    firebaseDatabase.getReference().child("Requests").child(user.getUid()).addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                CharSequence name = getString(R.string.app_name);
                                String description = getString(R.string.channel);
                                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                                NotificationChannel channel = new NotificationChannel("testing?", name, importance);
                                channel.setDescription(description);
                                // Register the channel with the system; you can't change the importance
                                // or other notification behaviors after this
                                NotificationManager notificationManager = getSystemService(NotificationManager.class);
                                notificationManager.createNotificationChannel(channel);
                                NotificationCompat.Builder builder = new NotificationCompat.Builder(LoginActivity.this,"testing?");
                                builder.setSmallIcon(R.drawable.ic_stat_name)
                                        .setContentTitle("Test")
                                        .setContentText("This is a Test")
                                        .setAutoCancel(true);
                                NotificationManagerCompat nm = NotificationManagerCompat.from(LoginActivity.this);
                                nm.notify(21323,builder.build());
                            }
                            else{
                                Notification.Builder builder = new Notification.Builder(LoginActivity.this)
                                        .setSmallIcon(R.drawable.ic_stat_name)
                                        .setContentTitle("Test")
                                        .setContentText("This is a Test")
                                        .setAutoCancel(true);

                                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                                notificationManager.notify(213121, builder.build());
                            }

                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    startActivity(intent);
                }
                else{

                    Intent intent = new Intent(LoginActivity.this,TraineeHomeActivity.class);

                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG,error.toString());
            }
        });

    }
    protected void updateTimestamp() {
        Date time = new Date();
        ref.child(user.getUid()).child("Timestamp").setValue(time.getTime());
    }
    protected void updateLocation(){
        LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        if(checkCallingOrSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            LocationListener locationListener = new MyLocationListener();
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
        }
        else{
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

            switch (requestCode) {
                case 1: {

                    // If request is cancelled, the result arrays are empty.
                    if (grantResults.length > 0&& grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        // permission was granted, yay! Do the
                        // contacts-related task you need to do.
                    } else {

                        // permission denied, boo! Disable the
                        // functionality that depends on this permission.
                        Toast.makeText(LoginActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                    }
                    return;
                }

                // other 'case' lines to check for other
                // permissions this app might request
            }

    }
}