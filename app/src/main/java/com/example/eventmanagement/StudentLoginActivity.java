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

public class StudentLoginActivity extends AppCompatActivity {

    EditText etRoll, etPassword;
    Button btnLogin;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

        etRoll = findViewById(R.id.studentRoll);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.studentLoginBtn);

        databaseReference = FirebaseDatabase.getInstance()
                .getReference("StudentRegistrations");

        btnLogin.setOnClickListener(v -> loginStudent());
    }

    private void loginStudent() {

        String roll = etRoll.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (roll.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Enter roll and password", Toast.LENGTH_SHORT).show();
            return;
        }

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot child : snapshot.getChildren()) {

                    String dbRoll = child.child("roll").getValue(String.class);
                    String dbPassword = child.child("password").getValue(String.class);

                    if (roll.equals(dbRoll) && password.equals(dbPassword)) {

                        Toast.makeText(StudentLoginActivity.this,
                                "Login Successful", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(
                                StudentLoginActivity.this,
                                StudentDashboardActivity.class
                        ));
                        finish();
                        return;
                    }
                }

                Toast.makeText(StudentLoginActivity.this,
                        "Invalid roll or password",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(StudentLoginActivity.this,
                        "Database error",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
