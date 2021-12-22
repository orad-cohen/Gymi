package com.project.gymi;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NotificationService extends Service {


    private FirebaseDatabase firebaseDatabase;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("TAG", "onStartCommand");
        super.onStartCommand(intent, flags, startId);
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.getReference().child("Requests").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addChildEventListener(new ChildEventListener() {
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
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(NotificationService.this,"testing?");
                    builder.setSmallIcon(R.drawable.ic_stat_name)
                            .setContentTitle("Request")
                            .setContentText("A trainee send a new request")
                            .setAutoCancel(true);
                    NotificationManagerCompat nm = NotificationManagerCompat.from(NotificationService.this);
                    nm.notify(21323,builder.build());
                }
                else{
                    Notification.Builder builder = new Notification.Builder(NotificationService.this)
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

        return START_STICKY;
    }


    @Override
    public void onCreate() {
        Log.e("TAG", "onCreate");


    }

    @Override
    public void onDestroy() {
        Log.e("TAG", "onDestroy");

        super.onDestroy();


    }

    //we are going to use a handler to be able to run in our TimerTask



}

