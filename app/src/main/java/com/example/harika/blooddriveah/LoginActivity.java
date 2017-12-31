package com.example.harika.blooddriveah;

import android.app.AlertDialog;
import android.app.KeyguardManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
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

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText userName;
    EditText passWord;
    Button login;
    Button signup;
    ProgressDialog processDialog;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    private KeyStore keyStore;
    private static final String KEY_NAME="Adarsh";
    private Cipher cipher;

    private PrefManager prefManager;
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

        prefManager = new PrefManager(this);
//  This is for the first time welcome intro!!
        if (!prefManager.isRegistered()) {
            fingerprintAuth();
        //    finish();
        }
/*
        if(firebaseAuth.getCurrentUser()!=null)
        {
            finish();
            startActivity(new Intent(getApplicationContext(),NavigationDrawer.class));

            user=firebaseAuth.getCurrentUser();
             LoggedIn_User_Mail=user.getEmail();
            OneSignal.sendTag("User_ID",LoggedIn_User_Mail);
        }
*/


        userName = (EditText) findViewById(R.id.edit_text_email);
        passWord = (EditText) findViewById(R.id.edit_text_password);
        login = (Button) findViewById(R.id.login);
        signup=(Button)findViewById(R.id.signup);
        login.setOnClickListener(this);
        signup.setOnClickListener(this);
        processDialog=new ProgressDialog(this);
    }

    private void fingerprintAuth() {

        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.fingerprint_dialog_content, null);

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Info");
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getBaseContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog dialog = alert.create();
        dialog.show();


        KeyguardManager keyguardManager=(KeyguardManager)getSystemService(KEYGUARD_SERVICE);

        FingerprintManager fingerprintManager=(FingerprintManager)getSystemService(FINGERPRINT_SERVICE);

        if(!fingerprintManager.isHardwareDetected())
        {
            Toast.makeText(this, "Fingerprint authentication permission not enabled", Toast.LENGTH_SHORT).show();
        }
        else
        {
            if(!fingerprintManager.hasEnrolledFingerprints())
                Toast.makeText(this, "enroll atleast one fingerprint in settings", Toast.LENGTH_SHORT).show();
            else{
                if(!keyguardManager.isKeyguardSecure())
                    Toast.makeText(this, "Lock screen security not enabled in settings", Toast.LENGTH_SHORT).show();
                else
                    genKey();
                if(cipherInit())
                {
                    FingerprintManager.CryptoObject cryptoObject=new FingerprintManager.CryptoObject(cipher);
                    FingerPrintHandler helper=new FingerPrintHandler(this);
                    helper.startAuthentication(fingerprintManager,cryptoObject);
                }
            }
        }

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


    private boolean cipherInit() {
        try {
            cipher= Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES+"/"+KeyProperties.BLOCK_MODE_CBC+"/"+KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
        try {
            keyStore.load(null);
            SecretKey key=(SecretKey) keyStore.getKey(KEY_NAME,null);
            cipher.init(Cipher.ENCRYPT_MODE,key);
            return true;
        } catch (IOException e1) {
            e1.printStackTrace();
            return false;
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
            return false;
        } catch (CertificateException e1) {
            e1.printStackTrace();
            return false;
        } catch (UnrecoverableKeyException e1) {
            e1.printStackTrace();
            return false;
        } catch (KeyStoreException e1) {
            e1.printStackTrace();
            return false;
        } catch (InvalidKeyException e1) {
            e1.printStackTrace();
            return false;
        }


    }

    private void genKey() {

        try {
            keyStore= KeyStore.getInstance("AndroidKeyStore");
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }

        KeyGenerator keyGenerator = null;
        try {
            keyGenerator=KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES,"AndroidKeyStore");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }

        try {
            keyStore.load(null);
            keyGenerator.init(new KeyGenParameterSpec.Builder(KEY_NAME,KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT).setBlockModes(KeyProperties.BLOCK_MODE_CBC).setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true).setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());
            keyGenerator.generateKey();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        catch(InvalidAlgorithmParameterException e){
            e.printStackTrace();
        }


    }
}