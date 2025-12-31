package com.example.eventmanagement;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class StudentDashboardActivity extends AppCompatActivity {

    LinearLayout btnViewEvents, btnRegisterEvent, btnMyBookings, btnAnnouncements;
    Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);

        btnViewEvents = findViewById(R.id.cardViewEvents);
        btnRegisterEvent = findViewById(R.id.cardRegisterEvent);
        btnMyBookings = findViewById(R.id.cardMyBookings);
        btnAnnouncements = findViewById(R.id.cardAnnouncements);

        btnLogout = findViewById(R.id.btnStudentLogout);

        btnRegisterEvent.setOnClickListener(v ->
                startActivity(new Intent(this, RegisterEventActivity.class))
        );

        btnViewEvents.setOnClickListener(v ->
                showNotImplemented()
        );

        btnMyBookings.setOnClickListener(v ->
                showNotImplemented()
        );

        btnAnnouncements.setOnClickListener(v ->
                showNotImplemented()
        );

        btnLogout.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }

    private void showNotImplemented() {
    }
}
