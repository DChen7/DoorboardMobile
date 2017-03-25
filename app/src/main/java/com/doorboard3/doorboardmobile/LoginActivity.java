package com.doorboard3.doorboardmobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameView;
    private EditText passwordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameView = (EditText) findViewById(R.id.login_username);
        passwordView = (EditText) findViewById(R.id.login_password);
        Button loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameView.getText().toString();
                String password = passwordView.getText().toString();

                if (TextUtils.isEmpty(username)) {
                    usernameView.setError("This field is required.");
                    usernameView.requestFocus();
                } else if (TextUtils.isEmpty(password)) {
                    passwordView.setError("This field is required.");
                    passwordView.requestFocus();
                } else {
                    validateInputs(username, password);
                }
            }
        });
        Intent mainIntent = new Intent(this, MainActivity.class);

    }

    private void validateInputs(String username, String password) {
        if (username.equals("dchen124") && password.equals("password")) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        } else {
            passwordView.setError("Username/password is incorrect. Please try again.");
            passwordView.requestFocus();
        }
    }
}
