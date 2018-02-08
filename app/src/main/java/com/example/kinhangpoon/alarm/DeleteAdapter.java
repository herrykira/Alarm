package com.example.kinhangpoon.alarm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by KinhangPoon on 7/2/2018.
 */

public class DeleteAdapter extends BaseAdapter {
    ArrayList<String> hours,minues;
    Context cts;
    LayoutInflater inflater;
    TimeDbHelper timeDbHelper;
    SQLiteDatabase database;
    boolean checked;

    public DeleteAdapter(ArrayList<String> hours, ArrayList<String> minues,Context cts,TimeDbHelper timeDbHelper,SQLiteDatabase database,boolean checked ) {
        this.hours = hours;
        this.minues = minues;
        this.cts = cts;
        this.database = database;
        this.timeDbHelper = timeDbHelper;
        this.checked = checked;

        inflater = (LayoutInflater) cts.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return hours.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder{
        TextView timeView;
        CheckBox checkBox;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();

        if(convertView==null){
            convertView = inflater.inflate(R.layout.delete_item_show,null);
            holder.timeView = convertView.findViewById(R.id.alarmTimeDelete);
            holder.checkBox = convertView.findViewById(R.id.checkBoxSelect);
            convertView.setTag(holder);
        }
        else{
        holder = (ViewHolder) convertView.getTag();
        }

        holder.timeView.setText(hours.get(position)+" : "+minues.get(position));

        if(checked){
            holder.checkBox.setChecked(true);
            ContentValues values = new ContentValues();
            values.put(timeDbHelper.HOUR,hours.get(position));
            values.put(timeDbHelper.MINUTE,minues.get(position));
            values.put(timeDbHelper.CHECKBOX,"True");
            long update = database.update(timeDbHelper.TABLE_NAME, values, timeDbHelper.HOUR + " == \"" + hours.get(position) + "\"" + " and "+timeDbHelper.MINUTE+" == \""+minues.get(position)+"\"", null);
//            if(update !=-1){
//                Toast.makeText(cts,"All is True",Toast.LENGTH_SHORT).show();
//            }

        }
        else{
            holder.checkBox.setChecked(false);
            ContentValues values = new ContentValues();
            values.put(timeDbHelper.HOUR,hours.get(position));
            values.put(timeDbHelper.MINUTE,minues.get(position));
            values.put(timeDbHelper.CHECKBOX,"False");
            long update = database.update(timeDbHelper.TABLE_NAME, values, timeDbHelper.HOUR + " == \"" + hours.get(position) + "\"" + " and "+timeDbHelper.MINUTE+" == \""+minues.get(position)+"\"", null);
//            if(update !=-1){
//                Toast.makeText(cts,"All is False",Toast.LENGTH_SHORT).show();
//            }
        }

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ContentValues values = new ContentValues();
                values.put(timeDbHelper.HOUR,hours.get(position));
                values.put(timeDbHelper.MINUTE,minues.get(position));
                if(isChecked){
                    values.put(timeDbHelper.CHECKBOX,"True");
                    long update = database.update(timeDbHelper.TABLE_NAME, values, timeDbHelper.HOUR + " == \"" + hours.get(position) + "\"" + " and "+timeDbHelper.MINUTE+" == \""+minues.get(position)+"\"", null);
                    if(update !=-1){
                        Toast.makeText(cts,"True",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    values.put(timeDbHelper.CHECKBOX,"False");
                    long update = database.update(timeDbHelper.TABLE_NAME, values, timeDbHelper.HOUR + " == \"" + hours.get(position) + "\"" + " and "+timeDbHelper.MINUTE+" == \""+minues.get(position)+"\"", null);
                    if(update !=-1){
                        Toast.makeText(cts,"False",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return convertView;
    }
}
