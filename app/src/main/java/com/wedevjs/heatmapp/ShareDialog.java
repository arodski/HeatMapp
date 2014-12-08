package com.wedevjs.heatmapp;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by marco on 11/14/14.
 */
public class ShareDialog extends DialogFragment {


    public ShareDialog() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_share, container);
        getDialog().setTitle("YAY! Share with friends!");

        return view;
    }

    public void close(View view){
        this.dismiss();
    }
}