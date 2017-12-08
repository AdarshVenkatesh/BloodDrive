package com.example.harika.blooddriveah;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.*;
import android.support.v7.widget.DividerItemDecoration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.ScaleInAnimator;

/**
 * Created by adars on 12/4/2017.
 */

public class Events_RecyclerFragment extends Fragment {

    EventData eventData = new EventData();
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    EventsFirebaseRecyclerAdapter eventsFirebaseRecyclerAdapter;
    public static Events_RecyclerFragment newInstance() {
        Events_RecyclerFragment fragment = new Events_RecyclerFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    public interface CustomOnClickRecycleViewListener {
        public void onRecycleViewItemClicked(View v, HashMap<String, ?> event);
    }
    private CustomOnClickRecycleViewListener customOnClickRvListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.event_fragment_recycler, container, false);

        customOnClickRvListener = (CustomOnClickRecycleViewListener) view.getContext();

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview2);//recyclerview
        eventData = new EventData();
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        DatabaseReference childRef = FirebaseDatabase.getInstance().getReference().child("postData").getRef();
        eventsFirebaseRecyclerAdapter = new EventsFirebaseRecyclerAdapter(Event.class,
                R.layout.event_item_layout, EventsFirebaseRecyclerAdapter.EventViewHolder.class, childRef, getContext());
      //  recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));

        if (eventData.getSize() == 0) {
       //     recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
            eventData.setAdapter(eventsFirebaseRecyclerAdapter);
            eventData.setContext(getActivity());//getApplicationContext()-activity is used
            eventData.initializeDataFromCloud(); }

        eventsFirebaseRecyclerAdapter.setOnItemClickListener(new EventsFirebaseRecyclerAdapter.RecycleItemClickListener()
        {
            @Override
            public void onItemClick(final View view, int position) {
                Log.d("Movie Detail", "inside on click");

                HashMap<String, ?> event = (HashMap<String, ?>) eventData.getItem(position);
                String id = (String) event.get("id");
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("postData").child(id);
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        HashMap<String,String> event = (HashMap<String,String>)dataSnapshot.getValue();

                        customOnClickRvListener.onRecycleViewItemClicked(view, event);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        recyclerView.setAdapter(eventsFirebaseRecyclerAdapter);
        itemAnimation();
        adapterAnimation();
        return view;
    }

    private void itemAnimation(){
        ScaleInAnimator anim = new ScaleInAnimator();
        anim.setAddDuration(1000);
        anim.setRemoveDuration(1000);
        recyclerView.setItemAnimator(anim);
    }
    private void adapterAnimation() {
        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(eventsFirebaseRecyclerAdapter);
        ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(alphaAdapter);
        recyclerView.setAdapter(scaleAdapter);
    }


}
