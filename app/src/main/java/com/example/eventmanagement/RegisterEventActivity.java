package com.example.eventmanagement;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterEventActivity extends AppCompatActivity {

    EditText etFullName, etEmail, etDepartment, etEventName, etClubName, etContactNo;
    AutoCompleteTextView actvBatch;
    Button btnRegisterEvent;

    DatabaseReference eventsRef, registrationsRef;

    String eventId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_event);
        eventId = getIntent().getStringExtra("eventId");
        String eventName = getIntent().getStringExtra("eventName");
        String clubName = getIntent().getStringExtra("clubName");

        if (eventId == null || eventName == null || clubName == null) {
            Toast.makeText(this, "Invalid event", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }


        etFullName = findViewById(R.id.etFullName);
        etEmail = findViewById(R.id.etEmail);
        etContactNo = findViewById(R.id.etContactNo);
        etDepartment = findViewById(R.id.etDepartment);
        actvBatch = findViewById(R.id.actvBatch);
        etEventName = findViewById(R.id.etEventName);
        etClubName = findViewById(R.id.etClubName);
        btnRegisterEvent = findViewById(R.id.btnRegisterEvent);


        etEventName.setText(eventName);
        etClubName.setText(clubName);
        etEventName.setEnabled(false);
        etClubName.setEnabled(false);

        setupBatchDropdown();


        eventsRef = FirebaseDatabase.getInstance().getReference("Events");
        registrationsRef = FirebaseDatabase.getInstance().getReference("EventRegistrations");

        btnRegisterEvent.setOnClickListener(v -> register());
    }

    private void setupBatchDropdown() {
        String[] batches = {"2K20", "2K21", "2K22", "2K23", "2K24"};
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this,
                        android.R.layout.simple_dropdown_item_1line,
                        batches);
        actvBatch.setAdapter(adapter);
        actvBatch.setOnClickListener(v -> actvBatch.showDropDown());
    }

    private void register() {

        String fullName = etFullName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String contactNo = etContactNo.getText().toString().trim();
        String department = etDepartment.getText().toString().trim();
        String batch = actvBatch.getText().toString().trim();

        if (fullName.isEmpty() || email.isEmpty() ||
                contactNo.isEmpty() || department.isEmpty() || batch.isEmpty()) {
            Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        String studentKey = email.replace(".", "_");


        eventsRef.child(eventId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        Boolean open = snapshot.child("registrationOpen")
                                .getValue(Boolean.class);

                        if (open == null || !open) {
                            Toast.makeText(RegisterEventActivity.this,
                                    "Registration is closed", Toast.LENGTH_SHORT).show();
                            return;
                        }


                        registrationsRef.child(eventId)
                                .child(studentKey)
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snap) {

                                        if (snap.exists()) {
                                            Toast.makeText(RegisterEventActivity.this,
                                                    "Already registered", Toast.LENGTH_SHORT).show();
                                            return;
                                        }

                                        HashMap<String, Object> data = new HashMap<>();
                                        data.put("fullName", fullName);
                                        data.put("email", email);
                                        data.put("contactNo", contactNo);
                                        data.put("department", department);
                                        data.put("batch", batch);

                                        registrationsRef.child(eventId)
                                                .child(studentKey)
                                                .setValue(data)
                                                .addOnSuccessListener(unused -> {

                                                    Toast.makeText(
                                                            RegisterEventActivity.this,
                                                            "Registration successful",
                                                            Toast.LENGTH_LONG
                                                    ).show();
                                                    finish();
                                                });
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {}
                                });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });
    }
}
