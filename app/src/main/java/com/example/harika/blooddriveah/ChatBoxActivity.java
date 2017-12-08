package com.example.harika.blooddriveah;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChatBoxActivity extends AppCompatActivity {


    private FirebaseListAdapter<ChatMessage> adapter;
    RelativeLayout chatBoxActivity;
    FloatingActionButton fab;
    String firstName;
    EditText input;
    List<User> users=new ArrayList<User>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_box);
        chatBoxActivity=(RelativeLayout)findViewById(R.id.chatbox);
        fab=(FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 input=(EditText)findViewById(R.id.input);
                String email= FirebaseAuth.getInstance().getCurrentUser().getEmail();
                final String userName=email.replace(".","_");
              DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(userName);
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        HashMap<String,String> user = (HashMap<String,String>)dataSnapshot.getValue();
                        firstName=(String)user.get("firstName");
                        FirebaseDatabase.getInstance().getReference().child("messages").push().setValue(new ChatMessage(input.getText().toString()
                                ,firstName ));
                        input.setText("");
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });
        displayChatMessage();
    }

    private void displayChatMessage()
    {
        ListView list_of_messages=(ListView)findViewById(R.id.list_of_message);
        adapter=new FirebaseListAdapter<ChatMessage>(this,ChatMessage.class,R.layout.chatbox_list_item,FirebaseDatabase.getInstance().getReference().child("messages")) {
            @Override
            protected void populateView(View v, ChatMessage model, int position) {

                TextView messageText,messageUser,messageTime;
                messageText=(TextView)v.findViewById(R.id.message_text);
                messageUser=(TextView)v.findViewById(R.id.message_user);
                messageTime=(TextView)v.findViewById(R.id.message_time);
                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());
                messageTime.setText(DateFormat.format("MM-dd-yyyy (HH:mm:ss)",model.getMessageTime()));
            }
        };
        list_of_messages.setAdapter(adapter);

    }
}
