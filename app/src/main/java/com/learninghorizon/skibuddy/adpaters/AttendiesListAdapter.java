package com.learninghorizon.skibuddy.adpaters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.learninghorizon.skibuddy.R;
import com.learninghorizon.skibuddy.UserProfile;
import com.learninghorizon.skibuddy.model.User;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ramnivasindani on 11/30/15.
 */
public class AttendiesListAdapter extends
        RecyclerView.Adapter<AttendiesListAdapter.ViewHolder> {

    private List<User> stList;
    private Context context;
    private static final String TAG = "AttendiesListAdapter";

    public AttendiesListAdapter(List<User> students, Context context) {
        this.stList = students;
        this.context = context;

    }

    // Create new views
    @Override
    public AttendiesListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.attendies_card, null);

        // create ViewHolder

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        final int pos = position;

        viewHolder.tvName.setText(stList.get(position).getFirstName() + " " + stList.get(position).getLastName());
        String fbProfileURL = "https://graph.facebook.com/" + stList.get(position).getFacebookId()+"/picture?type=square";
        Log.e(TAG, "fbProfileURL : " + fbProfileURL);
        Picasso.with(context)
                .load("https://graph.facebook.com/" + stList.get(position).getFacebookId()+"/picture?type=square").into(viewHolder.profilePic);
        //viewHolder.tvEmailId.setText(stList.get(position).getEmailId());

        //viewHolder.chkSelected.setChecked(stList.get(position).isSelected());

        //viewHolder.chkSelected.setTag(stList.get(position));
        viewHolder.userId.setText(stList.get(position).getFacebookId());


       /* viewHolder.chkSelected.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                User user = (User) cb.getTag();

                user.setSelected(cb.isChecked());
                stList.get(pos).setSelected(cb.isChecked());

                Toast.makeText(
                        v.getContext(),
                        "Clicked on Checkbox: " + cb.getText() + " is "
                                + cb.isChecked(), Toast.LENGTH_LONG).show();
            }
        });*/

    }

    // Return the size arraylist
    @Override
    public int getItemCount() {
        return stList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView tvName;
        // public TextView tvEmailId;

       // public CheckBox chkSelected;
        public ImageView profilePic;
        public User singlestudent;
        public TextView userId;
        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            itemLayoutView.setOnClickListener(this);
            tvName = (TextView) itemLayoutView.findViewById(R.id.tvName);
            profilePic = (ImageView) itemLayoutView.findViewById(R.id.profile);
            //tvEmailId = (TextView) itemLayoutView.findViewById(R.id.tvEmailId);
           // chkSelected = (CheckBox) itemLayoutView
             //       .findViewById(R.id.chkSelected);
            userId = (TextView) itemLayoutView.findViewById(R.id.user_id);

        }

        @Override
        public void onClick(View v) {
            String facebookUserId = ((TextView)v.findViewById(R.id.user_id)).getText().toString();
            Intent usersDetailedView = new Intent(v.getContext(), UserProfile.class);
            Bundle bundle = new Bundle();
            bundle.putString("userId", facebookUserId);
            usersDetailedView.putExtras(bundle);
            v.getContext().startActivity(usersDetailedView);
        }
    }
    // method to access in activity after updating selection
    public List<User> getStudentist() {
        return stList;
    }

}
