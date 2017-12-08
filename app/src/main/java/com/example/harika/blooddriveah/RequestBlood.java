package com.example.harika.blooddriveah;

import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.onesignal.OSNotification;
import com.onesignal.OSNotificationAction;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;

import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RequestBlood extends AppCompatActivity implements View.OnClickListener,OneSignal.NotificationOpenedHandler {
    final String send_email = "";
    List<User> users = new ArrayList<User>();
    List<User> users1 = new ArrayList<User>();
    DatabaseReference databaseReference;
    Context context1;
    Button APositive, ANegative, BPositive, BNegative, ABPositive, ABNegative, OPositive, ONegative;

    public RequestBlood(Context context) {
        context1 = context;
    }
   public  RequestBlood()
    {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_blood);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for (DataSnapshot child : children) {
                    User user = child.getValue(User.class);
                    users.add(user);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Intent notificationIntent = new Intent(getApplicationContext(), ChatBoxActivity.class);

        PendingIntent intent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, 0);

        APositive = (Button) findViewById(R.id.APositive);
        ANegative = (Button) findViewById(R.id.ANegative);
        BPositive = (Button) findViewById(R.id.BPositive);
        BNegative = (Button) findViewById(R.id.BNegative);
        ABPositive = (Button) findViewById(R.id.ABPositive);
        ABNegative = (Button) findViewById(R.id.ABNegative);
        OPositive = (Button) findViewById(R.id.OPositive);
        ONegative = (Button) findViewById(R.id.ONegative);
        APositive.setOnClickListener(this);
        ANegative.setOnClickListener(this);
        BPositive.setOnClickListener(this);
        BNegative.setOnClickListener(this);
        ABPositive.setOnClickListener(this);
        ABNegative.setOnClickListener(this);
        OPositive.setOnClickListener(this);
        ONegative.setOnClickListener(this);
    }


    private void sendNotification(final String msgBody, final String send_email) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                int SDK_INT = Build.VERSION.SDK_INT;
                if (SDK_INT > 8) {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    try {
                        String jsonResponse;
                        URL url = new URL("https://onesignal.com/api/v1/notifications");
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        con.setUseCaches(false);
                        con.setDoOutput(true);
                        con.setDoInput(true);

                        con.setRequestProperty("Content-Type", "application/json; charset=utf-8");
                        con.setRequestProperty("Authorization", "Basic NjIxMjA2YTUtNGU0NS00ZDMxLWIxOWEtZjkyZjc4ODlhNDdi");
                        con.setRequestMethod("POST");

                        String jsonBody = "{" +
                                "\"app_id\": \"c24a44f7-859b-4e24-aaee-d7428d3fe9c9\","
                                + "\"filters\": [{\"field\": \"tag\", \"key\": \"User_ID\", \"relation\": \"=\", \"value\": \"" + send_email + "\"}],"
                                // +"\"data\":{\"Users\":\"bar\"},"
                                + "\"contents\":{\"en\":\"" + msgBody + "\"}"
                                + "}";

                        System.out.println("String json is:" + jsonBody);

                        byte[] sendBytes = jsonBody.getBytes("UTF-8");
                        con.setFixedLengthStreamingMode(sendBytes.length);
                        OutputStream outputStream = con.getOutputStream();
                        outputStream.write(sendBytes);

                        int httpResponse = con.getResponseCode();
                        System.out.println("httpResponse" + httpResponse);

                        if (httpResponse >= HttpURLConnection.HTTP_OK && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
                            Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
                            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                            scanner.close();
                        } else {
                            Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
                            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                            scanner.close();
                        }
                        System.out.println("jsonResponse is:" + jsonResponse);

                    } catch (Throwable t) {
                        t.printStackTrace();
                    }

                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view == APositive) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("You sure you want to request for A +ve blood group")
                    .setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String msgBody = "Urgent requirement of A+ve Blood group.Please Help!!! Save life!!";
                    String email = "";
                    for (User user : users) {
                        if (user.getBloodGroup().equalsIgnoreCase("A+ve")) {
                            users1.add(user);
                        }
                    }
                    for (User user : users1) {
                        email = user.getEmail();
                        sendNotification(msgBody, email);
                    }
                    users1.clear();
                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            AlertDialog alertDialog = alert.create();
            alertDialog.setTitle("Request blood and save life!!!");
            alertDialog.show();


        } else if (view == ANegative) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("You sure you want to request for A -ve blood group")
                    .setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String msgBody = "Urgent requirement of A-ve Blood group.Please Help!!! Save life!!";
                    String email = "";
                    for (User user : users) {
                        if (user.getBloodGroup().equalsIgnoreCase("A-ve")) {
                            users1.add(user);
                        }
                    }
                    for (User user : users1) {
                        email = user.getEmail();
                        sendNotification(msgBody, email);
                    }
                    users1.clear();
                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            AlertDialog alertDialog = alert.create();
            alertDialog.setTitle("Request blood and save life!!!");
            alertDialog.show();

        } else if (view == BPositive) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("You sure you want to request for B +ve blood group")
                    .setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String msgBody = "Urgent requirement of B+ve Blood group.Please Help!!! Save life!!";
                    String email = "";
                    for (User user : users) {
                        if (user.getBloodGroup().equalsIgnoreCase("B+ve")) {
                            users1.add(user);
                        }
                    }
                    for (User user : users1) {
                        email = user.getEmail();
                        sendNotification(msgBody, email);
                    }
                    users1.clear();
                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            AlertDialog alertDialog = alert.create();
            alertDialog.setTitle("Request blood and save life!!!");
            alertDialog.show();

        } else if (view == BNegative) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("You sure you want to request for B-ve blood group")
                    .setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String msgBody = "Urgent requirement of B-ve Blood group.Please Help!!! Save life!!";
                    String email = "";
                    for (User user : users) {
                        if (user.getBloodGroup().equalsIgnoreCase("B-ve")) {
                            users1.add(user);
                        }
                    }
                    for (User user : users1) {
                        email = user.getEmail();
                        sendNotification(msgBody, email);
                    }
                    users1.clear();
                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            AlertDialog alertDialog = alert.create();
            alertDialog.setTitle("Request blood and save life!!!");
            alertDialog.show();

        } else if (view == ABPositive) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("You sure you want to request for AB+ve blood group")
                    .setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String msgBody = "Urgent requirement of AB+ve Blood group.Please Help!!! Save life!!";
                    String email = "";
                    for (User user : users) {
                        if (user.getBloodGroup().equalsIgnoreCase("AB+ve")) {
                            users1.add(user);
                        }
                    }
                    for (User user : users1) {
                        email = user.getEmail();
                        sendNotification(msgBody, email);
                    }
                    users1.clear();
                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            AlertDialog alertDialog = alert.create();
            alertDialog.setTitle("Request blood and save life!!!");
            alertDialog.show();

        } else if (view == ABNegative) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("You sure you want to request for AB-ve blood group")
                    .setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String msgBody = "Urgent requirement of B-ve Blood group.Please Help!!! Save life!!";
                    String email = "";
                    for (User user : users) {
                        if (user.getBloodGroup().equalsIgnoreCase("AB-ve")) {
                            users1.add(user);
                        }
                    }
                    for (User user : users1) {
                        email = user.getEmail();
                        sendNotification(msgBody, email);
                    }
                    users1.clear();
                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            AlertDialog alertDialog = alert.create();
            alertDialog.setTitle("Request blood and save life!!!");
            alertDialog.show();

        } else if (view == OPositive) {
            AlertDialog.Builder alert = new AlertDialog.Builder(RequestBlood.this);
            alert.setMessage("You sure you want to request for O+ve blood group. ")
                    .setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String msgBody = "Urgent requirement of O+ve Blood group.Please Help!!! Save life!!";
                    String email = "";
                    for (User user : users) {
                        if (user.getBloodGroup().equalsIgnoreCase("O+ve")) {
                            users1.add(user);
                        }
                    }
                    for (User user : users1) {
                        email = user.getEmail();
                        sendNotification(msgBody, email);
                    }
                    users1.clear();
                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            AlertDialog alertDialog = alert.create();
            alertDialog.setTitle("Request blood and save life!!!");
            alertDialog.show();
        } else if (view == ONegative) {
            AlertDialog.Builder alert = new AlertDialog.Builder(RequestBlood.this);
            alert.setMessage("You sure you want to request for O-ve blood group")
                    .setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String msgBody = "Urgent requirement of O-ve Blood group. Please Help!!! Save life!!";
                    String email = "";
                    for (User user : users) {
                        if (user.getBloodGroup().equalsIgnoreCase("O-ve")) {
                            users1.add(user);
                        }
                    }
                    for (User user : users1) {
                        email = user.getEmail();
                        sendNotification(msgBody, email);
                    }
                    users1.clear();
                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            AlertDialog alertDialog = alert.create();
            alertDialog.setTitle("Request blood and save life!!!");
            alertDialog.show();
        }
    }

    @Override
    public void notificationOpened(OSNotificationOpenResult result) {
        OSNotificationAction.ActionType actionType = result.action.type;
        JSONObject data = result.notification.payload.additionalData;
        String customKey;

        Intent intent = new Intent(context1, ChatBoxActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
        context1.startActivity(intent);


        if (data != null) {
            customKey = data.optString("customkey", null);
            if (customKey != null)
                Log.e("OneSignalExample", "customkey set with value: " + customKey);
        }

        if (actionType == OSNotificationAction.ActionType.ActionTaken) {
            Log.i("OneSignalExample", "Button pressed with id: " + result.action.actionID);


        }
    }


}
