package com.example.harika.blooddriveah;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.common.util.UriUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.Stack;

public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    private Toolbar toolbar;
    private FragmentDrawer drawerFragment;
    private ViewPager viewPager;


    Window window;
    Context context;
    Set<String> specialGuests;
    Set<String> celebs;
    Boolean fanwall = true, fanboards = true, news = true, events = true, questions = true;




    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sharedPreferences =getSharedPreferences("APP_DETAILS",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("IN_BACKGROUND",true);
        editor.commit();
        Log.d(getClass().getSimpleName(),"Home onPause");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);

        window=this.getWindow();

        context = this;
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        int fromPostQuestion = getIntent().getIntExtra("fromPostQuestion", 0);
        int fromPostSuccess = getIntent().getIntExtra("fromPostSuccess", 0);
        int fromPostSuccessRedirectToQuestions = getIntent().getIntExtra("redirectToQuestions", 0);
        setContentView(R.layout.activity_main);

        // Initializing all the required elements as present in the activity_home.xml.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.blue));
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        /* TODO Can run broad cast reciever */
       /* if (CommonRequiredServices.isNetworkAvailable(this)) {
            UpdateTask updateTask = new UpdateTask();
            updateTask.execute(this);
        }*/
        // This function will help to make use of the toolbar as the ActionBar of the app.
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerFragment = (FragmentDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
        drawerFragment.setDrawerListener(this);
        // Calling the viewPagerController with the layouts viewPager as an argument.

      /*  if(celebs.size()>0){
            if (fromPostQuestion == 1) {
                viewPager.setCurrentItem(0);
            }
            if (fromPostSuccess == 1) {
                viewPager.setCurrentItem(2);
            }
            if (fromPostSuccessRedirectToQuestions == 1) {
                viewPager.setCurrentItem(0);
            }

        }*/
        SharedPreferences preferences = this.getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE);
        String name = preferences.getString("userName", "Fan");
        String base64ProfilePicture = preferences.getString("profilePicture", "");
        String phoneNumber = preferences.getString("phoneNumber", "");


    }







    @Override
    public void onDrawerItemSelected(View view, int position) {

    }

    /*
    Creating a new FragmentPagerAdapter.
     */











}