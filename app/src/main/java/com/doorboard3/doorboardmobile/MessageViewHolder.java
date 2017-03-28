package com.doorboard3.doorboardmobile;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by danielchen on 3/28/17.
 */

public class MessageViewHolder extends RecyclerView.ViewHolder {

    ImageView profilePic;
    TextView name;
    TextView dateTime;
    TextView status;

    MessageViewHolder(View itemView) {
        super(itemView);
        profilePic = (ImageView) itemView.findViewById(R.id.profile_pic);
        name = (TextView) itemView.findViewById(R.id.name);
        dateTime = (TextView) itemView.findViewById(R.id.date_time);
        status = (TextView) itemView.findViewById(R.id.status);
    }
}
