package com.learninghorizon.skibuddy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.learninghorizon.skibuddy.AsyncTasks.LoadEventAttendies;
import com.learninghorizon.skibuddy.adpaters.AttendiesListAdapter;
import com.learninghorizon.skibuddy.model.SkiEvent;
import com.learninghorizon.skibuddy.model.User;
import com.learninghorizon.skibuddy.utility.DataUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


/**
 * Created by ramnivasindani on 11/29/15.
 */
public class EventAttendies extends AppCompatActivity {

    //private Toolbar toolbar;
    private String eventName;
    private String eventDescription;
    private long eventDate;
    private SkiEvent skiEvent;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    //List<User> eventAttendies;


   // private Button btnSelection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_attendies);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //toolbar = (Toolbar) findViewById(R.id.toolbar);
        //btnSelection = (Button) findViewById(R.id.btnShow);

        Bundle bundle = getIntent().getExtras();
        if(null != bundle){
            int eventPosition = bundle.getInt("eventPosition");
            Log.e("EVENT POSIITION", eventPosition+"");
             skiEvent = DataUtil.getInstance().getTotalEvents().get(eventPosition);
            if(null != skiEvent) {
                eventName = bundle.getString("eventName");
                eventDescription = bundle.getString("eventDescription");
                eventDate = bundle.getLong("eventDate");
            }
        }
        mAdapter = new AttendiesListAdapter(DataUtil.getInstance().getEventAttendies(),getApplicationContext());


        new LoadEventAttendies(this,skiEvent.getEventInvites(), (AttendiesListAdapter) mAdapter).execute(DataUtil.getInstance().getUser().getFacebookId());



        mRecyclerView = (RecyclerView) findViewById(R.id.event_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // create an Object for Adapter


        // set the adapter object to the Recyclerview
        mRecyclerView.setAdapter(mAdapter);
        final List<String> eventInvites = new ArrayList<String>();

    }

}