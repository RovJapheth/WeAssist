package com.ambongan.weassist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FeedbackActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextInputEditText feedback;
    Button submitBtn;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        // toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        feedback = (TextInputEditText) findViewById(R.id.feedback);
        submitBtn = (Button) findViewById((R.id.submitBtn));

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String feedBackTxt = feedback.getText().toString();

                //Get reference of the Real time database.

                reference = FirebaseDatabase.getInstance().getReference().child("Feedback:" );

                //Push the values to the database.

                reference.push().setValue(feedBackTxt);

                feedback.setText("");

                Toast.makeText(FeedbackActivity.this, "Thank you for providing the feedback.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}