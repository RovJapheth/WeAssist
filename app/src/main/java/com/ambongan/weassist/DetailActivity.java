package com.ambongan.weassist;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetailActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView tvTitle;
    TextView tvWho;
    TextView tvWhat;
    TextView tvWhen;
    TextView tvWhere;

    com.ambongan.weassist.ItemModel itemModel;

    private DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

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

        // Receive data from adapter
        itemModel = ( com.ambongan.weassist.ItemModel) getIntent().getSerializableExtra("data");
        if (itemModel != null) {

            tvTitle.setText(itemModel.getTitle());
            tvWho.setText(itemModel.getWho());
            tvWhat.setText(itemModel.getWhat());
            tvWhen.setText(itemModel.getWhen());
            tvWhere.setText(itemModel.getWhere());
        }
    }

    public static Intent getActIntent(Activity activity) {
        return new Intent(activity, DetailActivity.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.edit) {

            Intent intent = new Intent(this,  com.ambongan.weassist.EditActivity.class);
            intent.putExtra("key", itemModel.getKey());
            intent.putExtra("title", itemModel.getTitle());
            intent.putExtra("who", itemModel.getWho());
            intent.putExtra("what", itemModel.getWhat());
            intent.putExtra("when", itemModel.getWhen());
            intent.putExtra("where", itemModel.getWhere());
            intent.putExtra("time", itemModel.getTime());
            intent.putExtra("timeBackend", itemModel.getTimeBackend());
            intent.putExtra("expTime", itemModel.getExpireTime());
            intent.putExtra("expTimeBackend", itemModel.getExpireTimeBackend());
            startActivity(intent);
            finish();
        }
        else {
            deleteBox();
            Log.d("onOptionsItemSelected", "onOptionsItemSelected: hello");
        }


        return super.onOptionsItemSelected(item);
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
        db.child("item")
                .child(itemModel.getKey())
                .removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(DetailActivity.this, "Announcement Deleted!", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(DetailActivity.this, MainActivity2.class));
                        finish();
                    }
                });
    }
}
