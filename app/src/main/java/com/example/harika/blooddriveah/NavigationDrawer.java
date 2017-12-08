package com.example.harika.blooddriveah;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.design.widget.NavigationView;

import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.vision.text.Text;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.onesignal.OneSignal;
import com.squareup.picasso.Picasso;

import java.util.HashMap;


public class NavigationDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,RequestOrDonate.request_donate_OnClickListener {
    Intent intent;
    FirebaseAuth firebaseAuth;
    ImageView myPicture;
    TextView myName;
    TextView myMailId;
    private static int TIMEOUT=4000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        firebaseAuth=FirebaseAuth.getInstance();

       OneSignal.startInit(this)
                .setNotificationOpenedHandler(new RequestBlood(this))
                .init();

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
                myPicture=(ImageView)findViewById(R.id.profile_image);
                myMailId=(TextView)findViewById(R.id.my_mail_id);
                myName=(TextView)findViewById(R.id.my_name);

                String mailId=firebaseAuth.getCurrentUser().getEmail();
                myMailId.setText(mailId);
                final String userName=mailId.replace(".","_");
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(userName);
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        HashMap<String,String> user = (HashMap<String,String>)dataSnapshot.getValue();
                        String firstName=(String)user.get("firstName");
                        myName.setText(firstName);

                        String url=(String)user.get("photoUrl");
                        Picasso.with (getApplicationContext()). load (url). into (myPicture);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

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



        RequestOrDonate myFragment = new RequestOrDonate();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_navigation_drawer,myFragment).addToBackStack("").commit();
        getSupportFragmentManager().beginTransaction().commitAllowingStateLoss();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.option_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId()) {
            case R.id.profile:
                Intent profile = new Intent(this,UserProfile.class);
                startActivity(profile);
                return true;

            case R.id.message:
                Intent mesg = new Intent(this,ChatBoxActivity.class);
                startActivity(mesg);
                return true;

            case R.id.evnt:
                Intent evnt = new Intent(this,BloodDrive_Events.class);
                startActivity(evnt);
                return true;
        }
        return super.onOptionsItemSelected(item);
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



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.blood_chart) {

          Intent intent=new Intent(NavigationDrawer.this,BloodChart.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fui_slide_in_right,R.anim.fui_slide_out_left);
        }
        if(id==R.id.user_profile)
        {
            Intent intent=new Intent(NavigationDrawer.this,UserProfile.class);
            startActivity(intent);
            overridePendingTransition(R.anim.push_down_in,R.anim.push_up_out);
        }
        if(id==R.id.donation_events)
        {
            Intent intent=new Intent(NavigationDrawer.this,BloodDrive_Events.class);
            startActivity(intent);

        }
        if(id==R.id.safety_measures)
        {
            Intent intent=new Intent(NavigationDrawer.this,SafetyMeasures.class);
            startActivity(intent);
        }
        if(id==R.id.chatbox)
        {
            Intent intent=new Intent(NavigationDrawer.this,ChatBoxActivity.class);
            startActivity(intent);
        }
        if(id==R.id.statistics)
        {
            Intent intent=new Intent(NavigationDrawer.this,Blood_Statistics.class);
            startActivity(intent);
         //   overridePendingTransition(R.anim.rotate,R.anim.push_up_out);
        }
        if(id==R.id.logout)
        {
            finish();

            firebaseAuth.signOut();
            Intent intent=new Intent(NavigationDrawer.this,LoginActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void requestButtonClicked() {
        startActivity(new Intent(getApplicationContext(),RequestBlood.class));
    }

    @Override
    public void donateButtonClicked() {
        startActivity(new Intent(getApplicationContext(),NearByMaps.class));
    }
}