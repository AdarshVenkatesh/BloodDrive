package com.example.harika.blooddriveah;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.HashMap;

public class BloodDrive_Events extends AppCompatActivity implements Events_RecyclerFragment.CustomOnClickRecycleViewListener{

    Fragment myContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("in recycleractivity","yjfyut");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_drive__events);
        if (savedInstanceState != null) {
            myContent = getSupportFragmentManager().getFragment(
                    savedInstanceState, "mContent"
            );

        }

        else {
            myContent = Events_RecyclerFragment.newInstance();
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.recyclerviewactivity, myContent)
                .commit();
    }

    @Override
    public void onRecycleViewItemClicked(View v, HashMap<String, ?> event) {
        EventDetailFragment details=EventDetailFragment.newInstance(event);

        details.setSharedElementEnterTransition(new DetailsTransition());
        details.setEnterTransition(new android.transition.Fade());
        details.setExitTransition(new android.transition.Fade());
        details.setSharedElementReturnTransition(new DetailsTransition());
        v.setTransitionName((String)event.get("id"));


        getSupportFragmentManager().beginTransaction()
                .addSharedElement(v, v.getTransitionName())
                .replace(R.id.recyclerviewactivity,details)
                .addToBackStack(null)
                .commit();

    }
}
