package com.example.harika.blooddriveah;

import android.content.Context;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by adars on 11/18/2017.
 */

public class UserData {
    List<Map<String,?>> moviesList;
    DatabaseReference mRef;
    Context mContext;
    public UserData()
    {
        moviesList = new ArrayList<Map<String,?>>();
        mRef = FirebaseDatabase.getInstance().getReference().child("Users").getRef();
        mContext = null;
    }

    public void addItemToServer(Map<String,?> user){
        if(user!=null){
           // FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
           // String email=firebaseAuth.getCurrentUser().getEmail();

            String email1=(String)user.get("email");
            String userName1=email1.replace(".","_");


           // String userName=email.replace(".","_");
          //  Log.d("email is:",email1);
          //  Log.d("username is:",userName1);
            mRef.child(userName1).setValue(user);
        }
    }
}
