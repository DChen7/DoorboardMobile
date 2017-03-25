package com.doorboard3.doorboardmobile;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by danielchen on 3/25/17.
 */
public class CustomEditTextOnFocusChangeListener implements View.OnFocusChangeListener {
    Context context;

    public CustomEditTextOnFocusChangeListener(Context context) {
        this.context = context;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(!hasFocus) {
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }
}

