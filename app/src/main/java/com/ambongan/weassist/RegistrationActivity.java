package com.ambongan.weassist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ambongan.weassist.modal.dataLogin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegistrationActivity extends AppCompatActivity {

    Toolbar toolbar;
    protected TextInputEditText username, firstname, lastname, gender, grade, password, confirm_password, email;
    protected Button register;
    protected TextView login;
    protected Context context;
    protected RadioButton studentbox, parentbox, visitorbox, radioButton;
    protected RadioGroup groupradio;
    protected ProgressDialog loading;
    protected DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    com.ambongan.weassist.StudentListModel itemModel;
    String key;
    String value = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;
        loading = new ProgressDialog(context);
        loading.setCancelable(false);
        loading.setMessage("Loading...");

        username = findViewById(R.id.username);
        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        gender = findViewById(R.id.gender);
        grade = findViewById(R.id.grade);
        email = findViewById(R.id.email);
        confirm_password = findViewById(R.id.confirm_password);
        password = findViewById(R.id.password);
        register = findViewById(R.id.register);
        login = findViewById(R.id.login);
        studentbox = findViewById(R.id.studentbox);
        parentbox = findViewById(R.id.parentbox);
        visitorbox = findViewById(R.id.visitorbox);
        groupradio = findViewById(R.id.groupradio);

        groupradio.setOnCheckedChangeListener(
                new RadioGroup
                        .OnCheckedChangeListener() {
                    @Override

                    // The flow will come here when
                    // any of the radio buttons in the radioGroup
                    // has been clicked

                    // Check which radio button has been clicked
                    public void onCheckedChanged(RadioGroup group,
                                                 int checkedId)
                    {

                        // Get the selected Radio Button
                        radioButton
                                = (RadioButton)group
                                .findViewById(checkedId);
                    }
                });


        login.setOnClickListener(view -> {
            Intent intent = new Intent(context,  LoginActivity.class);
            intent.putExtra("key", itemModel.getKey());
            startActivity(intent);
        });


        register.setOnClickListener(view -> {
            String user = username.getText().toString();
            String fname = firstname.getText().toString();
            String temail = email.getText().toString();
            String lname = lastname.getText().toString();
            String sex = gender.getText().toString();
            String section = grade.getText().toString();
            String pass = password.getText().toString();
            String confirm_pass = confirm_password.getText().toString();
            String asa = radioButton.getText().toString();

            if (user.isEmpty()){
                Toast.makeText(context, "Please Enter User Name", Toast.LENGTH_SHORT).show();
            }else if (temail.isEmpty()){
                Toast.makeText(context, "Please Enter Email", Toast.LENGTH_SHORT).show();
            }else if (fname.isEmpty()){
                Toast.makeText(context, "Please Enter First Name", Toast.LENGTH_SHORT).show();
            }else if (lname.isEmpty()){
                Toast.makeText(context, "Please Enter Last Name", Toast.LENGTH_SHORT).show();
            }else if (sex.isEmpty()){
                Toast.makeText(context, "Please Enter Gender", Toast.LENGTH_SHORT).show();
            }else if (pass.isEmpty()){
                Toast.makeText(context, "Please Enter Password", Toast.LENGTH_SHORT).show();
            }else if (confirm_pass.isEmpty()){
                Toast.makeText(context, "Please Enter Confirm password", Toast.LENGTH_SHORT).show();
            }else if (!pass.equals(confirm_pass)){
                Toast.makeText(context, "Please Enter Confirm Password same as Password", Toast.LENGTH_SHORT).show();
            }else if (asa.isEmpty()){
                Toast.makeText(context, "Please Select your Role", Toast.LENGTH_SHORT).show();

            }else {

                database.child("login").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // this method is call to get the realtime
                        // updates in the data.
                        // this method is called when the data is
                        // changed in our Firebase console.
                        // below line is for getting the data from
                        // snapshot of our database.

                        for (DataSnapshot childSnapshot: snapshot.getChildren()) {
                            key = childSnapshot.getKey();
                        }
                        key = snapshot.child("login").getKey();

                        // after getting the value we are setting
                        // our value to our text view in below line.

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // calling on cancelled method when we receive
                        // any error or we are not able to get the data.
                        Toast.makeText(RegistrationActivity.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
                    }
                });

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(temail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(RegistrationActivity.this, "Registration Successfull...", Toast.LENGTH_SHORT).show();

//                            Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
//                            startActivity(intent);

//                            storeData();
                        }else {
                            Toast.makeText(RegistrationActivity.this, "Registration Fail....", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


                database.child("login").push().setValue(
                        new dataLogin(
                                user,
                                temail,
                                fname,
                                lname,
                                sex,
                                section,
                                pass,
                                asa
                        )
                ).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        loading.dismiss();
                        Toast.makeText(context, "Successfully registered, please login", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context,  LoginActivity.class);
                        intent.putExtra("key", key);
                        startActivity(intent);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        loading.dismiss();
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }


//            database.child("login").addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    // this method is call to get the realtime
//                    // updates in the data.
//                    // this method is called when the data is
//                    // changed in our Firebase console.
//                    // below line is for getting the data from
//                    // snapshot of our database.
//
//                    for (DataSnapshot childSnapshot: snapshot.getChildren()) {
//                        key = childSnapshot.getKey();
//                    }
//                    key = snapshot.child("login").getKey();
//
//                    // after getting the value we are setting
//                    // our value to our text view in below line.
//
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//                    // calling on cancelled method when we receive
//                    // any error or we are not able to get the data.
//                    Toast.makeText(RegistrationActivity.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
//                }
//            });

//            if (user.isEmpty()) {
//                username.setError("Data cannot be empty!");
//                username.requestFocus();
//            } else if (fname.isEmpty()) {
//                firstname.setError("Data cannot be empty!");
//                firstname.requestFocus();
//            }else if (temail.isEmpty()) {
//                email.setError("Data cannot be empty!");
//                email.requestFocus();
//            } else if (lname.isEmpty()) {
//                lastname.setError("Data cannot be empty!");
//                lastname.requestFocus();
//            } else if (sex.isEmpty()) {
//                gender.setError("Data cannot be empty!");
//                gender.requestFocus();
//            } else if (pass.isEmpty()) {
//                password.setError("Data cannot be empty!");
//                password.requestFocus();
//            } else if (confirm_pass.isEmpty()) {
//                confirm_password.setError("Data cannot be empty!");
//                confirm_password.requestFocus();
//            } else if (!pass.equals(confirm_pass)) {
//                confirm_password.setError("Password do not match");
//                confirm_password.requestFocus();
//            } else {
//                loading.show();


//                FirebaseAuth.getInstance().createUserWithEmailAndPassword(temail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()){
//                            Toast.makeText(RegistrationActivity.this, "Registration Successfull...", Toast.LENGTH_SHORT).show();
//
////                            Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
////                            startActivity(intent);
//
////                            storeData();
//                        }else {
//                            Toast.makeText(RegistrationActivity.this, "Registration Fail....", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//
//
//                database.child("login").push().setValue(
//                        new dataLogin(
//                                user,
//                                temail,
//                                fname,
//                                lname,
//                                sex,
//                                section,
//                                pass,
//                                asa
//                        )
//                ).addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//                        loading.dismiss();
//                        Toast.makeText(context, "Successfully registered, please login", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(context,  LoginActivity.class);
//                        intent.putExtra("key", key);
//                        startActivity(intent);
//                        finish();
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        loading.dismiss();
//                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
        });
    }
}