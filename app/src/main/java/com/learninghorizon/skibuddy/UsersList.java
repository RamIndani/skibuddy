package com.learninghorizon.skibuddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.learninghorizon.skibuddy.AsyncTasks.CreateSkiEvent;
import com.learninghorizon.skibuddy.AsyncTasks.LoadUsers;
import com.learninghorizon.skibuddy.adpaters.UsersListAdapter;
import com.learninghorizon.skibuddy.model.SkiEvent;
import com.learninghorizon.skibuddy.model.User;
import com.learninghorizon.skibuddy.utility.DataUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by ramnivasindani on 11/29/15.
 */
public class UsersList extends AppCompatActivity {

    //private Toolbar toolbar;
    private String eventName;
    private String eventDescription;
    private long eventDate;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String eventStartTime;
    private String eventEndTime;

    //private List<User> studentList = new ArrayList<User>();

    private Button btnSelection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //toolbar = (Toolbar) findViewById(R.id.toolbar);
        btnSelection = (Button) findViewById(R.id.btnShow);

        Bundle bundle = getIntent().getExtras();
        if(null != bundle){
            eventName = bundle.getString("eventName");
            eventDescription = bundle.getString("eventDescription");
            eventDate = bundle.getLong("eventDate");
            eventStartTime =  bundle.getString("startTime");
            eventEndTime = bundle.getString("endTIme");
        }
        mAdapter = new UsersListAdapter(DataUtil.getInstance().getUsersList(),getApplicationContext());
       // try {
             new LoadUsers(this, (UsersListAdapter) mAdapter).execute(DataUtil.getInstance().getUser().getFacebookId());
        /*} catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }*/
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // create an Object for Adapter


        // set the adapter object to the Recyclerview
        mRecyclerView.setAdapter(mAdapter);
        final List<String> eventInvites = new ArrayList<String>();
        btnSelection.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String data = "";
                List<User> stList = ((UsersListAdapter) mAdapter)
                        .getStudentist();

                for (int i = 0; i < stList.size(); i++) {
                    User singleStudent = stList.get(i);
                    if (singleStudent.getSelected() == true) {

                        data = data + "\n" + singleStudent.getFacebookId().toString();
                        eventInvites.add(singleStudent.getFacebookId().toString());
      /*
       * Toast.makeText( CardViewActivity.this, " " +
       * singleStudent.getName() + " " +
       * singleStudent.getEmailId() + " " +
       * singleStudent.isSelected(),
       * Toast.LENGTH_SHORT).show();
       */
                    }


                }
                eventInvites.add(DataUtil.getInstance().getUser().getFacebookId());
                SkiEvent skiEvent = new SkiEvent();
                skiEvent.setEventName(eventName);
                skiEvent.setEventDescription(eventDescription);
                skiEvent.setDate(eventDate);
                skiEvent.setStartTime(eventStartTime);
                skiEvent.setEndTime(eventEndTime);
                skiEvent.setEventInvites(eventInvites);
                /*Toast.makeText(UsersList.this,
                        "Selected Students: \n" + data, Toast.LENGTH_LONG)
                        .show();*/

                new CreateSkiEvent(getApplicationContext()).execute(skiEvent);
                finish();
            }
        });

    }

}