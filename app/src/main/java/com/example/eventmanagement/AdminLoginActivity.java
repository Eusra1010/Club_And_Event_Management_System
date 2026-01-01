package com.example.eventmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminLoginActivity extends AppCompatActivity {

    EditText etAdminId, etAdminPassword;
    Button btnAdminLogin;

    DatabaseReference adminRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        etAdminId = findViewById(R.id.username);
        etAdminPassword = findViewById(R.id.etPassword);
        btnAdminLogin = findViewById(R.id.AdminLoginBtn);

        adminRef = FirebaseDatabase.getInstance().getReference("Admins");

        btnAdminLogin.setOnClickListener(v -> loginAdmin());
    }

    private void loginAdmin() {
        String adminId = etAdminId.getText().toString().trim();
        String password = etAdminPassword.getText().toString().trim();

        if (adminId.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Enter ID and password", Toast.LENGTH_SHORT).show();
            return;
        }

        adminRef.child(adminId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {

                        if (!snapshot.exists()) {
                            Toast.makeText(AdminLoginActivity.this,
                                    "Admin not found",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }

                        String dbPassword = snapshot.child("password").getValue(String.class);
                        String clubName = snapshot.child("clubName").getValue(String.class);

                        if (!password.equals(dbPassword)) {
                            Toast.makeText(AdminLoginActivity.this,
                                    "Invalid password",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Intent intent = new Intent(
                                AdminLoginActivity.this,
                                AdminDashboardActivity.class
                        );
                        intent.putExtra("clubName", clubName);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        Toast.makeText(AdminLoginActivity.this,
                                "Database error",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
