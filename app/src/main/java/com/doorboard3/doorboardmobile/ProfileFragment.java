package com.doorboard3.doorboardmobile;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.ParseException;

import static android.content.Context.MODE_PRIVATE;

public class ProfileFragment extends Fragment {
    public static int RESULT_LOAD_IMAGE= 3;
    public static int RESULT_SUBMIT_REQUEST = 15;
    public static int RESULT_UPDATE_INFO = 54;
    public static String INFORMATION_FILE= "info.txt";
    public static String PICTURE_FILE = "photo.png";
    private ImageButton photo;
    private Button requestAccess;
    private TextView email;
    private TextView phone;
    private TextView website;
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
        email = (TextView) v.findViewById(R.id.email_field);
        phone = (TextView) v.findViewById(R.id.phone_field);
        website = (TextView) v.findViewById(R.id.website_field);
        info = (TextView) v.findViewById(R.id.info_field);
        editInfo = (Button) v.findViewById(R.id.update_info);
        loadItems();

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
                String emailText = email.getText().toString();
                String phoneText = phone.getText().toString();
                String websiteText = website.getText().toString();
                Bundle bundle = new Bundle();
                bundle.putString("EMAIL", emailText != null ? emailText : "");
                bundle.putString("PHONE", phoneText != null ? phoneText : "");
                bundle.putString("WEBSITE", websiteText != null ? websiteText : "");
                bundle.putString("INFO", infoText != null ? infoText : "");
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
            //save image to file
            File file = new File(getContext().getFilesDir(), PICTURE_FILE);

            FileOutputStream fos = null;
            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            try {
                //if(!file.exists()) file.createNewFile();
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                photo.setImageBitmap(bitmap);
                try {
                    fos = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if(fos!=null)
                            fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
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
            email.setText(data.getExtras().getString("EMAIL"));
            phone.setText(data.getExtras().getString("PHONE"));
            website.setText(data.getExtras().getString("WEBSITE"));
            info.setText(data.getExtras().getString("INFO"));

            PrintWriter writer = null;
            try {
                FileOutputStream fos = getActivity().openFileOutput(INFORMATION_FILE, MODE_PRIVATE);
                writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                        fos)));

                writer.println(data.getExtras().getString("EMAIL"));
                writer.println(data.getExtras().getString("PHONE"));
                writer.println(data.getExtras().getString("WEBSITE"));
                writer.println(data.getExtras().getString("INFO"));

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (null != writer) {
                    writer.close();
                }
            }

        }
    }
    private void loadItems() {
        try {
            File file = new File(getContext().getFilesDir(),PICTURE_FILE);
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(file));
            photo.setImageBitmap(b);
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }


        BufferedReader reader = null;
        try {
            FileInputStream fis = getActivity().openFileInput(INFORMATION_FILE);
            reader = new BufferedReader(new InputStreamReader(fis));
            email.setText(reader.readLine());
            phone.setText(reader.readLine());
            website.setText(reader.readLine());
            String infotext = "";
            String text ="";
            while (null != (text = reader.readLine())) {
                if (text.equals(""))
                    infotext+='\n';
                else
                    infotext += text;
            };
            info.setText(infotext);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != reader) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }





}
