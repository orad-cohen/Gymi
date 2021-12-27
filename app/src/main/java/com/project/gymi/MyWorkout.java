package com.project.gymi;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyWorkout extends AppCompatActivity {
    //variables
    TextView abs_sets, abs_returns, backhand_sets,
            backhand_returns, chest_sets,chest_returns,hand_sets,hand_returns;
    Button send_report_btn;

    ImageButton abs_btn,bhand_btn,chest_btn,hand_btn,cam_btn;

    ImageView imageView;



    //_____________when this activity create by the TRAINER the textview's should be updated automatically from firebase___________//
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

        cam_btn = findViewById(R.id.openCamButton);
        imageView = findViewById(R.id.imageView);

        Bundle extras = getIntent().getExtras();

            String absS = extras.getString("absS");
            String absR = extras.getString("absR");
            String backhandS = extras.getString("backhandS");
            String backhandR = extras.getString("backhandR");
            String chestS = extras.getString("chestS");
            String chestR = extras.getString("chestR");
            String handS = extras.getString("handS");
            String handR = extras.getString("handR");

        abs_sets.setText(absS);
        abs_returns.setText(absR);
        backhand_sets.setText(backhandS);
        backhand_returns.setText(backhandR);
        chest_sets.setText(chestS);
        chest_returns.setText(chestR);
        hand_sets.setText(handS);
        hand_returns.setText(handR);

        //Request for camera permission
        if (ContextCompat.checkSelfPermission(MyWorkout.this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MyWorkout.this,
                    new String[]{
                            Manifest.permission.CAMERA
                    },
                    100);
        }

        cam_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open camera
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,100);
            }
        });



        //YOUTUBE references for our exercises
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //the line above do not appear in the guide "how to capture image with camera in android studio | 8.11.2019
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            //get capture image
            Bitmap captureImage = (Bitmap) data.getExtras().get("data");
            //set capture image to imageView
            imageView.setImageBitmap(captureImage);
        }
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

    //helpful function for open browser activities
    private void goToUrl(String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW,uriUrl);
        startActivity(launchBrowser);
    }
}
