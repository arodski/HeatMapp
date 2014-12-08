package com.wedevjs.heatmapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wedevjs.heatmapp.adapter.PicturesListViewAdapter;

import java.util.ArrayList;
import java.util.Arrays;


public class PicturesActivity extends FragmentActivity {

    ArrayList<String> user = new ArrayList<String>();
    ArrayList<String> time = new ArrayList<String>();
    ListView listView;
    PicturesListViewAdapter adapter;
    boolean mightGo = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pictures);

        user.addAll(Arrays.asList(getResources().getStringArray(R.array.user)));
        time.addAll(Arrays.asList(getResources().getStringArray(R.array.time)));

        adapter = new PicturesListViewAdapter(this, user, time);
        listView = (ListView) findViewById(R.id.list_pictures);
        listView.setAdapter(adapter);

        TextView people_now = (TextView) findViewById(R.id.people_now_number_label);

        Intent intent = getIntent();
        String placeName = intent.getStringExtra("name");
        String peopleNumber = intent.getStringExtra("people");
        setTitle(placeName);
        people_now.setText(peopleNumber);

        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void changeMightGo(View view){
        RelativeLayout mg_button = (RelativeLayout) findViewById(R.id.might_go_button);
        TextView going_people = (TextView) findViewById(R.id.might_go_number_label);
        TextView button_text = (TextView) findViewById(R.id.might_go_button_label);
        ImageView mg_icon = (ImageView) findViewById(R.id.might_go_icon);
        TextView mg_label = (TextView) findViewById(R.id.might_go_button_label);
        int going_number = Integer.valueOf(going_people.getText().toString());

        if(!mightGo){
            mightGo = true;

            mg_button.setBackgroundColor(getResources().getColor(R.color.buttonSelected));
            mg_button.invalidate();

            going_number += 1;
            going_people.setText(String.valueOf(going_number));

            mg_icon.setImageResource(R.drawable.ic_might_go_selected);
            mg_label.setTextColor(getResources().getColor(R.color.buttonTextSelected));
            button_text.setText("YOU'RE GOING");


            FragmentManager fm = getSupportFragmentManager();
            ShareDialog share = new ShareDialog();
            share.show(fm, "fragment_share");
        }
        else {
            mightGo = false;

            mg_button.setBackgroundColor(getResources().getColor(R.color.buttonUnselected));
            mg_button.invalidate();

            going_number -= 1;
            going_people.setText(String.valueOf(going_number));

            mg_icon.setImageResource(R.drawable.ic_might_go_unselected);
            mg_label.setTextColor(getResources().getColor(R.color.buttonTextUnselected));
            button_text.setText("MIGHT GO");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.pictures, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
}
