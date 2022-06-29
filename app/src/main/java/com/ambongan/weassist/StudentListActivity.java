package com.ambongan.weassist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentListActivity extends AppCompatActivity implements OnDeletClickListener {


    private static DatabaseReference db;
    private RecyclerView rvView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<StudentListModel> listItem;
    StudentListModel itemModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        rvView      = (RecyclerView) findViewById(R.id.rv_studentlist);
        db = FirebaseDatabase.getInstance().getReference("login");
        rvView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        rvView.setLayoutManager(layoutManager);

        itemModel = ( com.ambongan.weassist.StudentListModel) getIntent().getSerializableExtra("data");



//        listItem = new ArrayList<>();
//        adapter = new  com.ambongan.weassist.StudentListAdapter(listItem, StudentListActivity.this,this);
//        rvView.setAdapter(adapter);
//
//        db.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
//
//                    itemModel = dataSnapshot.getValue( com.ambongan.weassist.StudentListModel.class);
//                    listItem.add(itemModel);
//
//                }
//
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


        listData();
    }

    public void deleteBox() {
        AlertDialog.Builder alert   = new AlertDialog.Builder(this);
        alert.setMessage("Are You sure Delete ? ");

        alert.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delete();
                    }
                });
        alert.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //
                    }
                });

        AlertDialog alertDialog = alert.create();
        alertDialog.show();
    }

    public void delete() {
//        db.child("login")
//                .child(key)
//                .removeValue()
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Toast.makeText(StudentListActivity.this, "Announcement Deleted!", Toast.LENGTH_LONG).show();
//                        adapter.notifyDataSetChanged();
////                        startActivity(new Intent(StudentListActivity.this, MainActivity2.class));
//                    }
//                });
    }



    public void listData() {
        /* inisiasi dan mengambil firebase database reference */
        db = FirebaseDatabase.getInstance().getReference();

        /* mengambil data dari firebase realtime database */
        db.child("login").orderByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                /* data ada */
                listItem = new ArrayList<>();
                for (DataSnapshot noteDataSnapshot : dataSnapshot.getChildren()) {


                    com.ambongan.weassist.StudentListModel itemModel = noteDataSnapshot.getValue( com.ambongan.weassist.StudentListModel.class);
                    itemModel.setKey(noteDataSnapshot.getKey());

                    listItem.add(itemModel);
                }


                adapter = new  com.ambongan.weassist.StudentListAdapter(listItem, StudentListActivity.this, StudentListActivity.this);
                rvView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("onCancelledList", databaseError.getDetails() + " " + databaseError.getMessage());
            }
        });

    }


    @Override
    public void onDeletItemClick(String key) {

        AlertDialog.Builder alert   = new AlertDialog.Builder(this);
        alert.setMessage("Are You sure Delete ? ");

        alert.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.child("login")
                                .child(key)
                                .removeValue()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(StudentListActivity.this, "Data Deleted!", Toast.LENGTH_LONG).show();
                                        adapter.notifyDataSetChanged();
                                    }
                                });
                    }
                });
        alert.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //
                    }
                });

        AlertDialog alertDialog = alert.create();
        alertDialog.show();
    }
}