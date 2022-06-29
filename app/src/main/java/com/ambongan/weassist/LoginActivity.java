package com.ambongan.weassist;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ambongan.weassist.modal.dataLogin;
import com.ambongan.weassist.services.preferences;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    Toolbar toolbar;
    protected DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    protected TextInputEditText username, password;
    protected Button login;
     TextView registration, textView2, forgotPassword;
    //    protected Switch active;
    protected Context context;
    public String key;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.e("status", "backinlogin");

        SharedPreferences prefs = getSharedPreferences("admin", Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = prefs.edit();

        Intent intent = getIntent();
        key = intent.getStringExtra("key");

        context = this;
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        textView2 = findViewById(R.id.textView2);
//        active = findViewById(R.id.active);
        registration = findViewById(R.id.registration);
        forgotPassword = findViewById(R.id.forgotPassword);



        databaseReference.child("login").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // this method is call to get the realtime
                // updates in the data.
                // this method is called when the data is
                // changed in our Firebase console.
                // below line is for getting the data from
                // snapshot of our database.

                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    key = childSnapshot.getKey();
                }
//                    key = snapshot.child("login").getKey();

                // after getting the value we are setting
                // our value to our text view in below line.

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                Toast.makeText(LoginActivity.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });

        showRateDialog(context, editor);
//        loginNow();
//        signUpNow();

        registration.setOnClickListener(view -> {
            startActivity(new Intent(context, RegistrationActivity.class));
        });
    }

//    private void signUpNow() {
//        registration.setOnClickListener(view -> {
//            startActivity(new Intent(context, RegistrationActivity.class));
//        });
//    }


//    private void loginNow() {
//        login.setOnClickListener(view -> {
//            String user = username.getText().toString();
//            String pass = password.getText().toString();
//
//            if (user.isEmpty()) {
//                username.setError("Data cannot be empty!");
//                username.requestFocus();
//            }else if (pass.isEmpty()) {
//                password.setError("Data cannot be empty!");
//                password.requestFocus();
//            }
//
//            databaseReference.child("login").child(user).addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    if (dataSnapshot.exists()) {
//                        dataLogin login = dataSnapshot.getValue(dataLogin.class);
//                        if (login != null) {
//                            if (login.getPassword().equals(pass)) {
//                                if (login.getAs().equals("admin")) {
//                                    if (active.isChecked()) {
//                                        preferences.setDataLogin(context, true);
//                                        preferences.setDataAs(context, "admin");
//                                        Log.e("status","hey admin");
//                                    }
//                                    startActivity(new Intent(context, MainActivity2.class));
//                                }
//                                if (active.isChecked()) {
//                                    if (active.isChecked()) {
//                                        preferences.setDataLogin(context, true);
//                                        preferences.setDataAs(context, "student");
//                                        Log.e("status","hey student");
//                                    }
//                                    startActivity(new Intent(context, Announcements.class));
//                                }
//                            } else {
//                                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    } else {
//                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
//        });
//    }

    @Override
    protected void onStart() {
        super.onStart();
        if (preferences.getDataLogin(context)) {
            if (preferences.getDataAs(context).equals("admin")) {
                startActivity(new Intent(context, MainActivity2.class));
            } else {
                startActivity(new Intent(context, Announcements.class));
            }
            finish();
        }
    }

    public void showRateDialog(final Context mContext, final SharedPreferences.Editor editor) {
        final Dialog dialog = new Dialog(mContext, R.style.Theme_Dialog);
        dialog.setContentView(R.layout.select_role);

        TextView admin = (TextView) dialog.findViewById(R.id.admin);
        TextView student = (TextView) dialog.findViewById(R.id.student);


        admin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                registration.setVisibility(View.GONE);
                forgotPassword.setVisibility(View.GONE);
                textView2.setText("Login For Admin");
                loginforadmin();
                dialog.dismiss();
            }
        });

        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView2.setText("Login For User");
                loginforstudent();
                dialog.dismiss();
            }
        });

        if (preferences.getDataLogin(context)) {
            if (preferences.getDataAs(context).equals("admin")) {
                startActivity(new Intent(context, SelsectionActivity.class));
            } else {
                startActivity(new Intent(context, Announcements.class));
            }
            finish();
        } else {
            dialog.show();
        }

    }

    private void loginforstudent() {

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent1 = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent1);
            }
        });

        login.setOnClickListener(view -> {
            String user = username.getText().toString();
            String pass = password.getText().toString();


            if (user.isEmpty()) {
                username.setError("Data cannot be empty!");
                username.requestFocus();
            } else if (pass.isEmpty()) {
                password.setError("Data cannot be empty!");
                password.requestFocus();
            } else {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(user, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Login Success...", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(LoginActivity.this, Announcements.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this, "Login fail...", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

//                databaseReference.child("login").child(key).addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                        if (dataSnapshot.exists()) {
//                            dataLogin login = dataSnapshot.getValue(dataLogin.class);
//                            if (login != null) {
////                            Log.e("password","password0"+login.getUsername());
////                            Log.e("password1","password1"+key);
////                            Log.e("password2","password2"+login.getPassword());
//
//                                if (login.getUsername().equals(user)) {
//                                    if (login.getPassword().equals(pass)) {
//                                        preferences.setDataLogin(context, true);
//                                        preferences.setDataAs(context, "student");
//                                        Log.e("status", "hey student");
////                                        startActivity(new Intent(context, Announcements.class));
//                                    } else {
//                                        Toast.makeText(context, "Wrong Password", Toast.LENGTH_SHORT).show();
//                                    }
//                                } else {
//                                    Toast.makeText(context, "Wrong Username", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        } else {
//                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
            }

//            if(key != null)


        });



        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent1 = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent1);
            }
        });
    }

    private void loginforadmin() {
        login.setOnClickListener(view -> {
            String user = username.getText().toString();
            String pass = password.getText().toString();

            if (user.isEmpty()) {
                username.setError("Data cannot be empty!");
                username.requestFocus();
            } else if (pass.isEmpty()) {
                password.setError("Data cannot be empty!");
                password.requestFocus();
            } else if (user.equals("admin@gmail.com") && pass.equals("123456")) {
                preferences.setDataLogin(context, true);
                preferences.setDataAs(context, "admin");
                startActivity(new Intent(context, SelsectionActivity.class));
            } else {
                Toast.makeText(context, "You Are Not A Admin", Toast.LENGTH_LONG).show();
            }


//            databaseReference.child("login").child(user).addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    if (dataSnapshot.exists()) {
//                        dataLogin login = dataSnapshot.getValue(dataLogin.class);
//                        if (login != null) {
//                            if (login.getPassword().equals(pass)) {
//                                if (login.getAs().equals("admin")) {
////                                    if (active.isChecked()) {
//                                        preferences.setDataLogin(context, true);
//                                        preferences.setDataAs(context, "admin");
//                                        Log.e("status","hey admin");
////                                    }
//                                    startActivity(new Intent(context, MainActivity2.class));
//                                }
////                                if (active.isChecked()) {
////                                    if (active.isChecked()) {
////                                        preferences.setDataLogin(context, true);
////                                        preferences.setDataAs(context, "student");
////                                        Log.e("status","hey student");
////                                    }
////                                    startActivity(new Intent(context, Announcements.class));
////                                }
//                            } else {
//                                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    } else {
//                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
        });
    }

}