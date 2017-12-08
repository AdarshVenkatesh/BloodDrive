package com.example.harika.blooddriveah;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ViewAnimator;

public class SafetyMeasures extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safety_measures);

        final ViewAnimator viewAnimator=(ViewAnimator)findViewById(R.id.viewFLipper);
        viewAnimator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnimationFactory.flipTransition(viewAnimator, AnimationFactory.FlipDirection.LEFT_RIGHT);
            }
        });
    }
}
