package com.project.gymi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {
        private ScrollView scrollWindow;
        private Button send,sendRequest;
        private EditText txt;
        private String Trainer,Trainee;
        private FirebaseDatabase chatData = FirebaseDatabase.getInstance();
        private DatabaseReference chatRef,pairRef;
        private String myRole;
        private String key;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //attach views to variables
        setContentView(R.layout.chat_layout);
        sendRequest = new Button(getApplicationContext());
        sendRequest.setText("Send the trainer a request");
        pairRef = chatData.getReference().child("Trainers");

        scrollWindow = findViewById(R.id.window);
        LinearLayout chatLayout = findViewById(R.id.chatLayout);

        send = findViewById(R.id.send);
        txt = findViewById(R.id.txtsend);
        Trainee = getIntent().getExtras().get("Trainee").toString();
        Trainer = getIntent().getExtras().get("Trainer").toString();
        myRole = getIntent().getExtras().get("Role").toString();
        if(myRole.equals("Trainee")){
            pairRef.child(Trainer).child(Trainee).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(!snapshot.exists()){
                        sendRequest.setBackgroundColor(getResources().getColor(R.color.dark_orange));
                        chatLayout.addView(sendRequest);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {}});

        }

        sendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth auth = FirebaseAuth.getInstance();
                FirebaseUser user = auth.getCurrentUser();
                pairRef.child("CkorN6Rq6KZtdiIAhMvoh6DgmPp1").child(user.getUid()).setValue("1");
                Toast.makeText(ChatActivity.this, "request sent", Toast.LENGTH_SHORT).show();
            }
        });
        //use Trainer Trainee to set up a chatroom
        String chatRoom = Trainer+" "+Trainee;
        chatRef = chatData.getReference().child("chat").child(chatRoom).getRef();
        //set up send listener
        send.setOnClickListener(v -> {
            if(txt.getText().toString().equals("")){//don't send empty messages
                return;
            }

            Map<String,Object> chatRoom1 = new HashMap<String,Object>();

            key = chatRef.push().getKey();
            chatRef.updateChildren(chatRoom1);
            //generate a unique key for a message
            DatabaseReference message_root = chatRef.child(key);
            Map<String,Object> MessageMap = new HashMap<String,Object>();
            //update messageMap
            if(myRole.equals("Trainer")){MessageMap.put("Name",Trainer);}
            else{MessageMap.put("Name",Trainee);}
            MessageMap.put("msg",txt.getText().toString());

            View view = ChatActivity.this.getCurrentFocus();

            txt.setText("");//clear text box
            if (view != null) {//collapse keyboard after sending message
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
            //Update FireBase data base
            message_root.updateChildren(MessageMap);
        });
        //set up message displaying listeners
        chatRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {append_chat_conversation(dataSnapshot,myRole);}

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {append_chat_conversation(dataSnapshot,myRole);}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

    }


    private String chat_msg,chat_user_name,chat_user_id,msg_role;
    private LinearLayout messageWindow;

    private void append_chat_conversation(DataSnapshot dataSnapshot,String myRole) {
        messageWindow =findViewById(R.id.chatwind);
        DatabaseReference refs = chatData.getReference().child("users");
        refs.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterator i = dataSnapshot.getChildren().iterator();

                while (i.hasNext()){
                    LinearLayout da = new LinearLayout(getApplicationContext());
                    da.setOrientation(LinearLayout.HORIZONTAL);

                    TextView messageContent = new TextView(getApplicationContext());
                    TextView name = new TextView(getApplicationContext());

                    chat_user_id = (String) ((DataSnapshot)i.next()).getValue();
                    chat_msg= (String) ((DataSnapshot)i.next()).getValue();

                    msg_role=snapshot.child(chat_user_id).child("Role").getValue(String.class);
                    chat_user_name = snapshot.child(chat_user_id).child("Name").getValue(String.class);

                    messageContent.setTextSize(24);
                    //Bitmap resizedBitmap;
                    name.setText(chat_user_name);
                    if(myRole.equals(msg_role)){

                        da.setGravity(Gravity.RIGHT);
                        name.setGravity(Gravity.RIGHT);
                        messageContent.setGravity(Gravity.RIGHT);
                        messageContent.setText(chat_msg);
                        messageContent.setPadding(0,0,25,0);
                        name.setPadding(0,0,10,0);
                        //messageContent.setBackground(getDrawable(R.color.purple_200));

                        //code for using vector as message background.
                        /*
                        Bitmap bk = getBitmapFromVectorDrawable(ChatActivity.this,R.drawable.ic_spbl);
                        messageContent.measure(0, 0);
                        Matrix matrix = new Matrix();
                        float he = bk.getHeight();
                        float wi = bk.getWidth();
                        matrix.postScale((float) messageContent.getMeasuredWidth()/wi,(float)messageContent.getMeasuredHeight()/  he);
                        messageContent.setPadding(10,10,101,10);
                        resizedBitmap = Bitmap.createBitmap(bk, 0, 0, bk.getWidth(), bk.getHeight(), matrix, false);*/


                    }
                    else{
                        da.setGravity(Gravity.LEFT);
                        name.setGravity(Gravity.LEFT);
                        messageContent.setGravity(Gravity.LEFT);
                        messageContent.setText(chat_msg);
                        name.setPadding(10,0,0,0);
                        messageContent.setPadding(25,0,0,0);
                        //messageContent.setBackground(getDrawable(R.color.lightBlue));

                        //code for using vector as message background.
                        /*
                        Bitmap bk = getBitmapFromVectorDrawable(ChatActivity.this,R.drawable.ic_spgre);
                        messageContent.measure(0, 0);
                        Matrix matrix = new Matrix();
                        float he = bk.getHeight();
                        float wi = bk.getWidth();
                        matrix.postScale((float) messageContent.getMeasuredWidth()/wi,(float)messageContent.getMeasuredHeight()/  he);
                        messageContent.setPadding(10,10,101,10);
                        resizedBitmap = Bitmap.createBitmap(bk, 0, 0, bk.getWidth(), bk.getHeight(), matrix, false);*/

                    }

                    //ImageView img = new ImageView(ChatActivity.this);
                    //img.setImageBitmap(resizedBitmap);
                    //messageContent.setBackground(img.getDrawable());

                    name.setTextSize(24);
                    name.setTypeface(name.getTypeface(), Typeface.BOLD);
                    messageWindow.addView(name);
                    da.addView(messageContent);
                    messageWindow.addView(da);
                    //auto scroll down when messages appear;
                    messageWindow.post(() -> {
                        scrollWindow = findViewById(R.id.window);
                        View v = ChatActivity.this.getCurrentFocus();
                        scrollWindow.fullScroll(v.FOCUS_DOWN);
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }

    /*public static Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
            drawable = (DrawableCompat.wrap(drawable)).mutate();

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }*/



    }

