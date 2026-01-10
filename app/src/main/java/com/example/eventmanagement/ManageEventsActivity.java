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

public class ManageEventsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ManageEventAdapter adapter;
    List<Event> eventList;

    DatabaseReference eventsRef;
    String clubName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_events);

        clubName = getIntent().getStringExtra("clubName");

        recyclerView = findViewById(R.id.recyclerManageEvents);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        eventList = new ArrayList<>();
        adapter = new ManageEventAdapter(this, eventList);
        recyclerView.setAdapter(adapter);

        eventsRef = FirebaseDatabase.getInstance().getReference("Events");

        loadClubEvents();
    }

    private void loadClubEvents() {

        if (clubName == null || clubName.trim().isEmpty()) {
            Toast.makeText(this, "Club not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        eventsRef.orderByChild("clubName")
                .equalTo(clubName)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        eventList.clear();

                        for (DataSnapshot child : snapshot.getChildren()) {
                            Event event = child.getValue(Event.class);
                            if (event != null) {
                                event.setEventId(child.getKey()); // ✅ ALWAYS SET ID
                                eventList.add(event);
                            }
                        }

                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(
                                ManageEventsActivity.this,
                                "Failed to load events",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                });
    }
}
