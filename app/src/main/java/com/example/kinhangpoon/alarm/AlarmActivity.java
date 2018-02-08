package com.example.kinhangpoon.alarm;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

public class AlarmActivity extends AppCompatActivity {
android.widget.TimePicker TimePicker;
    Button ButtonSave,ButtonCancel;
    TimeDbHelper timeDbHelper;
    SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        TimePicker = findViewById(R.id.timePicker);
        ButtonSave =findViewById(R.id.buttonSave);
        ButtonCancel = findViewById(R.id.buttonCancel);
        timeDbHelper = new TimeDbHelper(this);
        database = timeDbHelper.getWritableDatabase();

        ButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                int hour = TimePicker.getCurrentHour();
                int minute = TimePicker.getCurrentMinute();
                values.put(timeDbHelper.HOUR,hour+"");
                values.put(timeDbHelper.MINUTE,minute+"");
                values.put(timeDbHelper.CHECKBOX,"False");

                Cursor cursor = database.rawQuery("Select * from " + timeDbHelper.TABLE_NAME + " Where " + timeDbHelper.HOUR + " == \"" + hour + "\"" + " and "+timeDbHelper.MINUTE+" == \""+minute+"\"", null);

                if (cursor.getCount() == 0) {
                    long insert = database.insert(timeDbHelper.TABLE_NAME, null, values);
                    if (insert != -1) {
                        Toast.makeText(AlarmActivity.this, "Time saved", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(AlarmActivity.this, "Already saved", Toast.LENGTH_LONG).show();

                }
                Intent i = new Intent(AlarmActivity.this,MainActivity.class);
                startActivity(i);
            }
        });

        ButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AlarmActivity.this,MainActivity.class);
                startActivity(i);
            }
        });
    }
}
