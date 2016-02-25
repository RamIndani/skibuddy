package com.learninghorizon.skibuddy.tracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;


import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.learninghorizon.skibuddy.AsyncTasks.SaveLocation;
import com.learninghorizon.skibuddy.AsyncTasks.SaveSession;
import com.learninghorizon.skibuddy.R;
import com.learninghorizon.skibuddy.interfaces.SessionNameObserver;
import com.learninghorizon.skibuddy.model.SkiSession;
import com.learninghorizon.skibuddy.model.TravelPoint;
import com.learninghorizon.skibuddy.utility.DataUtil;
import com.learninghorizon.skibuddy.widget.SessionNameDialog;

import java.io.IOException;
import java.util.Date;
import java.util.Locale;

public class RunFragment extends Fragment implements SessionNameObserver {
    private static final String TAG = "RunFragment";
    final Handler handler = new Handler();
    Thread thread;
    Runnable runnable;
    SkiSession session = new SkiSession();
    double maxSpeed = -1;
    double averageSpeed = -1;
    double distance;
    double sumOfSpeed = 0;
    private BroadcastReceiver mLocationReceiver = new LocationReceiver() {

        @Override
        protected void onLocationReceived(Context context, Location loc) {
            mLastLocation = loc;
            if (isVisible())
                updateUI();
        }

        @Override
        protected void onProviderEnabledChanged(boolean enabled) {
            int toastText = enabled ? R.string.gps_enabled : R.string.gps_disabled;
            //Toast.makeText(getActivity(), toastText, Toast.LENGTH_LONG).show();
        }

    };

    private RunManager mRunManager;

    private Run mRun;
    private Location mLastLocation;

    private Button  mStopButton;
    private TextView mStartedTextView, mLatitudeTextView,
        mLongitudeTextView, mAltitudeTextView, mDurationTextView, mSpeedTextView, mStreetName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mRunManager = RunManager.get(getActivity());
        mRunManager.startLocationUpdates();
        mRun = new Run();


         runnable = new Runnable() {

            @Override
            public void run() {
                try{
                    updateTimer();
                   // handler.postDelayed(this, 1000);
                }
                catch (Exception e) {
                    // TODO: handle exception
                }
                finally{
                    //also call the same runnable
                    handler.postDelayed(this, 1000);
                }
            }
        };
         thread = new Thread(runnable);
        thread.start();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_run, container, false);

        mStartedTextView = (TextView)view.findViewById(R.id.run_startedTextView);
        mLatitudeTextView = (TextView)view.findViewById(R.id.run_latitudeTextView);
        mLongitudeTextView = (TextView)view.findViewById(R.id.run_longitudeTextView);
        mAltitudeTextView = (TextView)view.findViewById(R.id.run_altitudeTextView);
        mDurationTextView = (TextView)view.findViewById(R.id.run_durationTextView);
        mSpeedTextView = (TextView) view.findViewById(R.id.session_speed);
        mStreetName = (TextView) view.findViewById(R.id.street_name);

       /* mStartButton = (Button)view.findViewById(R.id.run_startButton);
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mRunManager.startLocationUpdates();

                updateUI();
            }
        });*/

        mStopButton = (Button)view.findViewById(R.id.run_stopButton);
        mStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRunManager.stopLocationUpdates();
                updateUI();
                try {
                    handler.removeCallbacks(thread);
                    handler.removeCallbacks(runnable);
                    saveSession();
                } catch (Exception exception) {

                }
            }
        });

        updateUI();

        return view;
    }

    private void saveSession(){
       // String sessionStart = mStartedTextView.getText().toString();
        String sessionLatitude = mLatitudeTextView.getText().toString();
        String sessionLongitude = mLongitudeTextView.getText().toString();
        String sessionAltitude = mAltitudeTextView.getText().toString();
        String sessionDuration = mDurationTextView.getText().toString();
        String sessionSpeed = mSpeedTextView.getText().toString();
        session.setDate(mRun.getStartDate().getTime());
        session.setAltitude(sessionAltitude);
        session.setDuration(sessionDuration);
        session.setAverageSpeed(String.valueOf(averageSpeed));
        session.setMaxSpeed(String.valueOf(maxSpeed));
        session.setDistance(String.valueOf(distance));
        //TODO: test this parameter
        DataUtil.getInstance().getUser().setSkiSession(session);
        SessionNameDialog sessionNameDialog = new SessionNameDialog();
        sessionNameDialog.show(getActivity().getFragmentManager(), "sessionName");
        sessionNameDialog.registerObserver(this);

    }


    @Override
    public void onStart() {
        super.onStart();
        getActivity().registerReceiver(mLocationReceiver,
                new IntentFilter(RunManager.ACTION_LOCATION));
    }

    @Override
    public void onStop() {
        getActivity().unregisterReceiver(mLocationReceiver);
        try {
            handler.removeCallbacks(thread);
            handler.removeCallbacks(runnable);
            if(mRunManager.isTrackingRun()){
                mRunManager.stopLocationUpdates();
                saveSession();

            }
        }catch (Exception exception){

        }
        super.onStop();
    }

    private void updateUI() {
        boolean started = mRunManager.isTrackingRun();

        if (mRun != null)
            mStartedTextView.setText(mRun.getStartDate().toString());

        //int durationSeconds = 0;
        if (mLastLocation != null) {

            Geocoder geocoder = new Geocoder(getActivity().getApplicationContext(), Locale.getDefault());
            try {
                Address usersAddress = (geocoder.getFromLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude(), 1)).get(0);
                String street = usersAddress.getThoroughfare();
                mStreetName.setText(street);
            } catch (IOException e) {
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
                float currentSpeed = mLastLocation.getSpeed();
            mSpeedTextView.setText(String.valueOf(currentSpeed));
           // durationSeconds = mRun.getDurationSeconds(new Date().getTime());
            mLatitudeTextView.setText(Double.toString(mLastLocation.getLatitude()));
            mLongitudeTextView.setText(Double.toString(mLastLocation.getLongitude()));
            mAltitudeTextView.setText(Double.toString(mLastLocation.getAltitude()));
            TravelPoint travelPoint = new TravelPoint(Double.toString(mLastLocation.getLatitude()),
                    Double.toString(mLastLocation.getLongitude()));
            session.setTravelPoint(travelPoint);
            if(currentSpeed > maxSpeed){
                maxSpeed = currentSpeed;
            }
            distance += 1;
            if(sumOfSpeed ==0 && currentSpeed >0){
                sumOfSpeed = currentSpeed;
            }else {
                sumOfSpeed += currentSpeed;
                averageSpeed = sumOfSpeed / session.getTravelPoints().size();
            }
        }


       // mStartButton.setEnabled(!started);
        mStopButton.setEnabled(started);
    }

    private void updateTimer(){
        int durationSeconds = mRun.getDurationSeconds(new Date().getTime());
        mDurationTextView.setText(Run.formatDuration(durationSeconds));

    }

    @Override
    public void updateData(String sessionName) {
        session.setSessionName(sessionName);
        new SaveSession(getActivity(), DataUtil.getInstance().getUser().getFacebookId()).execute(session);
        getActivity().finish();
    }
}
