package com.ambongan.weassist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetailActivityUser extends AppCompatActivity {

    Toolbar toolbar;
    TextView tvTitle;
    TextView tvWho;
    TextView tvWhat;
    TextView tvWhen;
    TextView tvWhere;
    TextView tvdate;

    com.ambongan.weassist.ItemModel itemModel;

    private DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_user);

        // toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // initial firebase
        db = FirebaseDatabase.getInstance().getReference();

        // initial
        tvTitle  = (TextView) findViewById(R.id.tvTitle);
        tvWho = (TextView) findViewById(R.id.tvWho);
        tvWhat = (TextView) findViewById(R.id.tvWhat);
        tvWhen = (TextView) findViewById(R.id.tvWhen);
        tvWhere = (TextView) findViewById(R.id.tvWhere);
        tvdate = (TextView) findViewById(R.id.tvdate);

        // Receive data from adapter
        itemModel = ( com.ambongan.weassist.ItemModel) getIntent().getSerializableExtra("data");
        if (itemModel != null) {

            tvTitle.setText(itemModel.getTitle());
            tvdate.setText(itemModel.getTime());
            tvWho.setText(itemModel.getWho());
            tvWhat.setText(itemModel.getWhat());
            tvWhen.setText(itemModel.getWhen());
            tvWhere.setText(itemModel.getWhere());

        }
    }

    public static Intent getActIntent(Activity activity) {
        return new Intent(activity, DetailActivityUser.class);
    }



}