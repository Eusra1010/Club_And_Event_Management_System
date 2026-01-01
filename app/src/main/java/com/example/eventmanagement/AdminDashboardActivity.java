package com.example.eventmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AdminDashboardActivity extends AppCompatActivity {

    LinearLayout btnCreateEvent, btnManageEvents, btnViewRegistrations, btnAnnouncements;
    Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        btnCreateEvent = findViewById(R.id.cardCreateEvent);
        btnManageEvents = findViewById(R.id.cardManageEvents);
        btnViewRegistrations = findViewById(R.id.cardViewRegistrations);
        btnAnnouncements = findViewById(R.id.cardAdminAnnouncements);
        btnLogout = findViewById(R.id.btnAdminLogout);

        btnCreateEvent.setOnClickListener(v -> {
            Intent intent = new Intent(AdminDashboardActivity.this, CreateEventActivity.class);
            startActivity(intent);
        });

        btnManageEvents.setOnClickListener(v ->
                showNotImplemented()
        );

        btnViewRegistrations.setOnClickListener(v ->
                showNotImplemented()
        );

        btnAnnouncements.setOnClickListener(v ->
                showNotImplemented()
        );

        btnLogout.setOnClickListener(v -> {
            startActivity(new Intent(AdminDashboardActivity.this, MainActivity.class));
            finish();
        });
    }

    private void showNotImplemented() {
        Toast.makeText(this, "Feature coming soon", Toast.LENGTH_SHORT).show();
    }
}
