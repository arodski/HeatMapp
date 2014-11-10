package com.wedevjs.heatmapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by marco on 11/10/14.
 */
public class CustomListViewAdapter extends ArrayAdapter<String>  {

    Activity context;
    ArrayList<String> name;
    ArrayList<String> pictures;
    ArrayList<String> views;
    ArrayList<String> people;

    public CustomListViewAdapter(Activity context, ArrayList<String> name, ArrayList<String> pictures, ArrayList<String> views, ArrayList<String> people) {
        super(context, R.layout.place_item, name);
        this.context = context;
        this.name = name;
        this.pictures = pictures;
        this.views = views;
        this.people = people;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.place_item, null, true);
        TextView txt_name = (TextView) rowView.findViewById(R.id.name_label);
        TextView txt_pictures = (TextView) rowView.findViewById(R.id.pictures_number_label);
        TextView txt_views = (TextView) rowView.findViewById(R.id.views_number_label);
        TextView txt_people = (TextView) rowView.findViewById(R.id.people_number_label);
        txt_name.setText(name.get(position));
        txt_pictures.setText(pictures.get(position));
        txt_views.setText(views.get(position));
        txt_people.setText(people.get(position));
        return rowView;
    }
}
