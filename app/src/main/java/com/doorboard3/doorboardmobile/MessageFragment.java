package com.doorboard3.doorboardmobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import static android.content.ContentValues.TAG;

public class MessageFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private DoorboardDbHelper dbHelper;
    private String room;
    private Bundle bundle;

    public static MessageFragment newInstance() {
        MessageFragment fragment = new MessageFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        dbHelper = new DoorboardDbHelper(getContext());


        // Populate db with test data
        Populator populator = new Populator(dbHelper, getContext());
        populator.populate();

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_message, container, false);

        // Populate drop-down menu
        Spinner spinner = (Spinner) v.findViewById(R.id.room_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.room_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        // Set the room
        bundle = this.getActivity().getIntent().getExtras();
        if (bundle != null && bundle.getBoolean("UPDATE")) {
            Log.i(TAG, "UPDATE is true");
            room = bundle.getString("ROOM");
        } else {
            room = "Iribe 203";
        }
        Log.i(TAG, "Room: " + room);

        int spinnerPosition = adapter.getPosition(room);
        spinner.setSelection(spinnerPosition);

        // Get messages
        mRecyclerView = (RecyclerView) v.findViewById(R.id.message_list);
        mAdapter = new MessageAdapter(dbHelper.getMessagesForRoom("Iribe 203"));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemViewCacheSize(20);
        mRecyclerView.setDrawingCacheEnabled(true);
        mRecyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        // Set behavior of EditText
        EditText updateMessage = (EditText) v.findViewById(R.id.update_message);
        updateMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = dbHelper.getMessageForNameAndRoom("Daniel Chen", room);
                Bundle bundle = new Bundle();
                if (msg != null)
                    bundle.putString("MESSAGE", msg.getStatus());
                bundle.putString("ROOM", room);
                Intent intent = new Intent(v.getContext(), UpdateMessageActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        return v;
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)

        room = (String) parent.getItemAtPosition(pos);
        mAdapter = new MessageAdapter(dbHelper.getMessagesForRoom(room));
        mRecyclerView.setAdapter(mAdapter);
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}
