package com.example.harika.blooddriveah;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by adars on 12/30/2017.
 */

public class PrefManager {
    SharedPreferences pref;             // To see the this slider again, goto Settings -> apps -> this app -> clear data
    SharedPreferences.Editor editor;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "Welcome";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    private static final String IS_REGISTERED="isRegistered";

    private static final String EMAIL="email";

    private static final String PASSWORD="password";

    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public void setIsRegistered(boolean isRegistered){
        editor.putBoolean(IS_REGISTERED,isRegistered);
        editor.commit();
    }

    public void setEmail(String email){
        editor.putString(EMAIL,email);
        editor.commit();
    }

    public void setPassword(String password){
        editor.putString(PASSWORD,password);
        editor.commit();
    }

    public boolean isRegistered(){
        return pref.getBoolean(IS_REGISTERED,true);
    }


    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public String emailIs(){
        return pref.getString(EMAIL,"");
    }
    public String passwordIs(){
        return pref.getString(PASSWORD,"");
    }

}
