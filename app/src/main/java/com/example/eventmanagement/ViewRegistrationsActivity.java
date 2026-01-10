package com.example.eventmanagement;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewRegistrationsActivity extends AppCompatActivity {

    private Spinner eventSpinner;
    private RecyclerView recyclerView;
    private TextView emptyText;

    private DatabaseReference eventsRef;
    private DatabaseReference registrationsRef;

    private final List<String> eventNames = new ArrayList<>();
    private final Map<String, String> eventNameToId = new HashMap<>();

    private final List<Registration> registrationList = new ArrayList<>();
    private RegistrationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_registrations);

        eventSpinner = findViewById(R.id.eventSpinner);
        recyclerView = findViewById(R.id.registrationRecyclerView);
        emptyText = findViewById(R.id.emptyText);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RegistrationAdapter(registrationList);
        recyclerView.setAdapter(adapter);

        eventsRef = FirebaseDatabase.getInstance().getReference("Events");
        registrationsRef = FirebaseDatabase.getInstance().getReference("EventRegistrations");

        loadEvents();
    }

    private void loadEvents() {
        eventsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                eventNames.clear();
                eventNameToId.clear();

                for (DataSnapshot ds : snapshot.getChildren()) {
                    String eventId = ds.getKey();
                    String eventName = ds.child("eventName").getValue(String.class);

                    if (eventId != null && eventName != null) {
                        eventNames.add(eventName);
                        eventNameToId.put(eventName, eventId);
                    }
                }

                if (eventNames.isEmpty()) {
                    emptyText.setVisibility(View.VISIBLE);
                    emptyText.setText("No events found");
                    return;
                }

                ArrayAdapter<String> spinnerAdapter =
                        new ArrayAdapter<>(ViewRegistrationsActivity.this,
                                android.R.layout.simple_spinner_dropdown_item,
                                eventNames);

                eventSpinner.setAdapter(spinnerAdapter);

                eventSpinner.setOnItemSelectedListener(
                        new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(
                                    AdapterView<?> parent, View view, int position, long id) {

                                String selectedEventName = eventNames.get(position);
                                String selectedEventId = eventNameToId.get(selectedEventName);

                                if (selectedEventId != null) {
                                    loadRegistrations(selectedEventId);
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {}
                        }
                );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    private void loadRegistrations(String eventId) {
        registrationsRef.child(eventId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        registrationList.clear();

                        for (DataSnapshot ds : snapshot.getChildren()) {
                            Registration r = ds.getValue(Registration.class);
                            if (r != null) {
                                registrationList.add(r);
                            }
                        }

                        adapter.notifyDataSetChanged();

                        emptyText.setVisibility(
                                registrationList.isEmpty()
                                        ? View.VISIBLE
                                        : View.GONE
                        );

                        if (registrationList.isEmpty()) {
                            emptyText.setText("No registrations found");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });
    }
}
