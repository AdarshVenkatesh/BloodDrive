package com.example.harika.blooddriveah;

import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.HashMap;

/**
 * Created by adars on 12/7/2017.
 */

public class EventDetailFragment extends Fragment {

    TextView caption;
    ImageView image;
    TextView description;
    private static final String ARG_EVENT = "Event";
    private HashMap<String, ?> event;
    public static EventDetailFragment newInstance(HashMap<String, ?> event)
    {
        EventDetailFragment fragment = new EventDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_EVENT, event);
        fragment.setArguments(args);
        return fragment;
    }
    public EventDetailFragment()
    {

    }


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null)
        {
            event = (HashMap<String, ?> )getArguments().getSerializable(ARG_EVENT);
        }
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {

        View rootView = inflater.inflate(R.layout.event_detail_fragment, container, false);
        caption=(TextView)rootView.findViewById(R.id.title_detail_caption);
        image=(ImageView)rootView.findViewById(R.id.event_detail_Picture);
        description=(TextView) rootView.findViewById(R.id.event_detail_Description);

        caption.setText(event.get("caption").toString());
        description.setText(event.get("description").toString());
        String url=event.get("picture").toString();
        Picasso.with (getContext()). load (url). into (image);
        image.setTransitionName((String)event.get("id"));
        return rootView;
    }

}
