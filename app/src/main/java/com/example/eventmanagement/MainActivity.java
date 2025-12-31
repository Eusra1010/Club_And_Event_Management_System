package com.example.eventmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialButton loginDropdownBtn = findViewById(R.id.loginDropdownBtn);
        MaterialButton registerBtn = findViewById(R.id.registerBtn);

        loginDropdownBtn.setOnClickListener(v -> {
                    PopupMenu popupMenu = new PopupMenu(MainActivity.this, loginDropdownBtn);
                    popupMenu.getMenuInflater().inflate(R.menu.login_menu, popupMenu.getMenu());

                    popupMenu.setOnMenuItemClickListener(item -> {
                        int id = item.getItemId();

                        if (id == R.id.student_login) {
                            startActivity(new Intent(MainActivity.this, StudentLoginActivity.class));
                            return true;

                        } else if (id == R.id.admin_login) {
                            startActivity(new Intent(MainActivity.this, AdminLoginActivity.class));
                            return true;
                        }

                        return false;
                    });

                    popupMenu.show();
                });
            registerBtn.setOnClickListener(v ->
                    startActivity(new Intent(
                            MainActivity.this,
                            StudentRegisterActivity.class
                    ))
        );
    }
}
