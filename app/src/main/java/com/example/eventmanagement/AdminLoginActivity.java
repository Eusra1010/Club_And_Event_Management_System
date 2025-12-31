package com.example.eventmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AdminLoginActivity extends AppCompatActivity {

    EditText etAdminId, etAdminPassword;
    Button btnAdminLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        etAdminId = findViewById(R.id.username);
        etAdminPassword = findViewById(R.id.etPassword);
        btnAdminLogin = findViewById(R.id.AdminLoginBtn);

        btnAdminLogin.setOnClickListener(v -> {
            Toast.makeText(this, "Admin Login Successful", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(
                    AdminLoginActivity.this,
                    AdminDashboardActivity.class
            ));
            finish();
        });
    }
}
