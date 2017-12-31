package com.example.harika.blooddriveah;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.onesignal.OneSignal;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText firstName,lastName,email,password,retypepassword,contactNumber;
    private String s_fname,s_lname,s_email,s_password,s_retypepassword,s_bloodGroup,s_contactNumber;
    Spinner bloodGroup;
    FirebaseAuth mAuth;
    Button signMeUp;
    private PrefManager prefManager;
    FirebaseAuth.AuthStateListener mAuthListener;
    UserData userdata=new UserData();
    User user =new User();
    private static final int RC_SIGN_IN = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        firstName=(EditText)findViewById(R.id.edit_text_firstName);
        lastName=(EditText)findViewById(R.id.edit_text_lastName);
        email=(EditText)findViewById(R.id.edit_text_email);
        password=(EditText)findViewById(R.id.edit_text_password);
        retypepassword=(EditText)findViewById(R.id.edit_text_retyppepassword);
        bloodGroup=(Spinner)findViewById(R.id.edit_text_bloodgroup);
        ArrayAdapter<String> groupAdapter = new ArrayAdapter<String>(RegisterActivity.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.blood_groups));
        groupAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bloodGroup.setAdapter(groupAdapter);
        contactNumber=(EditText)findViewById(R.id.edit_text_contactnumber);
        signMeUp=(Button)findViewById(R.id.signmeup);
        signMeUp.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        Log.d("in on click","");

        if(v==signMeUp)
        {
            register();
        }
    }

    public void register()
    {
        initialize();
        if(!validate())
        {
            Toast.makeText(this, "Sign up failed", Toast.LENGTH_SHORT).show();
        }
        else
        {
            signUpSuccess();
        }
    }
    public void signUpSuccess()
    {
        //Toast.makeText(this, s_email, Toast.LENGTH_SHORT).show();
        // Toast.makeText(this, mAuth.toString(), Toast.LENGTH_SHORT).show();
        mAuth.createUserWithEmailAndPassword(s_email,s_password)
                .addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "User registered Successfully", Toast.LENGTH_SHORT).show();
                            OneSignal.sendTag("User_ID",s_email);
                            startActivity(new Intent(getApplicationContext(),NavigationDrawer.class));
                        }else{
                            Toast.makeText(RegisterActivity.this, "Failed to register user", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                //  .setNotificationOpenedHandler(new RequestBlood(this))
                .init();
        HashMap<String,String> user=new HashMap<String, String>();
        user.put("firstName",s_fname);
        user.put("lastname",s_lname);
        user.put("email",s_email);
        user.put("password",s_password);
        user.put("bloodGroup",s_bloodGroup);
        user.put("contactNumber",s_contactNumber);

        userdata.addItemToServer(user);
        prefManager = new PrefManager(this);
        prefManager.setIsRegistered(false);
        prefManager.setEmail(s_email);
        prefManager.setPassword(s_password);
    }

    public boolean validate()
    {
        if (TextUtils.isEmpty(s_fname))
        {
            Toast.makeText(getApplicationContext(), "Enter First Name",Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(s_email))
        {
            Toast.makeText(getApplicationContext(), "Enter email address",Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(s_password))
        {
            Toast.makeText(getApplicationContext(), "Enter password", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!s_password.equalsIgnoreCase(s_retypepassword))
        {
            Toast.makeText(getApplicationContext(), "Passwords doesn't match", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(s_bloodGroup))
        {
            Toast.makeText(getApplicationContext(), "Enter Blood Group", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(s_contactNumber))
        {
            Toast.makeText(getApplicationContext(), "Enter Contact Number", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    public void initialize()
    {
        s_fname=firstName.getText().toString().trim();
        s_lname=lastName.getText().toString().trim();
        s_email=email.getText().toString().trim();
        s_password=password.getText().toString().trim();
        s_retypepassword=retypepassword.getText().toString().trim();
        s_bloodGroup=bloodGroup.getSelectedItem().toString().trim();
        //Log.d("bg is",s_bloodGroup);
        s_contactNumber=contactNumber.getText().toString().trim();
    }

}
