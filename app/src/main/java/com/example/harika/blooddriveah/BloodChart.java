package com.example.harika.blooddriveah;

import android.support.transition.Explode;
import android.support.transition.Slide;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class BloodChart extends AppCompatActivity {

    TableRow row1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_chart);

getWindow().setAllowEnterTransitionOverlap(false);
       // Explode explode=new Explode();
        //getWindow().setExitTransition(explode);
       //
        android.transition.Explode explode=new android.transition.Explode();
        getWindow().setExitTransition(explode);
    }
}
