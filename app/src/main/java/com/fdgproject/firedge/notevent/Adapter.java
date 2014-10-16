package com.fdgproject.firedge.notevent;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Firedge on 29/09/2014.
 */
public class Adapter extends ArrayAdapter<Event> {

    private Context cnt;
    private ArrayList<Event> evt;
    private int rec;
    private static LayoutInflater lin;

    public static class ViewHolder{
        public TextView tv1, tv2, tv3;
    }

    public Adapter(Context context, int resource, ArrayList<Event> objects) {
        super(context, resource, objects);
        this.cnt = context;
        this.evt = objects;
        this.rec = resource;
        this.lin = (LayoutInflater)cnt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if(convertView == null) {
            convertView = lin.inflate(rec, null);
            vh =new ViewHolder();
            vh.tv1 = (TextView)convertView.findViewById(R.id.tv_day);
            vh.tv2 = (TextView)convertView.findViewById(R.id.tv_month);
            vh.tv3 = (TextView)convertView.findViewById(R.id.tv_title);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder)convertView.getTag();
        }
        vh.tv1.setText(Integer.toString(evt.get(position).getDay()));
        vh.tv2.setText(evt.get(position).getMonth());
        String texto = evt.get(position).getText();
        if(texto.length()>18)
            vh.tv3.setText(texto.substring(0,18)+"...");
        else
            vh.tv3.setText(texto);
        return convertView;
    }
}
