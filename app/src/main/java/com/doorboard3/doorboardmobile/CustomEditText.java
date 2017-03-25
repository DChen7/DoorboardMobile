package com.doorboard3.doorboardmobile;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by danielchen on 3/25/17.
 */
public class CustomEditText extends EditText {

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnFocusChangeListener(new CustomEditTextOnFocusChangeListener(context));
    }
}
