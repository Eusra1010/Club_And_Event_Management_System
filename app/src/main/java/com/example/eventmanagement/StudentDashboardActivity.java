package com.example.eventmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

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

public class StudentDashboardActivity extends AppCompatActivity {

    RecyclerView rvEvents;
    TextView btnNotification, btnHome, tvViewAll;
    LinearLayout cardMyRegistrations;

    DatabaseReference eventRef;
    ArrayList<Event> eventList;
    EventAdapter eventAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);

        rvEvents = findViewById(R.id.rvEvents);
        btnNotification = findViewById(R.id.btnNotification);
        btnHome = findViewById(R.id.btnHome);
        tvViewAll = findViewById(R.id.tvViewAll);
        cardMyRegistrations = findViewById(R.id.cardMyRegistrations);

        rvEvents.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        );

        eventList = new ArrayList<>();
        eventAdapter = new EventAdapter(this, eventList);
        rvEvents.setAdapter(eventAdapter);

        eventRef = FirebaseDatabase.getInstance().getReference("Events");

        eventRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                eventList.clear();
                int count = 0;

                for (DataSnapshot child : snapshot.getChildren()) {
                    if (count == 8) break;

                    Event event = child.getValue(Event.class);
                    if (event != null) {
                        eventList.add(event);
                        count++;
                    }
                }
                eventAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        btnHome.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });

        btnNotification.setOnClickListener(v ->
                startActivity(new Intent(this, NotificationActivity.class))
        );

        cardMyRegistrations.setOnClickListener(v ->
                startActivity(new Intent(this, MyRegistrationsActivity.class))
        );

        tvViewAll.setOnClickListener(v ->
                startActivity(new Intent(
                        StudentDashboardActivity.this,
                        ViewEventsActivity.class
                ))
        );
    }
}
