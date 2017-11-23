package com.example.harika.blooddriveah;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by adars on 11/21/2017.
 */

public class BloodChart extends Fragment {

    public BloodChart()
    {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Log.i("About me", "in about me");
        return inflater.inflate(R.layout.bloodchart, container, false);
    }
}
