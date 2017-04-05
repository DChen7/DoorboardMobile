package com.doorboard3.doorboardmobile;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddScheduleActivity extends AppCompatActivity
        implements DatePickerDialog.OnDateSetListener {

    private EditText eventName;
    private EditText description;
    private TextView date;
    private TextView startTime;
    private TextView endTime;
    private RadioGroup roomGroup;
    private LinearLayout repeatGroup;
    private DoorboardDbHelper dbHelper;
    private int month;
    private int day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);

        getSupportActionBar().setTitle("Add an event");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbHelper = new DoorboardDbHelper(this);

        eventName = (EditText) findViewById(R.id.edit_event_name);
        description = (EditText) findViewById(R.id.edit_event_description);

        date = (TextView) findViewById(R.id.pick_date);
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMMM dd", Locale.US);
        Calendar c = Calendar.getInstance();
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        date.setText(sdf.format(Calendar.getInstance().getTime()));
        final DatePickerDialog datePickerDialog = new DatePickerDialog(
                this, AddScheduleActivity.this, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        LinearLayout datePickerLayout = (LinearLayout) findViewById(R.id.pick_date_layout);
        datePickerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        startTime = (TextView) findViewById(R.id.pick_start);
        startTime.setText(c.get(Calendar.HOUR) + ":00 " + (c.get(Calendar.AM_PM) == Calendar.AM ? "AM" : "PM"));
        final TimePickerDialog startTimeDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String amPm = "AM";
                if (hourOfDay == 0) {
                    hourOfDay = 12;
                } else if (hourOfDay > 12) {
                    hourOfDay -= 12;
                    amPm = "PM";
                }
                startTime.setText(hourOfDay + ":" + minute + " " + amPm);
            }
        }, c.get(Calendar.HOUR), c.get(Calendar.MINUTE), false);
        LinearLayout startTimeLayout = (LinearLayout) findViewById(R.id.pick_start_layout);
        startTimeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimeDialog.show();
            }
        });

        endTime = (TextView) findViewById(R.id.pick_end);
        endTime.setText(c.get(Calendar.HOUR) + ":30 " + (c.get(Calendar.AM_PM) == Calendar.AM ? "AM" : "PM"));
        final TimePickerDialog endTimeDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String amPm = "AM";
                if (hourOfDay == 0) {
                    hourOfDay = 12;
                } else if (hourOfDay > 12) {
                    hourOfDay -= 12;
                    amPm = "PM";
                }
                endTime.setText(hourOfDay + ":" + minute + " " + amPm);
            }
        }, c.get(Calendar.HOUR), c.get(Calendar.MINUTE), false);
        LinearLayout endTimeLayout = (LinearLayout) findViewById(R.id.pick_end_layout);
        endTimeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endTimeDialog.show();
            }
        });

        roomGroup = (RadioGroup) findViewById(R.id.room_group);
        repeatGroup = (LinearLayout) findViewById(R.id.repeat_group);

        repeatGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(), "This feature is still under development", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        Button saveButton = (Button) findViewById(R.id.save_event_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.saveEvent(eventName.getText().toString(), date.getText);
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(year + 1900, month, dayOfMonth);
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMMM dd", Locale.US);
        date.setText(sdf.format(c.getTime()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
