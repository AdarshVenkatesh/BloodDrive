package com.example.harika.blooddriveah;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

/**
 * Created by adars on 12/4/2017.
 */

public class EventsFirebaseRecyclerAdapter extends FirebaseRecyclerAdapter<Event, EventsFirebaseRecyclerAdapter.EventViewHolder>  {


    private Context mContext;
    static RecycleItemClickListener onItemClickListener;
    public EventsFirebaseRecyclerAdapter(Class<Event> modelClass, int modelLayout,
                                     Class<EventViewHolder> holder, DatabaseReference ref, Context context) {
        super(modelClass, modelLayout, holder, ref);
        this.mContext = context;
    }
    @Override
    protected void populateViewHolder(EventViewHolder eventViewHolder, Event event, int i) {

        //TODO: Populate viewHolder by setting the movie attributes to cardview fields
        eventViewHolder.eventDescription.setText(event.getDescription());
        eventViewHolder.eventCaption.setText(event.getCaption());
        String picture=(String)event.getPicture();

        Picasso.with(mContext)
                .load(picture)
                .into(eventViewHolder.eventPicture);

    }

    public void setOnItemClickListener(final RecycleItemClickListener mItemClickListener) {
        this.onItemClickListener = mItemClickListener;
    }

    public interface RecycleItemClickListener {
        public void onItemClick(View view, int position);

    }

    //TODO: Populate ViewHolder and add listeners.
    public static class EventViewHolder extends RecyclerView.ViewHolder {
        public ImageView eventPicture;
        public TextView eventDescription;
        public TextView eventCaption;

        public EventViewHolder(View view) {
            super(view);
            eventPicture = (ImageView)
                    view.findViewById(R.id.eventPicture);
            eventDescription = (TextView)
                    view.findViewById(R.id.eventDescription);

            eventCaption=(TextView)view.findViewById(R.id.title_caption);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                            onItemClickListener.onItemClick(v, getAdapterPosition());
                        }
                    }
                }
            });

        }
    }


}
