package com.example.harika.blooddriveah;

import android.content.Context;
import android.util.Log;

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
            Log.d("in add server",mRef.toString());
            String id = (String) user.get("contactNumber");
            mRef.child(id).setValue(user);
        }
    }
}
