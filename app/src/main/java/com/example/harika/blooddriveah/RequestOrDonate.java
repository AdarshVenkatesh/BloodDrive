package com.example.harika.blooddriveah;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class RequestOrDonate extends Fragment {


    public RequestOrDonate() {
        // Required empty public constructor
    }
    

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        final request_donate_OnClickListener mListener;
        try{
            mListener = (request_donate_OnClickListener) getContext();
        }
        catch(ClassCastException e){
            throw new ClassCastException("forget to implement onclick");
        }
        View v=inflater.inflate(R.layout.request_donate, container, false);
        Button request=(Button)v.findViewById(R.id.requestbutton);
        Button donate=(Button)v.findViewById(R.id.donatebutton);
        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.requestButtonClicked();
            }
        });
        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.donateButtonClicked();
            }
        });
        return v;
    }


    public interface request_donate_OnClickListener
    {
        public void requestButtonClicked();
        public void donateButtonClicked();
    }
}
