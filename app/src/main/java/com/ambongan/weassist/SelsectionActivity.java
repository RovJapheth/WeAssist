package com.ambongan.weassist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ambongan.weassist.services.preferences;
import com.google.firebase.auth.FirebaseAuth;

public class SelsectionActivity extends AppCompatActivity {

    CardView studentlist;
    CardView announcement;
    TextView logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selsection);

        announcement = findViewById(R.id.announcement);
        studentlist = findViewById(R.id.studentlist);
        logout = findViewById(R.id.logout);

        announcement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity2.class));
            }
        });

        studentlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), StudentListActivity.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutItemClick();
            }
        });
    }

    private void logoutItemClick() {
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();
        preferences.setDataLogin(this, false);
        Intent intent = new Intent(SelsectionActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}