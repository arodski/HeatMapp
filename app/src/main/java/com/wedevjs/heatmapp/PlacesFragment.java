package com.wedevjs.heatmapp;

import android.app.Fragment;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.wedevjs.heatmapp.adapter.PlacesListViewAdapter;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by marco on 11/12/14.
 */
public class PlacesFragment extends Fragment {

    public PlacesFragment(){}

    ArrayList<String> name = new ArrayList<String>();
    ArrayList<String> pictures = new ArrayList<String>();
    ArrayList<String> views = new ArrayList<String>();
    ArrayList<String> people = new ArrayList<String>();
    ArrayList<String> photos = new ArrayList<String>();
    ListView listView;
    PlacesListViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        TypedArray pictures_arr;

        View rootView = inflater.inflate(R.layout.fragment_places, container, false);

        name.addAll(Arrays.asList(getResources().getStringArray(R.array.name)));
        pictures.addAll(Arrays.asList(getResources().getStringArray(R.array.pictures)));
        views.addAll(Arrays.asList(getResources().getStringArray(R.array.views)));
        people.addAll(Arrays.asList(getResources().getStringArray(R.array.people)));
        photos.addAll(Arrays.asList(getResources().getStringArray(R.array.places_pictures)));

        pictures_arr = getResources().obtainTypedArray(R.array.places_pictures);

        for(int i = 0; i < pictures_arr.length(); i++){
            photos.add(i, String.valueOf(pictures_arr.getResourceId(i, -1)));
        }

        adapter = new PlacesListViewAdapter(getActivity(), name, pictures, views, people, photos);
        listView = (ListView) rootView.findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView txt_name = (TextView) view.findViewById(R.id.name_label);
                TextView txt_people = (TextView) view.findViewById(R.id.people_number_label);

                String placeName = txt_name.getText().toString();
                String peopleNumber = txt_people.getText().toString();
                Intent intent = new Intent(getActivity(), PicturesActivity.class);
                intent.putExtra("name", placeName);
                intent.putExtra("people", peopleNumber);
                startActivity(intent);
            }
        });

        return rootView;
    }
}
