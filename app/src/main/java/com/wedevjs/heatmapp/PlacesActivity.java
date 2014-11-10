package com.wedevjs.heatmapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;


public class PlacesActivity extends Activity {

    ArrayList<String> name = new ArrayList<String>();
    ArrayList<String> pictures = new ArrayList<String>();
    ArrayList<String> views = new ArrayList<String>();
    ArrayList<String> people = new ArrayList<String>();
    ListView listView;
    CustomListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);

        name.addAll(Arrays.asList(getResources().getStringArray(R.array.name)));
        pictures.addAll(Arrays.asList(getResources().getStringArray(R.array.pictures)));
        views.addAll(Arrays.asList(getResources().getStringArray(R.array.views)));
        people.addAll(Arrays.asList(getResources().getStringArray(R.array.people)));

        adapter = new CustomListViewAdapter(this, name, pictures, views, people);
        listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(this, PicturesActivity.class);
//                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.places, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
