package com.learninghorizon.skibuddy.viewholders;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.learninghorizon.skibuddy.MapsActivity;
import com.learninghorizon.skibuddy.R;

/**
 * Created by ramnivasindani on 11/24/15.
 */
public  class SessionViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
    private  TextView sName;
    private  TextView sDistance;
    private  TextView sSessionDate;
    private  TextView sSessionAverageSpeed;
    private  TextView sSessionMaxSpeed;
    private  TextView sSessionDuration;
    private TextView sSessionId;

    public SessionViewHolder(View v) {
        super(v);
        v.setOnClickListener(this);
        sName =  (TextView) v.findViewById(R.id.session_title);
        sDistance = (TextView)  v.findViewById(R.id.session_distance);
        sSessionDate = (TextView)  v.findViewById(R.id.session_date);
        sSessionAverageSpeed = (TextView) v.findViewById(R.id.session_average_speed);
        sSessionMaxSpeed = (TextView) v.findViewById(R.id.max_speed);
        sSessionDuration = (TextView) v.findViewById(R.id.duration);
        sSessionId = (TextView) v.findViewById(R.id.sessionid);
    }

    @Override
    public void onClick(View v){
       // Toast.makeText(v.getContext(), "Clicked :" +getLayoutPosition(), Toast.LENGTH_LONG).show();
        Bundle bundle = new Bundle();
        bundle.putInt("sessionRecordLocation", getLayoutPosition());
        Intent mapsIntent = new Intent(v.getContext(), MapsActivity.class);
        mapsIntent.putExtras(bundle);
        v.getContext().startActivity(mapsIntent);
    }

    public TextView getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName.setText(sName);
    }

    public TextView getsDistance() {
        return sDistance;
    }

    public void setsDistance(String sDistance) {
        this.sDistance.setText(sDistance);
    }

    public TextView getsSessionDate() {
        return sSessionDate;
    }

    public void setsSessionDate(String sSessionDate) {
        this.sSessionDate.setText(sSessionDate);
    }

    public TextView getsSessionAverageSpeed() {
        return sSessionAverageSpeed;
    }

    public void setsSessionAverageSpeed(String sSessionAverageSpeed) {
        this.sSessionAverageSpeed.setText(sSessionAverageSpeed);
    }

    public TextView getsSessionMaxSpeed() {
        return sSessionMaxSpeed;
    }

    public void setsSessionMaxSpeed(String sSessionMaxSpeed) {
        this.sSessionMaxSpeed.setText(sSessionMaxSpeed);
    }

    public TextView getsSessionDuration() {
        return sSessionDuration;
    }

    public void setsSessionDuration(String sSessionDuration) {
        this.sSessionDuration.setText(sSessionDuration);
    }

    public TextView getsSessionId() {
        return sSessionId;
    }

    public void setsSessionId(String sSessionId) {
        this.sSessionId.setText(sSessionId);
    }
}