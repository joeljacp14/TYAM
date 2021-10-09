package com.jpdevelopers.myapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.jpdevelopers.myapp.R;

public class InfoActivity extends Activity {

    TextView viewName, viewLastname, viewAge, viewAddress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i("TEST", "OnCreate Second Activity");
        super.onCreate(savedInstanceState);
        setContentView (R.layout.activity_info);

        Intent intent = getIntent();
        if (intent == null)
            return;
        String name = intent.getStringExtra(MainActivity.NAME);
        String lastName = intent.getStringExtra(MainActivity.LASTNAME);
        String age = intent.getStringExtra(MainActivity.AGE);
        String address = intent.getStringExtra(MainActivity.ADDRESS);
        Log.i("TEST", name+" - "+lastName+" - "+age+" - "+address);

        viewName = findViewById(R.id.welcome_1);
        viewLastname = findViewById(R.id.welcome_2);
        viewAge = findViewById(R.id.welcome_3);
        viewAddress = findViewById(R.id.welcome_4);

        if (name != null && lastName != "")
            viewName.setText(name+" "+lastName);
        if (age != "")
            viewAge.setText(age);
        if (address != "")
            viewAddress.setText(address);

    }

    /*Button btnInformation = findViewById (R.id.btnInformation);
        btnInformation.setOnClickListener (view -> {
        Intent intent = new Intent (getBaseContext (), InfoActivity.class);
        startActivity (intent);
    });*/

}