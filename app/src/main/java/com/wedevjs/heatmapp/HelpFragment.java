package com.wedevjs.heatmapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class HelpFragment extends Fragment {

    public HelpFragment(){}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_help, container, false);

        Button button = (Button) rootView.findViewById(R.id.contact_send_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView name = (TextView) getView().findViewById(R.id.contact_name_text);
                TextView email = (TextView) getView().findViewById(R.id.contact_email_text);
                TextView message = (TextView) getView().findViewById(R.id.contact_message_text);

                name.setText("");
                email.setText("");
                message.setText("");

                Toast.makeText(getActivity().getApplicationContext(), "Message sent", Toast.LENGTH_LONG).show();
            }
        });

        return rootView;
    }

    public void sendMessage(View view){
        TextView name = (TextView) getView().findViewById(R.id.contact_name_text);
        TextView email = (TextView) getView().findViewById(R.id.contact_email_text);
        TextView message = (TextView) getView().findViewById(R.id.contact_message_text);

        name.setText("");
        email.setText("");
        message.setText("");

        Toast.makeText(getActivity().getApplicationContext(), "Message sent", Toast.LENGTH_LONG).show();
    }

}
