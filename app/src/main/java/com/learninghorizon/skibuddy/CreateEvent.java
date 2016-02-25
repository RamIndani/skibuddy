package com.learninghorizon.skibuddy;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class CreateEvent extends AppCompatActivity implements View.OnClickListener {

    Button nextButton;
    EditText nameEditText;
    EditText descriptionEditText;
    Button eventDatePicker;
    Button eventStartTimePicker;
    Button eventEndTimePicker;
    TextView eventDate;
    TextView eventStartTime;
    TextView eventEndTime;
    private int year;
    private int month;
    private int day;
    static final int DATE_DIALOG_ID = 999;
    static final int START_TIME_DIALOG_ID = 998;
    static final int END_TIME_DIALOG_ID = 997;
    private long selectedDate = new Date().getTime();

    private int hour;
    private int minute;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        nextButton = (Button) findViewById(R.id.event_next);
        nextButton.setOnClickListener(this);
        nameEditText = (EditText) findViewById(R.id.event_name);
        descriptionEditText = (EditText) findViewById(R.id.event_description);
        eventDatePicker = (Button) findViewById(R.id.select_date);
        eventStartTimePicker = (Button) findViewById(R.id.select_start_time);
        eventEndTimePicker = (Button) findViewById(R.id.select_end_time);

        eventDate = (TextView) findViewById(R.id.event_date);
        eventStartTime = (TextView) findViewById(R.id.event_start_time);
        eventEndTime = (TextView) findViewById(R.id.event_end_time);

        eventDatePicker.setOnClickListener(this);
        eventStartTimePicker.setOnClickListener(this);
        eventEndTimePicker.setOnClickListener(this);

        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        setCurrentDateAndTime();
    }

    private void setCurrentDateAndTime() {
        Calendar calendar = Calendar.getInstance();

        // set selected date into textview
        SimpleDateFormat formatter = new SimpleDateFormat("MMM d, yyyy");
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm");
        long currentDate =new  Date().getTime();
        eventDate.setText(formatter.format(currentDate));
        eventStartTime.setText(timeFormatter.format(currentDate));
        eventEndTime.setText(timeFormatter.format(currentDate));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.event_next:{
                String eventName = nameEditText.getText().toString();
                String eventDescription = descriptionEditText.getText().toString();
                //selectedDate check
                String eventStartTimeString = eventStartTime.getText().toString();
                String eventEndTImeString = eventEndTime.getText().toString();
                if(null == eventName || eventName.isEmpty()){
                    nameEditText.setError(getResources().getString(R.string.required_field));
                    return;
                }
                if(null == eventDescription || eventDescription.isEmpty()){
                    descriptionEditText.setError(getResources().getString(R.string.required_field));
                    return;
                }

                if(null == eventStartTimeString || eventStartTimeString.isEmpty()){
                    eventStartTime.setError(getResources().getString(R.string.required_field));
                    return;
                }

                if(null == eventEndTImeString || eventEndTImeString.isEmpty()){
                    eventEndTime.setError(getResources().getString(R.string.required_field));
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("eventName", eventName);
                bundle.putString("eventDescription", eventDescription);
                bundle.putLong("eventDate", selectedDate);
                bundle.putString("startTime", eventStartTimeString);
                bundle.putString("endTIme", eventEndTImeString);
                Intent usersListIntent = new Intent(getApplicationContext(),UsersList.class);
                usersListIntent.putExtras(bundle);
                startActivity(usersListIntent);
                finish();
                break;
            }
            case R.id.select_date:{
                showDialog(DATE_DIALOG_ID);
                break;
            }
            case R.id.select_start_time:{
                showDialog(START_TIME_DIALOG_ID);
                break;
            }
            case R.id.select_end_time:{
                showDialog(END_TIME_DIALOG_ID);
                break;
            }
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID: {
                // set date picker as current date
                return new DatePickerDialog(this, datePickerListener,
                        year, month, day);

            }
            case START_TIME_DIALOG_ID: {
                // set time picker as current time
                return new TimePickerDialog(this,
                        startTimePickerListener, hour, minute, false);
            }

            case END_TIME_DIALOG_ID: {
                // set time picker as current time
                return new TimePickerDialog(this,
                        endTimePickerListener, hour, minute, false);
            }
        }
        return null;
    }

    public static java.util.Date getDateFromDatePicker(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();
        Log.e("DatePICKER TEST : ", "Year " + year);
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(year, month, day);

        return calendar.getTime();
    }

    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            Calendar calendar = Calendar.getInstance();
            calendar.clear();
            calendar.set(year, month, day);
            // set selected date into textview
            SimpleDateFormat formatter = new SimpleDateFormat("MMM d, yyyy");
            selectedDate = calendar.getTime().getTime();
            eventDate.setText(formatter.format(calendar.getTime().getTime()));

            // set selected date into datepicker also
            //dpResult.init(year, month, day, null);

        }
    };

private TimePickerDialog.OnTimeSetListener startTimePickerListener =
        new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker view, int selectedHour,
                                  int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;

                // set current time into textview
                eventStartTime.setText(new StringBuilder().append(pad(hour))
                        .append(":").append(pad(minute)));


            }
        };

private TimePickerDialog.OnTimeSetListener endTimePickerListener =
        new TimePickerDialog.OnTimeSetListener() {
            public void onTimeSet(TimePicker view, int selectedHour,
                                  int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;

                // set current time into textview
                eventEndTime.setText(new StringBuilder().append(pad(hour))
                        .append(":").append(pad(minute)));


            }
        };

    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }
}
