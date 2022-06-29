package com.ambongan.weassist;

import static java.text.DateFormat.getDateTimeInstance;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.text.format.DateFormat;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

//import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CreateActivity extends AppCompatActivity implements View.OnClickListener {

    // refers variable Firebase Realtime Database
    private DatabaseReference db;

    private EditText itemTitle;
    private EditText itemWho;
    private EditText itemWhat;
    private EditText itemWhen;
    private EditText itemWhere;

    private Button btnCreate, expireTimeBtn;
    private TextView expiryDate;
    Toolbar toolbar;

    final Calendar myCalendar = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener datePickerDialog;

    private int mHour, mMinute;

    String chooseDateTxt, choosedateTxtBackend, chooseTimeTxt, chooseTimeTxtBackend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        // toolbar
        toolbar = findViewById(R.id.tlCreate);
        toolbar.setTitle("Create Announcements");

        // initialize
        itemTitle   = (EditText) findViewById(R.id.etItemTitle);
        itemWho = (EditText) findViewById(R.id.etItemWho);
        itemWhat  = (EditText) findViewById(R.id.etItemWhat);
        itemWhen = (EditText) findViewById(R.id.etItemWhen);
        itemWhere = (EditText) findViewById(R.id.etItemWhere);
        btnCreate   = (Button) findViewById(R.id.btnCreate);
        expireTimeBtn   = (Button) findViewById(R.id.expireTimeBtn);
        expiryDate   =  findViewById(R.id.expiryDate);
        db          = FirebaseDatabase.getInstance().getReference();

        btnCreate.setOnClickListener(this);
        expireTimeBtn.setOnClickListener(this);


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
        if (v == btnCreate) {
            save();
        }
        if (v == expireTimeBtn) {
            new DatePickerDialog(CreateActivity.this, datePickerDialog, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();

        }
    }

    private void openTimePickerDialog() {
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(CreateActivity.this,
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
                    }
                }, mHour, mMinute, true);
        timePickerDialog.show();
    }

    public void save() {
        String title     = itemTitle.getText().toString();
        String who    = itemWho.getText().toString();
        String what    = itemWhat.getText().toString();
        String when    = itemWhen.getText().toString();
        String where    = itemWhere.getText().toString();

        String expiryTime = expiryDate.getText().toString();

//        String expiryTime = chooseDateTxt + " "+chooseTimeTxt;
        String expiryTimeBackend = choosedateTxtBackend+chooseTimeTxtBackend;

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyyMMddHHmm");
        Date date = new Date();

        String currentDate = formatter.format(date);
        String currentDateForBackend = formatter2.format(date);

        if (title.isEmpty()){
            Toast.makeText(this, "Please Enter Title", Toast.LENGTH_LONG).show();
        } else if (who.isEmpty()){
            Toast.makeText(this, "Please Enter Who", Toast.LENGTH_LONG).show();
        }else if (what.isEmpty()){
            Toast.makeText(this, "Please Enter What", Toast.LENGTH_LONG).show();
        }else if (when.isEmpty()){
            Toast.makeText(this, "Please Enter When", Toast.LENGTH_LONG).show();
        }else if (where.isEmpty()){
            Toast.makeText(this, "Please Enter Where", Toast.LENGTH_LONG).show();
        } else if (expiryTime.isEmpty()){
            Toast.makeText(this, "Please Choose Expiry Date", Toast.LENGTH_LONG).show();
        } else {

            com.ambongan.weassist.ItemModel itemModel = new  com.ambongan.weassist.ItemModel(title, who, what, when, where, currentDate, currentDateForBackend,expiryTime, expiryTimeBackend);

            db.child("item").push().setValue(itemModel).addOnSuccessListener(this, new OnSuccessListener<Void>() {

                @Override
                public void onSuccess(Void aVoid) {
//                    itemTitle.setText("");
//                    itemInfo.setText("");

                    Toast.makeText(CreateActivity.this, "Success Save Data", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(CreateActivity.this, MainActivity2.class);
                    startActivity(intent);
                    finish();
//                    finish();
                }

            } );
        }

    }



}
