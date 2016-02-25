package com.learninghorizon.skibuddy;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.learninghorizon.skibuddy.model.User;
import com.learninghorizon.skibuddy.utility.DataUtil;
import com.squareup.picasso.Picasso;


public class UserProfile extends AppCompatActivity {
    User user = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        String userId =null;
        final Bundle bundle = getIntent().getExtras();

        if(null != bundle){
            userId = bundle.getString("userId");
        }
        if(null != userId){
            for(User eventAttendie : DataUtil.getInstance().getEventAttendies()){
                if(eventAttendie.getFacebookId().equals(userId)){
                    user = eventAttendie;
                    break;
                }
            }
        }

        if(null != user){
            ImageView profilePic = (ImageView) findViewById(R.id.profile_pic);
            TextView userName = (TextView) findViewById(R.id.user_name);
            TextView location = (TextView) findViewById(R.id.location);
            TextView noOfSkiEvents = (TextView) findViewById(R.id.skirecords);
            Picasso.with(this)
                    .load("https://graph.facebook.com/" + userId + "/picture?height=240&width=240").into(profilePic);
            userName.setText(user.getFirstName().concat(" ").concat(user.getLastName()));

            if(user.isShareLocation()){
                String city = user.getLastKnownCity();
                String state = user.getLastKnownCity();
                if(null != city && null != state){
                    location.setText(user.getLastKnownCity().concat(", ").concat(user.getLastKnownState()));
                    location.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String latitude = user.getLastKnownLatitude();
                            String longitude = user.getLastKnownLongitude();
                            String userName = user.getFirstName() +" "+user.getLastName();
                            Bundle bundle = new Bundle();
                            bundle.putString("latitude",latitude);
                            bundle.putString("longitude", longitude);
                            bundle.putString("userName", userName);
                            Intent mapIntent = new Intent(getApplicationContext(), MapsActivity.class);
                            mapIntent.putExtras(bundle);
                            startActivity(mapIntent);

                        }
                    });
                }else{
                    location.setText("Location not available");
                }
            }else{
                location.setText("Location not shared");
            }
            noOfSkiEvents.setText(user.getSkiSessions().size()+" Ski Sessions");
            noOfSkiEvents.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view){
                    Intent skiRecords = new Intent(view.getContext(), AttendiesSkiSessions.class);
                    skiRecords.putExtras(bundle);
                    startActivity(skiRecords);
                }
            });

        }
    }

}
