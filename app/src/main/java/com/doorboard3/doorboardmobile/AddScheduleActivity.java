package com.doorboard3.doorboardmobile;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
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
    private String startTimeStr;
    private String endTimeStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);

        getSupportActionBar().setTitle("Add an event");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbHelper = new DoorboardDbHelper(this);

        eventName = (EditText) findViewById(R.id.edit_event_name);
        description = (EditText) findViewById(R.id.edit_event_description);

        Calendar c = Calendar.getInstance();
        int hourOfDay = c.get(Calendar.HOUR_OF_DAY);
        int hour = c.get(Calendar.HOUR);
        int ampm = c.get(Calendar.AM_PM);

        date = (TextView) findViewById(R.id.pick_date);
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMMM dd, yyyy", Locale.US);
        date.setText(sdf.format(c.getTime()));
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
        startTimeStr = hourOfDay + ":00";
        startTime.setText(hour + ":00 " + (ampm == Calendar.AM ? "AM" : "PM"));
        final TimePickerDialog startTimeDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                startTimeStr = hourOfDay + ":" + minute;
                String amPm = "AM";
                if (hourOfDay == 0) {
                    hourOfDay = 12;
                } else if (hourOfDay > 12) {
                    hourOfDay -= 12;
                    amPm = "PM";
                }

                startTime.setText(hourOfDay + ":" + (minute == 0 ? "00" : minute) + " " + amPm);
            }
        }, hour, 0, false);
        LinearLayout startTimeLayout = (LinearLayout) findViewById(R.id.pick_start_layout);
        startTimeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimeDialog.show();
            }
        });

        endTime = (TextView) findViewById(R.id.pick_end);
        endTimeStr = hourOfDay + ":30";
        endTime.setText(hour + ":30 " + (ampm == Calendar.AM ? "AM" : "PM"));
        final TimePickerDialog endTimeDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                endTimeStr = hourOfDay + ":" + minute;
                String amPm = "AM";
                if (hourOfDay == 0) {
                    hourOfDay = 12;
                } else if (hourOfDay > 12) {
                    hourOfDay -= 12;
                    amPm = "PM";
                }
                endTime.setText(hourOfDay + ":" + (minute == 0 ? "00" : minute) + " " + amPm);
            }
        }, hour, 30, false);
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
                int selectedId = roomGroup.getCheckedRadioButtonId();
                RadioButton roomButton = (RadioButton) findViewById(selectedId);

                dbHelper.saveEvent(eventName.getText().toString(), date.getText().toString(),
                        startTimeStr, endTimeStr, roomButton.getText().toString(), description.getText().toString());

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("scheduleUpdate", true);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, dayOfMonth);
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMMM dd, yyyy", Locale.US);
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
