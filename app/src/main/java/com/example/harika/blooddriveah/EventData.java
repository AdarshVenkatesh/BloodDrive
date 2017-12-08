package com.example.harika.blooddriveah;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by adars on 12/4/2017.
 */

public class EventData {

    List<Map<String,?>> eventsList;
    DatabaseReference mRef;
    EventsFirebaseRecyclerAdapter eventsFirebaseRecyclerAdapter;
    Context mContext;

    public void setAdapter(EventsFirebaseRecyclerAdapter mAdapter) {
        eventsFirebaseRecyclerAdapter = mAdapter;
    }

    public void removeItemFromServer(Map<String,?> event){
        if(event!=null){
            String id = (String)event.get("id");
            mRef.child(id).removeValue();
        }
    }

    public void addItemToServer(Map<String,?> event){
        if(event!=null){
            String id = (String) event.get("id");
            mRef.child(id).setValue(event);
        }
    }

    public DatabaseReference getFireBaseRef(){
        return mRef;
    }
    public void setContext(Context context){mContext = context;}

    public List<Map<String, ?>> getMoviesList() {
        return eventsList;
    }

    public int getSize(){
        return eventsList.size();
    }

    public HashMap getItem(int i){
        if (i >=0 && i < eventsList.size()){
            return (HashMap) eventsList.get(i);
        } else return null;
    }


    public void onItemRemovedFromCloud(HashMap item){
        int position = -1;
        String id=(String)item.get("id");
        for(int i=0;i<eventsList.size();i++){
            HashMap event = (HashMap)eventsList.get(i);
            String mid = (String)event.get("id");
            if(mid.equals(id)){
                position= i;
                break;
            }
        }
        if(position != -1){
            eventsList.remove(position);
            Toast.makeText(mContext, "Item Removed:" + id, Toast.LENGTH_SHORT).show();

        }
    }

    public void onItemAddedToCloud(HashMap item){
        int insertPosition = 0;
        String id=(String)item.get("id");
        for(int i=0;i<eventsList.size();i++){
            HashMap event = (HashMap)eventsList.get(i);
            String mid = (String)event.get("id");
            if(mid.equals(id)){
                return;
            }
            if(mid.compareTo(id)<0){
                insertPosition=i+1;
            }else{
                break;
            }
        }
        eventsList.add(insertPosition,item);
    }

    public void onItemUpdatedToCloud(HashMap item){
        String id=(String)item.get("id");
        for(int i=0;i<eventsList.size();i++){
            HashMap event = (HashMap)eventsList.get(i);
            String mid = (String)event.get("id");
            if(mid.equals(id)){
                eventsList.remove(i);
                eventsList.add(i,item);
                break;
            }
        }

    }
    public void initializeDataFromCloud() {
        // moviesList.clear();
        mRef.addChildEventListener(new com.google.firebase.database.ChildEventListener() {
            @Override
            public void onChildAdded(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {
                HashMap<String,String> event = (HashMap<String,String>)dataSnapshot.getValue();
                onItemAddedToCloud(event);

            }

            @Override
            public void onChildChanged(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {
                HashMap<String,String> event = (HashMap<String,String>)dataSnapshot.getValue();
                onItemUpdatedToCloud(event);
            }

            @Override
            public void onChildRemoved(com.google.firebase.database.DataSnapshot dataSnapshot) {
                HashMap<String,String> event = (HashMap<String,String>)dataSnapshot.getValue();
                onItemRemovedFromCloud(event);
            }

            @Override
            public void onChildMoved(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public EventData(){

        eventsList = new ArrayList<Map<String,?>>();
        mRef = FirebaseDatabase.getInstance().getReference().child("postData").getRef();
        eventsFirebaseRecyclerAdapter = null;
        mContext = null;

    }



}
