package com.learninghorizon.skibuddy.viewholders;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.learninghorizon.skibuddy.EventAttendies;
import com.learninghorizon.skibuddy.R;
import com.learninghorizon.skibuddy.model.SkiEvent;
import com.learninghorizon.skibuddy.utility.DataUtil;

import java.util.List;

/**
 * Created by ramnivasindani on 11/24/15.
 */
public  class EventViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
    private  TextView eName;
    private  TextView eDescription;
    private  TextView sEventDate;
    private TextView eventStartTime;
    private TextView eventEndTime;

    public EventViewHolder(View v) {
        super(v);
        v.setOnClickListener(this);
        eName =  (TextView) v.findViewById(R.id.event_title);
        eDescription = (TextView)  v.findViewById(R.id.event_description);
        sEventDate = (TextView)  v.findViewById(R.id.event_date);
        eventStartTime = (TextView) v.findViewById(R.id.event_start_time);
        eventEndTime = (TextView) v.findViewById(R.id.event_end_time);
    }

    @Override
    public void onClick(View v){

        Intent attendiesIntent = new Intent(v.getContext(), EventAttendies.class);
        Bundle bundle = new Bundle();
        List<SkiEvent> allEvent = DataUtil.getInstance().getTotalEvents();
        SkiEvent skiEvent = allEvent.get(getLayoutPosition());
        if(null != skiEvent) {

            bundle.putInt("eventPosition", getLayoutPosition());
            attendiesIntent.putExtras(bundle);
            v.getContext().startActivity(attendiesIntent);
        }else{
            Toast.makeText(v.getContext(), "Event can not be opened, try again later", Toast.LENGTH_LONG).show();
        }
    }

    public TextView geteName() {
        return eName;
    }

    public void seteName(String eName) {
        this.eName.setText(eName);
    }

    public TextView geteDescription() {
        return eDescription;
    }

    public void seteDescription(String eDescription) {
        this.eDescription.setText(eDescription);
    }

    public TextView getsEventDate() {
        return sEventDate;
    }

    public void setsEventDate(String sEventDate) {
        this.sEventDate.setText(sEventDate);
    }

    public void setEventStartTime(String eventStartTime) {
        this.eventStartTime.setText(eventStartTime);
    }

    public void setEventEndTime(String eventEndTime) {
        this.eventEndTime.setText(eventEndTime);
    }
}