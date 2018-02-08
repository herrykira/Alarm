package com.example.kinhangpoon.alarm;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by KinhangPoon on 6/2/2018.
 */

public class TimeAdapter extends BaseAdapter {
    ArrayList<String> hours,minues;
    Context cts;
    LayoutInflater inflater;
//    int version;

    public TimeAdapter( ArrayList<String> hours,ArrayList<String> minues,Context cts) {

        this.hours = hours;
        this.minues =minues;
        this.cts = cts;
//        this.version = version;

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
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        if(convertView==null){
//           if(version ==1){
               convertView = inflater.inflate(R.layout.alarm_item_show,null);
               holder.timeView = convertView.findViewById(R.id.alarmTime);
//           }
//           else{
//               convertView = inflater.inflate(R.layout.delete_item_show,null);
//               holder.timeView = (convertView.findViewById(R.id.alarmTimeDelete));
//           }
           convertView.setTag(holder);

        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.timeView.setText(hours.get(position)+" : "+minues.get(position));


        return convertView;
    }
}
