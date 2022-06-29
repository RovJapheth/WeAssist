package com.ambongan.weassist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ambongan.weassist.services.preferences;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    FloatingActionButton fabCreate;

    private DatabaseReference db;
    private RecyclerView rvView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList< com.ambongan.weassist.ItemModel> listItem;

    String currentDateAndTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setTitle("Admin");

        rvView      = (RecyclerView) findViewById(R.id.rv_main);
        rvView.setHasFixedSize(true);
//        layoutManager = new LinearLayoutManager(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        rvView.setLayoutManager(linearLayoutManager);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmm");
        Date date = new Date();
        currentDateAndTime = formatter.format(date);

        listData();

        // create
        fabCreate = findViewById(R.id.fabCreate);
        fabCreate.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu_admin, menu);
        return true;
    }
    /*
        To handle the click of option menu items
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.aboutItem:
                aboutItemClicked();
                break;
            case R.id.feedbackItem:
                feedbackItemClicked();
                break;
            case R.id.logoutItem:
                logoutItemClick();
                break;
            case R.id.closeItem:
                closeApplication();
                break;
        }
        return true;
    }
    /*
        Closes the enitre application
     */
    private void closeApplication() {
        finishAffinity();
        System.exit(0);
    }

    /*
        To Logout from the application and not Close.
     */
    private void logoutItemClick() {
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();
        sendToLoginActivity();
    }
    /*
        To send user to the login page.
     */
    private void sendToLoginActivity() {
        Log.e("status","login");
        preferences.setDataLogin(this, false);
        Intent intent = new Intent(MainActivity2.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }
    /*
        Send user to the feedback page.
     */
    private void feedbackItemClicked() {
        Intent intent = new Intent(MainActivity2.this, FeedbackActivityAdmin.class);
        startActivity(intent);
    }
    /*
        Show the team details to the user.
     */
        private void aboutItemClicked() {
            Intent intent = new Intent(MainActivity2.this, AboutActivityAdmin.class);
            startActivity(intent);
        }

    @Override
    public void onClick(View v) {
        if (v == fabCreate) {
            startActivity(new Intent(this, com.ambongan.weassist.CreateActivity.class));
            finish();
        }
    }

    public void listData() {
        /* inisiasi dan mengambil firebase database reference */
        db = FirebaseDatabase.getInstance().getReference();

        /* mengambil data dari firebase realtime database */
        db.child("item").orderByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                /* data ada */
                listItem = new ArrayList<>();
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {


                    com.ambongan.weassist.ItemModel itemModel = noteDataSnapshot.getValue( com.ambongan.weassist.ItemModel.class);
                    itemModel.setKey(noteDataSnapshot.getKey());

                    listItem.add(itemModel);

//                    Long timeBackendLong = Long.valueOf(itemModel.getTimeBackend());
                    Long timeBackendLong = Long.valueOf(currentDateAndTime);
                    Long expireTimeBackendLong = Long.valueOf(itemModel.getExpireTimeBackend());

                    if (timeBackendLong>expireTimeBackendLong || timeBackendLong.equals(expireTimeBackendLong)){
                        listItem.remove(itemModel);
                    }
                }


                adapter = new  com.ambongan.weassist.MainActivityAdapter(listItem, MainActivity2.this);
                rvView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("onCancelledList", databaseError.getDetails() + " " + databaseError.getMessage());
            }
        });
    }
}
