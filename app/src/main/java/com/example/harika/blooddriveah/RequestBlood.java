package com.example.harika.blooddriveah;

import android.os.AsyncTask;
import android.os.Build;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RequestBlood extends AppCompatActivity implements View.OnClickListener{
    final String send_email="";
    List<User> users=new ArrayList<User>();
    List<User> users1=new ArrayList<User>();
    DatabaseReference databaseReference;
    Button APositive,ANegative,BPositive,BNegative,ABPositive,ABNegative,OPositive,ONegative;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_blood);
        databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Users").addValueEventListener(new ValueEventListener(){


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for(DataSnapshot child:children)
                {
                    User user = child.getValue(User.class);
                    users.add(user);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        APositive=(Button)findViewById(R.id.APositive);
        ANegative=(Button)findViewById(R.id.ANegative);
        BPositive=(Button)findViewById(R.id.BPositive);
        BNegative=(Button)findViewById(R.id.BNegative);
        ABPositive=(Button)findViewById(R.id.ABPositive);
        ABNegative=(Button)findViewById(R.id.ABNegative);
        OPositive=(Button)findViewById(R.id.OPositive);
        ONegative=(Button)findViewById(R.id.ONegative);
        APositive.setOnClickListener(this);
        ANegative.setOnClickListener(this);
        BPositive.setOnClickListener(this);
        BNegative.setOnClickListener(this);
        ABPositive.setOnClickListener(this);
        ABNegative.setOnClickListener(this);
        OPositive.setOnClickListener(this);
        ONegative.setOnClickListener(this);



    }



    private void sendNotification(final String msgBody,final String send_email)
    {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                int SDK_INT= Build.VERSION.SDK_INT;
                if(SDK_INT>8)
                {
                    StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                   final String send_email;

                  //  if(LoginActivity.LoggedIn_User_Mail.equalsIgnoreCase("harika@gmail.com"))
                    //{
                       // send_email="adarshven@gmail.com";
                    //}
                    //else
                    //{
                     //   send_email="harika@gmail.com";
//                    }

                    try
                    {
                        String jsonResponse;
                        URL url=new URL("https://onesignal.com/api/v1/notifications");
                        HttpURLConnection con=(HttpURLConnection) url.openConnection();
                        con.setUseCaches(false);
                        con.setDoOutput(true);
                        con.setDoInput(true);

                        con.setRequestProperty("Content-Type","application/json; charset=utf-8");
                        con.setRequestProperty("Authorization", "Basic NjIxMjA2YTUtNGU0NS00ZDMxLWIxOWEtZjkyZjc4ODlhNDdi");
                        con.setRequestMethod("POST");

                        String jsonBody="{" +
                                "\"app_id\": \"c24a44f7-859b-4e24-aaee-d7428d3fe9c9\","
                                +"\"filters\": [{\"field\": \"tag\", \"key\": \"User_ID\", \"relation\": \"=\", \"value\": \""+ send_email +"\"}],"
                               // +"\"data\":{\"Users\":\"bar\"},"
                                +"\"contents\":{\"en\":\""+msgBody+"\"}"
                                +"}";

                        System.out.println("String json is:"+jsonBody);

                        byte[] sendBytes=jsonBody.getBytes("UTF-8");
                        con.setFixedLengthStreamingMode(sendBytes.length);
                        OutputStream outputStream=con.getOutputStream();
                        outputStream.write(sendBytes);

                        int httpResponse=con.getResponseCode();
                        System.out.println("httpResponse"+httpResponse);

                        if(httpResponse>=HttpURLConnection.HTTP_OK && httpResponse<HttpURLConnection.HTTP_BAD_REQUEST)
                        {
                            Scanner scanner=new Scanner(con.getInputStream(),"UTF-8");
                            jsonResponse=scanner.useDelimiter("\\A").hasNext() ?scanner.next() : "";
                            scanner.close();
                        }
                        else
                        {
                            Scanner scanner=new Scanner(con.getInputStream(),"UTF-8");
                            jsonResponse=scanner.useDelimiter("\\A").hasNext() ?scanner.next() : "";
                            scanner.close();
                        }
                        System.out.println("jsonResponse is:"+jsonResponse);

                    }
                    catch(Throwable t)
                    {
                        t.printStackTrace();
                    }

                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view==APositive)
        {
            String msgBody="Urgent requirement of A+ve Blood group";
            String email="";
            for (User user:users) {
                if(user.getBloodGroup()=="A+ve")
                {
                    users1.add(user);
                }
            }
            for(User user:users1) {
                email=user.getEmail();
                sendNotification(msgBody,email);
            }
        }
        else if(view==ANegative)
        {
            String msgBody="Urgent requirement of A-ve Blood group";
            String email="";
            for (User user:users) {
                if(user.getBloodGroup()=="A-ve")
                {
                    users1.add(user);
                }
            }
            for(User user:users1) {
                email=user.getEmail();
                sendNotification(msgBody,email);
            }
        }
        else if(view==BPositive)
        {
            String msgBody="Urgent requirement of B+ve Blood group";
            String email="";
            for (User user:users) {
                if(user.getBloodGroup()=="B+ve")
                {
                    users1.add(user);
                }
            }
            for(User user:users1) {
                email=user.getEmail();
                sendNotification(msgBody,email);
            }
        }
        else if(view==BNegative)
        {
            String msgBody="Urgent requirement of B-ve Blood group";
            String email="";
            for (User user:users) {
                if(user.getBloodGroup()=="B-ve")
                {
                    users1.add(user);
                }
            }
            for(User user:users1) {
                email=user.getEmail();
                sendNotification(msgBody,email);
            }
        }
        else if(view==ABPositive)
        {
            String msgBody="Urgent requirement of AB+ve Blood group";
            String email="";
            for (User user:users) {
                if(user.getBloodGroup()=="AB+ve")
                {
                    users1.add(user);
                }
            }
            for(User user:users1) {
                email=user.getEmail();
                sendNotification(msgBody,email);
            }
        }
        else if(view==ABNegative)
        {
            String msgBody="Urgent requirement of AB-ve Blood group";
            String email="";
            for (User user:users) {
                if(user.getBloodGroup()=="AB-ve")
                {
                    users1.add(user);
                }
            }
            for(User user:users1) {
                email=user.getEmail();
                sendNotification(msgBody,email);
            }
        }
        else if(view==OPositive)
        {
            String msgBody="Urgent requirement of O+ve Blood group";
            String email="";
            for (User user:users) {
                if(user.getBloodGroup()=="O+ve")
                {
                    users1.add(user);
                }
            }
            for(User user:users1) {
                email=user.getEmail();
                sendNotification(msgBody,email);
            }
        }
        else if(view==ONegative)
        {
            String msgBody="Urgent requirement of O-ve Blood group";
            String email="";
            for (User user:users) {
                if(user.getBloodGroup()=="O-ve")
                {
                    users1.add(user);
                }
            }
            for(User user:users1) {
                email=user.getEmail();
                sendNotification(msgBody,email);
            }
        }
    }
}
