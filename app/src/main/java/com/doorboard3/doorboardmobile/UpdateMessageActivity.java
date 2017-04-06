package com.doorboard3.doorboardmobile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

public class UpdateMessageActivity extends AppCompatActivity {

    private String message;
    private String room;
    private DoorboardDbHelper dbHelper;
    private EditText editMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_message);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Update Message");
        editMessage = (EditText) findViewById(R.id.edit_message);

        // Set message if it already exists
        Bundle bundle = getIntent().getExtras();
        message = bundle.getString("MESSAGE");
        if (message != null) {
            editMessage.setText(message);
        }
        room = bundle.getString("ROOM");

        // Set focus and show keyboard
        editMessage.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        // Set button behavior
        dbHelper = new DoorboardDbHelper(this);
        Button saveButton = (Button) findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.deleteMessage(room);
                String msg = editMessage.getText().toString();
                if (!msg.equals(""))
                    dbHelper.insertMessage(getApplicationContext(), room, msg);
                Bundle bundle = new Bundle();
                bundle.putString("ROOM", room);
                bundle.putBoolean("UPDATE", true);
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                intent.putExtras(bundle);
                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(editMessage.getWindowToken(), 0);
                startActivity(intent);
            }
        });

        Button clearButton = (Button) findViewById(R.id.clear_button);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editMessage.setText("");
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(editMessage.getWindowToken(), 0);
        int id = item.getItemId();
        switch(id) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause() {
        super.onPause();
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(editMessage.getWindowToken(), 0);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
