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
 * Created by Yvonne Luk on 4/6/2017.
 */

public class UpdateInfoActivity extends AppCompatActivity {

    private Button save;
    private EditText email;
    private EditText phone;
    private EditText website;
    private EditText info;
    private String currentInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);

        getSupportActionBar().setTitle("Update Info");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        info = (EditText) findViewById(R.id.edit_info);
        email = (EditText) findViewById(R.id.email_edit);
        phone = (EditText) findViewById(R.id.phone_edit);
        website = (EditText) findViewById(R.id.website_edit);
        Bundle bundle = getIntent().getExtras();
        currentInfo = bundle.getString("EMAIL");
        if (currentInfo != null) {
            email.setText(currentInfo);
        }
        currentInfo = bundle.getString("PHONE");
        if (currentInfo != null) {
            phone.setText(currentInfo);
        }
        currentInfo = bundle.getString("WEBSITE");
        if (currentInfo != null) {
            website.setText(currentInfo);
        }
        currentInfo = bundle.getString("INFO");
        if (currentInfo != null) {
            info.setText(currentInfo);
        }

        save = (Button)findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = getIntent();
                i.putExtra("INFO", info.getText().toString());
                i.putExtra("EMAIL", email.getText().toString());
                i.putExtra("PHONE", phone.getText().toString());
                i.putExtra("WEBSITE", website.getText().toString());
                setResult(Activity.RESULT_OK, i);
                finish();
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


