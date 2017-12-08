package com.example.harika.blooddriveah;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.facebook.drawee.view.SimpleDraweeView;



import java.util.Collections;
import java.util.List;


public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.MyViewHolder> {
    List<NavDrawerItem> data = Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;
    Activity activity;

    public NavigationDrawerAdapter(Context context, List<NavDrawerItem> data, Activity activity) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.activity = activity;
    }

    public void delete(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.nav_drawer_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final NavDrawerItem current = data.get(position);
        holder.title.setText(current.getTitle());
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(current.getTitle().equals("SignOut"))
                {
                   /* ParseUser.getCurrentUser().logOutInBackground(new LogOutCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e == null) {
                                Digits.getSessionManager().clearActiveSession();
                                SaveUserSetOperations operations = SaveUserSetOperations.getInstance();
                                operations.resetLikedCelebs(context,"LIKED_CELEBS");
                                Intent intent = new Intent(context, RedirectUser.class);
                                context.startActivity(intent);
                            }
                            else
                            {
                                InitializeConfig.getInstance().trackException(e);
                            }
                        }
                    });*/
                }
                else if(current.getTitle().equals("Suggest Friend"))
                {
                    Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Are you a hardcore fan ? Get Connected Here !!");
                    shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Are you a hardcore fan? Get Connected Here !!. I'm using Fan Station for Android and I recommend it. Click here: https://play.google.com/store/apps/details?id=nayaone.developer.prabhukonchada.fanstation");

                    Intent chooserIntent = Intent.createChooser(shareIntent, "Share with");
                    chooserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(chooserIntent);
                }
                else {
                    if (current.getTitle().equals("Settings")) {
                        Toast.makeText(context, "Go to eventspage", Toast.LENGTH_LONG).show();

                    /*Intent settingsIntent = new Intent(context,UserSetting.class);
                    context.startActivity(settingsIntent);*/

                    } else if (current.getTitle().equals("My Questions")) {
                        Toast.makeText(context, "Go to eventspage", Toast.LENGTH_LONG).show();
                        // loadQuestionsAsked();
                    } else if (current.getTitle().equals("My Discussions")) {
                        Toast.makeText(context, "Go to eventspage", Toast.LENGTH_LONG).show();
                        // loadUserDiscussions();
                    }
                }
//                else if(current.getTitle().equals("Leader Boards")){
//                    context.startActivity(new Intent(context,LeaderBoardsActivity.class));
//                }
//                else if(current.getTitle().equals("set Wallpaper")){
//                    WallpaperManager myWallpaperManager
//                            = WallpaperManager.getInstance(context);
//                    try {
//                        myWallpaperManager.setResource(R.drawable.app_small_icon);
//                    } catch (IOException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
//                }
            }
        });

    }

    private void setSnackBar(String message)
    {
        Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
                .setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //call function
                    }
                })
                .setActionTextColor(Color.YELLOW);
        snackbar.show();
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(context.getResources().getColor(R.color.black_semi_transparent));
    }



    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        SimpleDraweeView icon;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            icon = (SimpleDraweeView) itemView.findViewById(R.id.navigationBarIcon);
        }
    }
}