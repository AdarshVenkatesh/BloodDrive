package com.example.harika.blooddriveah;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by adars on 11/30/2017.
 */

public class UserProfileContentFragment extends Fragment {

    private static User user1;

    public static UserProfileContentFragment newInstance(User user) {
        UserProfileContentFragment fragment = new UserProfileContentFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        user1 = user;
        return fragment;
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        Log.d("in content",user1.getBloodGroup());
        View rootView = inflater.inflate(R.layout.profile_content,container,false);
        final TextView name=(TextView)rootView.findViewById(R.id.profile_name);
        final TextView email=(TextView)rootView.findViewById(R.id.profile_email);
        final TextView bloodGroup=(TextView)rootView.findViewById(R.id.profile_bloodgroup);
        final TextView contactNumber=(TextView)rootView.findViewById(R.id.profile_contactnumber);
        name.setText(user1.getFirstName());
        email.setText(user1.getEmail());
        bloodGroup.setText(user1.getBloodGroup());
        contactNumber.setText(user1.getContactNumber());
        return rootView;
    }
}
