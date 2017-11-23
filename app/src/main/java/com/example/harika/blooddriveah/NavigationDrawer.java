package com.example.harika.blooddriveah;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class NavigationDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close) {
            @Override
            public void onDrawerClosed(View drawerview) {
                // code here will be triggered once the drawer closes
                // as we don't want anything to happen so leave this blank
                super.onDrawerClosed(drawerview);
            }
            @Override
            public void onDrawerOpened(View drawerview) {
                // code here is triggered once the drawer open
                // as we don't anything happen as we leave this blank
                super.onDrawerOpened(drawerview);
            }
        };
        // setting the toggle to drawer layout
        drawer.setDrawerListener(toggle);
        // calling the sync state is necessary or else hanmburger icon wont show up
        toggle.syncState();

        // Initialise the navigationview
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
      //  if (id == R.id.action_settings) {
        //    return true;
        //}

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.donation_events) {
          //  aboutme myFragment = new aboutme();
            //getSupportFragmentManager().beginTransaction().replace(R.id.navigation_drawer_frame_layout,myFragment).addToBackStack("").commit();
        }
        else if(id == R.id.blood_chart) {
            //intent = new Intent(NavigationDrawer.this, Task2.class);
            //startActivity(intent);
              BloodChart myFragment = new BloodChart();
            getSupportFragmentManager().beginTransaction().replace(R.id.navigation_drawer_frame_layout,myFragment).addToBackStack("").commit();
        }
        else if(id == R.id.safe_donation) {
            //intent = new Intent(NavigationDrawer.this, Task3.class);
            //startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
