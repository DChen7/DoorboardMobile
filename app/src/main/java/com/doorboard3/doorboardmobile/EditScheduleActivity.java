package com.doorboard3.doorboardmobile;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EditScheduleActivity extends AppCompatActivity
        implements DatePickerDialog.OnDateSetListener{

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
    Bundle bundle;
    private String TAG = "TEST_____________";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_schedule);

        getSupportActionBar().setTitle("Edit event");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbHelper = new DoorboardDbHelper(this);

        eventName = (EditText) findViewById(R.id.edit_name);
        description = (EditText) findViewById(R.id.edit_description);
        date = (TextView) findViewById(R.id.edit_pick_date);
        startTime = (TextView) findViewById(R.id.edit_pick_start);
        endTime = (TextView) findViewById(R.id.edit_pick_end);

        bundle = this.getIntent().getExtras();
        Log.i(TAG, bundle.getString("name"));
        eventName.setText(bundle.getString("name"));
        description.setText(bundle.getString("description"));

        date.setText(bundle.getString("date"));
        Calendar c = Calendar.getInstance();
        final DatePickerDialog datePickerDialog = new DatePickerDialog(
                this, EditScheduleActivity.this, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        LinearLayout datePickerLayout = (LinearLayout) findViewById(R.id.edit_pick_date_layout);
        datePickerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        startTimeStr = bundle.getString("startTime");
        endTimeStr = bundle.getString("endTime");
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            final Date dateObj = sdf.parse(startTimeStr);
            final SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm a");
            startTime.setText(sdf2.format(dateObj));

            final Date dateObj2 = sdf.parse(endTimeStr);
            endTime.setText(sdf2.format(dateObj2));
        } catch (final ParseException e) {
            e.printStackTrace();
        }
        int startHour = Integer.parseInt(startTimeStr.substring(0, startTimeStr.indexOf(":")));
        int startMin = Integer.parseInt(startTimeStr.substring(startTimeStr.indexOf(":") + 1));
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
        }, startHour, startMin, false);
        LinearLayout startTimeLayout = (LinearLayout) findViewById(R.id.edit_pick_start_layout);
        startTimeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimeDialog.show();
            }
        });

        int endHour = Integer.parseInt(endTimeStr.substring(0, endTimeStr.indexOf(":")));
        int endMin = Integer.parseInt(endTimeStr.substring(endTimeStr.indexOf(":") + 1));
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
        }, endHour, endMin, false);
        LinearLayout endTimeLayout = (LinearLayout) findViewById(R.id.edit_pick_end_layout);
        endTimeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endTimeDialog.show();
            }
        });

        repeatGroup = (LinearLayout) findViewById(R.id.edit_repeat_group);
        repeatGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(), "This feature is still under development", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        roomGroup = (RadioGroup) findViewById(R.id.edit_room_group);
        String room = bundle.getString("room");
        if (room.equals("Iribe 203")) {
            RadioButton b = (RadioButton) findViewById(R.id.edit_radio_room_1);
            b.setChecked(true);
        } else {
            RadioButton b = (RadioButton) findViewById(R.id.edit_radio_room_2);
            b.setChecked(true);
        }

        Button deleteButton = (Button) findViewById(R.id.delete_event_button);
        Button saveButton = (Button) findViewById(R.id.edit_save_event_button);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.deleteEvent(bundle.getString("ID"));

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("scheduleUpdate", true);
                startActivity(intent);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = roomGroup.getCheckedRadioButtonId();
                RadioButton roomButton = (RadioButton) findViewById(selectedId);

                dbHelper.deleteEvent(bundle.getString("ID"));
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
