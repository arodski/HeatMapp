package com.wedevjs.heatmapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by marco on 11/14/14.
 */
public class ShareDialog extends DialogFragment {

    private String placeName = "";

    public ShareDialog() {
        // Empty constructor required for DialogFragment
    }

    @SuppressLint("ValidFragment")
    public ShareDialog(String placeName) {
        this.placeName = placeName;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_share, container);
        getDialog().setTitle("YAY! Share with friends!");

        Button dismiss = (Button) view.findViewById(R.id.cancel_share_button);
        dismiss.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        Button facebook = (Button) view.findViewById(R.id.fb_share_button);
        facebook.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent("android.intent.category.LAUNCHER");
                    intent.setClassName("com.facebook.katana", "com.facebook.katana.LoginActivity");
                    startActivity(intent);
                } catch ( Exception e) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://facebook.com"));
                    startActivity(intent);
                }

            }
        });

        Button twitter = (Button) view.findViewById(R.id.twt_share_button);
        twitter.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com"));
                startActivity(intent);
            }
        });

        return view;
    }


}