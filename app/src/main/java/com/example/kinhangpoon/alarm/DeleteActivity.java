package com.example.kinhangpoon.alarm;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class DeleteActivity extends AppCompatActivity {
    ListView listViewDelete;
    Button deleteButton,CancelButton,ViewButton;
    CheckBox selectall;
    DeleteAdapter deleteAdapter;
    TimeDbHelper timeDbHelper;
    SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        listViewDelete = findViewById(R.id.listviewDelete);
        deleteButton = findViewById(R.id.buttonDelete2);
        CancelButton = findViewById(R.id.buttonCanceldelete);
        selectall = findViewById(R.id.SelectAll);
        ViewButton = findViewById(R.id.buttonView);
        timeDbHelper = new TimeDbHelper(this);
        database = timeDbHelper.getWritableDatabase();

        // show all alarms from database
        Cursor cursor = database.rawQuery("Select * from " + timeDbHelper.TABLE_NAME , null);
        cursor.moveToFirst();
        final ArrayList<String> hours = new ArrayList<>();
        final ArrayList<String> minutes = new ArrayList<>();
        do {
            if(cursor.getCount()==0){
                break;
            }
            String hour = cursor.getString(cursor.getColumnIndex(timeDbHelper.HOUR));
            String minute = cursor.getString(cursor.getColumnIndex(timeDbHelper.MINUTE));
            hours.add(hour);
            minutes.add(minute);

        }while(cursor.moveToNext());
        deleteAdapter= new DeleteAdapter(hours,minutes,DeleteActivity.this,timeDbHelper,database,false);
        listViewDelete.setAdapter(deleteAdapter);

        // check whether selectall checkbox is checked or not
        selectall.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    deleteAdapter= new DeleteAdapter(hours,minutes,DeleteActivity.this,timeDbHelper,database,true);
                }
                else{
                    deleteAdapter= new DeleteAdapter(hours,minutes,DeleteActivity.this,timeDbHelper,database,false);
                }
                listViewDelete.setAdapter(deleteAdapter);
            }
        });

         deleteButton.setOnClickListener(new View.OnClickListener() {

             @Override
             public void onClick(View v) {
                 AlertDialog.Builder builder = new AlertDialog.Builder(DeleteActivity.this);
                 builder.setTitle("Alert");
                 builder.setMessage("Are you sure ?");
                 builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {
                         long delete = database.delete(timeDbHelper.TABLE_NAME, timeDbHelper.CHECKBOX + " == \"True\"", null);
                         if(delete!=0){
                             Toast.makeText(DeleteActivity.this,"Deleted Successfully",Toast.LENGTH_SHORT).show();
                         }
                         Intent i = new Intent(DeleteActivity.this,MainActivity.class);
                         startActivity(i);
                     }
                 });
                 builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {

                     }
                 });
                 builder.show();
             }
         });

         CancelButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent i = new Intent(DeleteActivity.this,MainActivity.class);
                 startActivity(i);
             }
         });

         ViewButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Cursor res = database.rawQuery("select * from "+timeDbHelper.TABLE_NAME,null);
                 // No data
                 if (res.getCount() == 0) {
                     showMessage("Error", "No data found");
                     return;
                 }
                 StringBuffer buffer = new StringBuffer();
                 while (res.moveToNext()) {
                     buffer.append(timeDbHelper.KEY_ID + " : " + res.getString(0) + "\n");
                     buffer.append(timeDbHelper.HOUR + " : " + res.getString(1) + "\n");
                     buffer.append(timeDbHelper.MINUTE + " : " + res.getString(2) + "\n");
                     buffer.append(timeDbHelper.CHECKBOX + " : " + res.getString(3) + "\n");
                 }
                 showMessage("Data", buffer.toString());
             }
         });
    }
    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(DeleteActivity.this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}
