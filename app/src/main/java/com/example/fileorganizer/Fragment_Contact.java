package com.example.fileorganizer;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Fragment_Contact extends Fragment {

    public Button btnCall;
    public Button btnEmail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fragment_contact, container, false);
        View v = inflater.inflate(R.layout.fragment_contact, container, false);


       final Button btnCall = v.findViewById(R.id.button1);

      btnCall.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
               intent.setData(Uri.parse("tel:"+ btnCall.getText().toString().trim() ));
               startActivity(intent);
           }

       });


        final Button btnEmail =  v.findViewById(R.id.button2);

        btnEmail.setOnClickListener(new OnClickListener() {
            @Override
                public void onClick(View v) {
                sendemail();
            }
        });


        return v;
    }

    private void sendemail() {
        Intent intent=new Intent(Intent.ACTION_SEND);
        String[] recipients={"mailto@gmail.com"};
        intent.putExtra(Intent.EXTRA_EMAIL, recipients);
        intent.putExtra(Intent.EXTRA_SUBJECT,"Subject text here...");
        intent.putExtra(Intent.EXTRA_TEXT,"Body of the content here...");
        intent.putExtra(Intent.EXTRA_CC,"mailcc@gmail.com");
        intent.setType("text/html");
        intent.setPackage("com.google.android.gm");
        startActivity(Intent.createChooser(intent, "Send mail"));
    }


}


