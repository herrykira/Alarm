package com.example.kinhangpoon.alarm;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    Button CreateButton,DeleteButton;
    TimeAdapter timeAdapter;
    TimeDbHelper timeDbHelper;
    SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listview);
        CreateButton = findViewById(R.id.buttonCreate);
        DeleteButton = findViewById(R.id.buttonDelete);
        timeDbHelper = new TimeDbHelper(this);
        database = timeDbHelper.getWritableDatabase();
        //show all time from database
        Cursor cursor = database.rawQuery("Select * from " + timeDbHelper.TABLE_NAME , null);
        cursor.moveToFirst();
        //initialize two Array lists
        ArrayList<String> hours = new ArrayList<>();
        ArrayList<String> minutes = new ArrayList<>();
        do {
            if(cursor.getCount()==0){
                break;
            }
            // Get hour and minute from database
            String hour = cursor.getString(cursor.getColumnIndex(timeDbHelper.HOUR));
            String minute = cursor.getString(cursor.getColumnIndex(timeDbHelper.MINUTE));
            //save hour and minute into ArrayList
            hours.add(hour);
            minutes.add(minute);

        }while(cursor.moveToNext());

        timeAdapter = new TimeAdapter(hours,minutes,MainActivity.this);
        listView.setAdapter(timeAdapter);

        // go to create alarm
        CreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,AlarmActivity.class);
                startActivity(i);
            }
        });

        // go to delete alarm
        DeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,DeleteActivity.class);
                startActivity(i);
            }
        });

    }
}
