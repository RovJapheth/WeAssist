package com.ambongan.weassist;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EditActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    public String key;

    private DatabaseReference db;
    private TextView etItemTitle, expiryDate;
    private TextView etItemWho;
    private TextView etItemWhat;
    private TextView etItemWhen;
    private TextView etItemWhere;
    private Button btnUpdate, expiryBtn;

    final Calendar myCalendar = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener datePickerDialog;

    String chooseDateTxt, choosedateTxtBackend, chooseTimeTxt, chooseTimeTxtBackend;

    String expTimeBackend;

    private int mHour, mMinute;

    public static Intent getActIntent(Activity activity) {
        return new Intent(activity, EditActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        //Toolbar
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Edit");

        // Receive Data from intent
        Intent intent = getIntent();
        key             = intent.getStringExtra("key");
        String title     = intent.getStringExtra("title");
        String who    = intent.getStringExtra("who");
        String what    = intent.getStringExtra("what");
        String when    = intent.getStringExtra("when");
        String where    = intent.getStringExtra("where");
        String time    = intent.getStringExtra("time");
        String timeBackend    = intent.getStringExtra("timeBackend");
        String expTime    = intent.getStringExtra("expTime");
        expTimeBackend    = intent.getStringExtra("expTimeBackend");

        // initial firebase
        db = FirebaseDatabase.getInstance().getReference();

        // initial
        etItemTitle     = (TextInputEditText) findViewById(R.id.etItemTitle);
        etItemWho  = (TextInputEditText) findViewById(R.id.etItemWho);
        etItemWhat  = (TextInputEditText) findViewById(R.id.etItemWhat);
        etItemWhen  = (TextInputEditText) findViewById(R.id.etItemWhen);
        etItemWhere   = (TextInputEditText) findViewById(R.id.etItemWhere);
        expiryDate   = (TextView) findViewById(R.id.expiryDate);
        btnUpdate       = (Button) findViewById(R.id.btnUpdate);
        expiryBtn       = (Button) findViewById(R.id.expiryBtn);

        // setText
        etItemTitle.setText(title);
        etItemWho.setText(who);
        etItemWhat.setText(what);
        etItemWhen.setText(when);
        etItemWhere.setText(where);
        expiryDate.setText(expTime);

        btnUpdate.setOnClickListener(this);
        expiryBtn.setOnClickListener(this);

        datePickerDialog = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);

                updateLable();
            }
        };
    }

    private void updateLable() {

        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
//            chooseDateTxt.setText(dateFormat.format(myCalendar.getTime()));
        chooseDateTxt = dateFormat.format(myCalendar.getTime());

        //for backend
        String myFormat2 = "yyyyMMdd";
        SimpleDateFormat dateFormat2 = new SimpleDateFormat(myFormat2, Locale.US);
        choosedateTxtBackend = dateFormat2.format(myCalendar.getTime());

        // Open TimePickerDialog
        openTimePickerDialog();
    }

    @Override
    public void onClick(View v) {
        if (v == btnUpdate) {
            updateItem();
        }
        if (v == expiryBtn) {
            new DatePickerDialog(EditActivity.this, datePickerDialog, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            
        }
    }

    private void openTimePickerDialog() {
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(EditActivity.this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        NumberFormat numberFormat = new DecimalFormat("00");
                        Long lHoursOfDay = Long.valueOf(hourOfDay);
                        Long lMinute = Long.valueOf(minute);

//                        chooseTimeTxt.setText(numberFormat.format(lHoursOfDay) + numberFormat.format(lMinute)); //  we also can use this
//                        chooseTimeTxt.setText(String.format("%1$02d : %2$02d", hourOfDay, minute)); // for zem(hour : minute) format
//                        chooseTimeTxt.setText(String.format("%1$02d%2$02d", hourOfDay, minute)); // for hourminute format

                        chooseTimeTxt = String.format("%1$02d:%2$02d", hourOfDay, minute);
                        chooseTimeTxtBackend = String.format("%1$02d%2$02d", hourOfDay, minute);

                        expiryDate.setText(chooseDateTxt + " "+chooseTimeTxt);
//                        expiryDate.setText(choosedateTxtBackend+chooseTimeTxtBackend);
                        expTimeBackend = choosedateTxtBackend+chooseTimeTxtBackend;
                    }
                }, mHour, mMinute, true);
        timePickerDialog.show();
    }

    public void updateItem() {
        String title     = etItemTitle.getText().toString();
        String who    = etItemWho.getText().toString();
        String what    = etItemWhat.getText().toString();
        String when    = etItemWhen.getText().toString();
        String where    = etItemWhere.getText().toString();
        String expireTime    = expiryDate.getText().toString();

//        String expiryTime = expiryDate.getText().toString();

//        String expiryTime = chooseDateTxt + " "+chooseTimeTxt;
//        String expiryTimeBackend = choosedateTxtBackend+chooseTimeTxtBackend;
        String expiryTimeBackend = expTimeBackend;

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyyMMddHHmm");
        Date date = new Date();

        String currentDate = formatter.format(date);
        String currentDateForBackend = formatter2.format(date);

        if (title.isEmpty()){
            Toast.makeText(this, "Please Enter Title", Toast.LENGTH_SHORT).show();
        } else if (who.isEmpty()){
            Toast.makeText(this, "Please Enter who", Toast.LENGTH_SHORT).show();
        } else if (what.isEmpty()){
            Toast.makeText(this, "Please Enter what", Toast.LENGTH_SHORT).show();
        } else if (when.isEmpty()){
            Toast.makeText(this, "Please Enter when", Toast.LENGTH_SHORT).show();
        } else if (where.isEmpty()){
            Toast.makeText(this, "Please Enter where", Toast.LENGTH_SHORT).show();
        } else if (expireTime.isEmpty()){
            Toast.makeText(this, "Please Choose Expiry Date", Toast.LENGTH_SHORT).show();
        }

        ItemModel itemModel = new ItemModel(title, who, what, when, where, currentDate, currentDateForBackend, expireTime, expiryTimeBackend);
        db.child("item")
                .child(key)
                .setValue(itemModel)
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(EditActivity.this, "Success Update Data", Toast.LENGTH_LONG).show();

                        startActivity(new Intent(EditActivity.this, MainActivity2.class));
                        finish();
                    }
                });
    }


}
