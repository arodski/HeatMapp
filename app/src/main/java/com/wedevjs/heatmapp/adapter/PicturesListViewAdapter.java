package com.wedevjs.heatmapp.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.wedevjs.heatmapp.R;

import java.util.ArrayList;

/**
 * Created by marco on 11/13/14.
 */
public class PicturesListViewAdapter extends ArrayAdapter<String> {
    Activity context;
    ArrayList<String> user;
    ArrayList<String> time;

    public PicturesListViewAdapter(Activity context, ArrayList<String> user, ArrayList<String> time) {
        super(context, R.layout.picture_item, user);
        this.context = context;
        this.user = user;
        this.time = time;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.picture_item, null, true);
        TextView txt_user = (TextView) rowView.findViewById(R.id.by_name_label);
        TextView txt_time = (TextView) rowView.findViewById(R.id.ago_numer_label);
        txt_user.setText(user.get(position));
        txt_time.setText(time.get(position));
        return rowView;
    }
}
