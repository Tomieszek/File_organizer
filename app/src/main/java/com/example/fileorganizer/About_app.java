package com.example.fileorganizer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.ActionBar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

public class About_app extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app2);

        Toolbar toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar myActionBar = getSupportActionBar();
        if ( myActionBar != null ) {

            myActionBar.setDisplayHomeAsUpEnabled(true);

        }
    }

//    public boolean onCreateOptionsMenu(Menu menu){
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu,menu);
//        return true;
//
//
//    }
}