package com.example.eventmanagement;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterEventActivity extends AppCompatActivity {

    EditText etFullName, etEmail, etDepartment, etClub;
    AutoCompleteTextView actvBatch, actvEventName, actvClub;
    Button btnRegisterEvent;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_event);

        etFullName = findViewById(R.id.etFullName);
        etEmail = findViewById(R.id.etEmail);
        etDepartment = findViewById(R.id.etDepartment);


        actvBatch = findViewById(R.id.actvBatch);
        actvEventName = findViewById(R.id.actvEventName);
        actvClub = findViewById(R.id.actvClub);


        btnRegisterEvent = findViewById(R.id.btnRegisterEvent);

        databaseReference = FirebaseDatabase.getInstance().getReference("EventRegistrations");

        setupBatchDropdown();
        setupEventDropdown();
        setupClubDropdown();



        actvBatch.setOnClickListener(v -> actvBatch.showDropDown());
        actvEventName.setOnClickListener(v -> actvEventName.showDropDown());
        actvClub.setOnClickListener(v -> actvClub.showDropDown());


        btnRegisterEvent.setOnClickListener(v -> registerEvent());
    }

    private void setupClubDropdown() {
        String[] events = {"Computer Club", "Debating Club", "Robotics Club", "Sports Club"};
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, events);
        actvClub.setAdapter(adapter);
        actvClub.setThreshold(0);
    }

    private void setupBatchDropdown() {
        String[] batches = {"2K20", "2K21", "2K22", "2K23", "2K24"};
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, batches);
        actvBatch.setAdapter(adapter);
        actvBatch.setThreshold(0);
    }

    private void setupEventDropdown() {
        String[] events = {"Tech Fest", "Cultural Night", "Workshop", "Seminar"};
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, events);
        actvEventName.setAdapter(adapter);
        actvEventName.setThreshold(0);
    }


    private void registerEvent() {
        String fullName = etFullName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String batch = actvBatch.getText().toString().trim();
        String department = etDepartment.getText().toString().trim();
        String eventName = actvEventName.getText().toString().trim();
        String club = actvClub.getText().toString().trim();

        if (fullName.isEmpty() || email.isEmpty() || batch.isEmpty()
                || department.isEmpty() || eventName.isEmpty()) {

            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String registrationId = databaseReference.push().getKey();
        if (registrationId == null) {
            Toast.makeText(this, "Failed to generate registration id", Toast.LENGTH_SHORT).show();
            return;
        }

        HashMap<String, String> data = new HashMap<>();
        data.put("fullName", fullName);
        data.put("email", email);
        data.put("batch", batch);
        data.put("department", department);
        data.put("eventName", eventName);
        data.put("club", club);

        databaseReference.child(registrationId).setValue(data)
                .addOnSuccessListener(unused ->
                        Toast.makeText(this, "Event Registered Successfully", Toast.LENGTH_SHORT).show()
                )
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );
    }
}
