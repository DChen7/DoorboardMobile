package com.doorboard3.doorboardmobile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Yvonne Luk on 4/5/2017.
 */

public class RequestAccessActivity extends AppCompatActivity{

    private Button submit;
    private EditText room;
    private EditText access;
    private EditText reason;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_access);

        getSupportActionBar().setTitle("Request access for a room");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        room = (EditText)findViewById(R.id.room_number);
        access = (EditText)findViewById(R.id.access_time);
        reason = (EditText)findViewById(R.id.reason);

        submit = (Button)findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(room.getText().toString())) {
                    room.setError("This field is required.");
                    room.requestFocus();
                } else if (TextUtils.isEmpty(access.getText().toString())) {
                    access.setError("This field is required.");
                    access.requestFocus();
                }else if (TextUtils.isEmpty(reason.getText().toString())) {
                        reason.setError("This field is required.");
                        reason.requestFocus();
                } else {
                    Intent i = getIntent();
                    setResult(Activity.RESULT_OK, i);
                    finish();
                }
            }
        });
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


