package com.example.harika.blooddriveah;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener{

private DrawerLayout drawerLayout;
    Button request;
    private ActionBarDrawerToggle mToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        request=(Button)findViewById(R.id.requestbutton);
        request.setOnClickListener(this);

        drawerLayout=(DrawerLayout)findViewById(R.id.drawerlayout);
        mToggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(mToggle.onOptionsItemSelected(item))
        {
            int id=item.getItemId();
            Log.d(String.valueOf(R.id.blood_chart),String.valueOf(id));
            if(id==R.id.blood_chart)
            {
                Log.d("inside id is:",String.valueOf(id));
                BloodChart myFragment = new BloodChart();
                getSupportFragmentManager().beginTransaction().replace(R.id.replace_layout,myFragment).addToBackStack("").commit();

            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.blood_chart) {
            BloodChart myFragment = new BloodChart();
            getSupportFragmentManager().beginTransaction().replace(R.id.replace_layout,myFragment).addToBackStack("").commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerlayout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {

        if(view==request)
        {
            startActivity(new Intent(getApplicationContext(),RequestBlood.class));
        }
    }
}
