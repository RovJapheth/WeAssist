package com.ambongan.weassist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    TextInputEditText fPemail;
    TextView fPassBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        fPemail = findViewById(R.id.fPemail);
        fPassBtn = findViewById(R.id.fPassBtn);

        fPassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String fPEmailString = fPemail.getText().toString();

                if (fPEmailString.isEmpty()){
                    Toast.makeText(ForgotPasswordActivity.this, "Please enter Email", Toast.LENGTH_SHORT).show();
                } else {

                    FirebaseAuth.getInstance().sendPasswordResetEmail(fPEmailString).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(ForgotPasswordActivity.this, "Reset Password Insttruction has sent to your Email", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ForgotPasswordActivity.this, "Email Doesn't Exist...", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ForgotPasswordActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });
    }
}