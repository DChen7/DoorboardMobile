package com.doorboard3.doorboardmobile;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

/**
 * Created by danielchen on 3/25/17.
 */
public class CustomEditText extends AppCompatEditText {

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnFocusChangeListener(new CustomEditTextOnFocusChangeListener(context));
    }
}
