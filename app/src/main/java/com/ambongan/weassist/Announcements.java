package com.ambongan.weassist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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

public class Announcements extends AppCompatActivity {

    TextView receiver_msg;
    private Button chatbot;
    Toolbar toolbar;
//    FloatingActionButton fabCreate;

    private DatabaseReference db;
    private RecyclerView rvView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList <ItemModel> listItem;


    String currentDateAndTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcements);

        Log.e("status","hey student Activity");

        chatbot = findViewById(R.id.chatbot);
        // toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rvView = (RecyclerView) findViewById(R.id.rv_main);
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

        chatbot.setOnClickListener(view -> {
            Intent intent = new Intent(Announcements.this, MainActivity.class);
            startActivity(intent);
        });
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

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

    private void aboutItemClicked() {
        Intent intent = new Intent(Announcements.this, AboutActivityAdmin.class);
        startActivity(intent);
    }

    private void feedbackItemClicked() {
        Intent intent = new Intent(Announcements.this, FeedbackActivityAdmin.class);
        startActivity(intent);
    }

    private void logoutItemClick() {
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();
        preferences.setDataLogin(this, false);
        Intent intent = new Intent(Announcements.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void closeApplication() {
        finishAffinity();
        System.exit(0);
    }

    public void listData() {
        /* inisiasi dan mengambil firebase database reference */
        db = FirebaseDatabase.getInstance().getReference();

        /* mengambil data dari firebase realtime database */
        db.child("item").orderByChild("key").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                /* data ada */
                listItem = new ArrayList<>();
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {

                    ItemModel itemModel = noteDataSnapshot.getValue( ItemModel.class);
                    itemModel.setKey(noteDataSnapshot.getKey());

                    listItem.add(itemModel);

//                    if (itemModel.getTime().compareTo(itemModel.getExpireTime())>0){
//                        listItem.remove(itemModel);
//                    }

                    Long timeBackendLong = Long.valueOf(currentDateAndTime);
                    Long expireTimeBackendLong = Long.valueOf(itemModel.getExpireTimeBackend());

                    if (timeBackendLong>expireTimeBackendLong || timeBackendLong.equals(expireTimeBackendLong)){
                        listItem.remove(itemModel);
                    }
                }

                adapter = new  AnnouncementAdapter(listItem, Announcements.this);
                rvView.setAdapter(adapter);


            }
 



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("onCancelledList", databaseError.getDetails() + " " + databaseError.getMessage());
            }
        });

    }

}



