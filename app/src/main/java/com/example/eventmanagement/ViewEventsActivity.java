package com.example.eventmanagement;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewEventsActivity extends AppCompatActivity {

    RecyclerView rvAllEvents;
    ArrayList<Event> eventList;
    EventAdapter eventAdapter;

    String studentKey;   // 🔑 ROLL

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_events);

        // 🔑 RECEIVE studentKey
        studentKey = getIntent().getStringExtra("studentKey");

        if (studentKey == null || studentKey.isEmpty()) {
            Toast.makeText(this,
                    "Session expired. Please login again.",
                    Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        rvAllEvents = findViewById(R.id.rvAllEvents);
        rvAllEvents.setLayoutManager(new LinearLayoutManager(this));

        eventList = new ArrayList<>();

        // 🔥 PASS studentKey TO ADAPTER
        eventAdapter = new EventAdapter(this, eventList, studentKey);
        rvAllEvents.setAdapter(eventAdapter);

        FirebaseDatabase.getInstance()
                .getReference("Events")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        eventList.clear();
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            Event event = ds.getValue(Event.class);
                            if (event != null) {
                                eventList.add(event);
                            }
                        }
                        eventAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Optional: show error
                    }
                });
    }
}
