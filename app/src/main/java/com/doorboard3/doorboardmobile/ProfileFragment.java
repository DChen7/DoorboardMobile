package com.doorboard3.doorboardmobile;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ProfileFragment extends Fragment {
    public static int RESULT_LOAD_IMAGE= 3;
    public static int RESULT_SUBMIT_REQUEST = 15;
    public static int RESULT_UPDATE_INFO = 54;
    private ImageButton photo;
    private Button requestAccess;
    private TextView info;
    private Button editInfo;

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        // Inflate the layout for this fragment
        photo = (ImageButton) v.findViewById(R.id.imageButton);
        requestAccess = (Button) v.findViewById(R.id.request_access);
        info = (TextView) v.findViewById(R.id.info_field);
        editInfo = (Button) v.findViewById(R.id.update_info);

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, RESULT_LOAD_IMAGE);
            }
        });

        requestAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), RequestAccessActivity.class);
                startActivityForResult(intent,RESULT_SUBMIT_REQUEST);
            }
        });

        editInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String infoText = info.getText().toString();
                //Message msg = dbHelper.getMessageForNameAndRoom("Daniel Chen", room);
                Bundle bundle = new Bundle();
                if (infoText != null)
                    bundle.putString("MESSAGE", infoText);
                Intent intent = new Intent(v.getContext(), UpdateInfoActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, RESULT_UPDATE_INFO);
            }
        });
        return v;
    }

        @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==RESULT_LOAD_IMAGE && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                photo.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        if(requestCode==RESULT_SUBMIT_REQUEST && resultCode == Activity.RESULT_OK) {
            Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Your request has been sent!", Toast.LENGTH_SHORT);
            toast.show();
        }

            if(requestCode==RESULT_UPDATE_INFO && resultCode == Activity.RESULT_OK) {
                info.setText(data.getExtras().getString("INFO"));

            }
    }





}
