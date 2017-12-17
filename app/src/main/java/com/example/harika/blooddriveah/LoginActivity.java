package com.example.harika.blooddriveah;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
/*import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;*/
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.onesignal.OneSignal;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText userName;
    EditText passWord;
    Button login;
    Button signup;
    ProgressDialog processDialog;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    static String LoggedIn_User_Mail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
              //  .setNotificationOpenedHandler(new RequestBlood(this))
                .init();


        firebaseAuth=FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser()!=null)
        {
            finish();
            startActivity(new Intent(getApplicationContext(),NavigationDrawer.class));

            user=firebaseAuth.getCurrentUser();
             LoggedIn_User_Mail=user.getEmail();
            OneSignal.sendTag("User_ID",LoggedIn_User_Mail);
        }



        userName = (EditText) findViewById(R.id.edit_text_email);
        passWord = (EditText) findViewById(R.id.edit_text_password);
        login = (Button) findViewById(R.id.login);
        signup=(Button)findViewById(R.id.signup);
        login.setOnClickListener(this);
        signup.setOnClickListener(this);
        processDialog=new ProgressDialog(this);
    }

    @Override
    public void onClick(View v) {

        if(v==signup)
        {
            Intent myIntent = new Intent(LoginActivity.this, RegisterActivity.class);
            LoginActivity.this.startActivity(myIntent);
        }
        if(v==login)
        {
            String email=userName.getText().toString();
            String password=passWord.getText().toString();
            if(TextUtils.isEmpty(email)) {
                Toast.makeText(this, "Email is empty", Toast.LENGTH_SHORT).show();
                return;
            }
            if(TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Password is empty", Toast.LENGTH_SHORT).show();
                return;
            }
            processDialog.setMessage("Logging in");
            processDialog.show();
            Log.d("ref is:",firebaseAuth.toString());
            firebaseAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            processDialog.dismiss();
                            if(task.isSuccessful())
                            {
                                finish();
                                OneSignal.sendTag("User_ID",userName.getText().toString());
                                startActivity(new Intent(getApplicationContext(),NavigationDrawer.class));
                            }
                        }
                    });

        }
    }
}