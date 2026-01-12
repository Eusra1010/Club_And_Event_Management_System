package com.example.eventmanagement;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class StudentRegisterActivity extends AppCompatActivity {

    EditText etFullName, etRoll, etPassword;
    Button btnRegister;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_register);

        etFullName = findViewById(R.id.etFullName);
        etRoll = findViewById(R.id.etRoll);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);

        databaseReference = FirebaseDatabase.getInstance()
                .getReference("StudentRegistrations");

        btnRegister.setOnClickListener(v -> registerStudent());
    }

    private void registerStudent() {

        String fullName = etFullName.getText().toString().trim();
        String roll = etRoll.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (fullName.isEmpty() || roll.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        HashMap<String, String> data = new HashMap<>();
        data.put("fullName", fullName);
        data.put("roll", roll);
        data.put("password", password);

        databaseReference.child(roll)
                .setValue(data)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(this, "Sign up Successful", Toast.LENGTH_LONG).show();
                    finish();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );
    }
}
