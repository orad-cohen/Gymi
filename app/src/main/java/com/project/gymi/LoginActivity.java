package com.project.gymi;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;
public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    private DatabaseReference ref = firebaseDatabase.getReference("users/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.login_layout);

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
                        FirebaseUser user = mAuth.getCurrentUser();
                        User.getInstance(); //Update singleton user so it would be ready for the app;

                        ref.child(user.getUid()).child("Role").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                if(snapshot.getValue(String.class).equals("Trainer")){
                                    Intent intent = new Intent(LoginActivity.this,ChatActivity.class);

                                    //todo once the database structure for Trainer Trainee connection is ready update it accordingly
                                    intent.putExtra("Trainer",user.getUid());
                                    intent.putExtra("Trainee","AW88ReWhZEavTl4diQFzympWNEO2");
                                    intent.putExtra("Role","Trainer");
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

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();

                    }
                });
    }
}