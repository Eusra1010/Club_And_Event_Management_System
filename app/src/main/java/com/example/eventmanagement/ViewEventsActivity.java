package com.example.eventmanagement;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewEventsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    EventAdapter adapter;
    List<Event> eventList;

    DatabaseReference eventsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_events);

        recyclerView = findViewById(R.id.recyclerEvents);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        eventList = new ArrayList<>();
        adapter = new EventAdapter(this, eventList);
        recyclerView.setAdapter(adapter);

        eventsRef = FirebaseDatabase.getInstance().getReference("Events");

        loadEvents();
    }

    private void loadEvents() {

        eventsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                eventList.clear();

                for (DataSnapshot child : snapshot.getChildren()) {

                    Event event = child.getValue(Event.class);

                    if (event != null) {
                        eventList.add(event);
                    }
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ViewEventsActivity.this,
                        "Failed to load events",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

}
