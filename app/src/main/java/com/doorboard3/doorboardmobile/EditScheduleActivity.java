package com.doorboard3.doorboardmobile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class EditScheduleActivity extends AppCompatActivity {

    TextView test;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_schedule);

        bundle = this.getIntent().getExtras();
        test = (TextView) findViewById(R.id.test);
        test.setText(bundle.getString("ID"));

    }
}
