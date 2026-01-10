package com.example.eventmanagement;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class EditEventActivity extends AppCompatActivity {

    EditText etEventName, etEventDate, etVenue, etFees, etDeadline;
    SwitchMaterial switchRegistration;
    Button btnSaveChanges;

    DatabaseReference eventRef;
    String eventId;
    String eventStatus = "ACTIVE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

        eventId = getIntent().getStringExtra("eventId");
        if (eventId == null) {
            finish();
            return;
        }

        eventRef = FirebaseDatabase.getInstance()
                .getReference("Events")
                .child(eventId);

        etEventName = findViewById(R.id.etEventName);
        etEventDate = findViewById(R.id.etEventDate);
        etVenue = findViewById(R.id.etVenue);
        etFees = findViewById(R.id.etFees);
        etDeadline = findViewById(R.id.etDeadline);
        switchRegistration = findViewById(R.id.switchRegistration);
        btnSaveChanges = findViewById(R.id.btnSaveChanges);

        etEventName.setEnabled(false);

        loadEventData();

        etEventDate.setOnClickListener(v -> showDatePicker(etEventDate));
        etDeadline.setOnClickListener(v -> showDatePicker(etDeadline));

        btnSaveChanges.setOnClickListener(v ->
                new AlertDialog.Builder(this)
                        .setTitle("Confirm Update")
                        .setMessage("Save changes?")
                        .setPositiveButton("Save", (d, w) -> saveChanges())
                        .setNegativeButton("Cancel", null)
                        .show()
        );
    }

    private void showDatePicker(EditText target) {
        Calendar cal = Calendar.getInstance();
        new DatePickerDialog(
                this,
                (v, y, m, d) -> {
                    String date = y + "-" +
                            String.format("%02d", m + 1) + "-" +
                            String.format("%02d", d);
                    target.setText(date);
                },
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
        ).show();
    }

    private void loadEventData() {
        eventRef.get().addOnSuccessListener(snapshot -> {
            etEventName.setText(snapshot.child("eventName").getValue(String.class));
            etEventDate.setText(snapshot.child("eventDate").getValue(String.class));
            etVenue.setText(snapshot.child("venue").getValue(String.class));
            etFees.setText(snapshot.child("fees").getValue(String.class));
            etDeadline.setText(snapshot.child("registrationDeadline").getValue(String.class));

            Boolean open = snapshot.child("registrationOpen").getValue(Boolean.class);
            String status = snapshot.child("status").getValue(String.class);

            if (status != null) eventStatus = status;

            if ("CANCELLED".equals(eventStatus)) {
                switchRegistration.setChecked(false);
                switchRegistration.setEnabled(false);
            } else {
                switchRegistration.setChecked(open != null && open);
                switchRegistration.setEnabled(true);
            }
        });
    }

    private void saveChanges() {

        String date = etEventDate.getText().toString().trim();
        String venue = etVenue.getText().toString().trim();
        String fees = etFees.getText().toString().trim();
        String deadline = etDeadline.getText().toString().trim();

        boolean registrationOpen =
                !"CANCELLED".equals(eventStatus) && switchRegistration.isChecked();

        if (fees.isEmpty()) fees = "0";

        eventRef.child("eventDate").setValue(date);
        eventRef.child("venue").setValue(venue);
        eventRef.child("fees").setValue(fees);
        eventRef.child("registrationDeadline").setValue(deadline);
        eventRef.child("registrationOpen").setValue(registrationOpen)
                .addOnSuccessListener(aVoid -> finish());
    }
}
