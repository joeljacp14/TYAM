package com.jpdevelopers.myfirstapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class SecondActivity extends Activity {

    TextView viewName, viewLastname, viewAge, viewAddress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView (R.layout.activity_second);

        Intent intent = getIntent();
        if (intent == null)
            return;
        String name = intent.getStringExtra(MainActivity.NAME);
        String lastName = intent.getStringExtra(MainActivity.LASTNAME);
        String age = intent.getStringExtra(MainActivity.AGE);
        String address = intent.getStringExtra(MainActivity.ADDRESS);

        viewName = findViewById(R.id.welcome_1);
        viewLastname = findViewById(R.id.welcome_2);
        viewAge = findViewById(R.id.welcome_3);
        viewAddress = findViewById(R.id.welcome_4);

        if (name != null && lastName != null)
            viewName.setText(name+" "+lastName);
        if (age != null)
            viewAge.setText(age);
        if (address != null)
            viewAddress.setText(address);

    }

    /*Button btnInformation = findViewById (R.id.btnInformation);
        btnInformation.setOnClickListener (view -> {
        Intent intent = new Intent (getBaseContext (), SecondActivity.class);
        startActivity (intent);
    });*/

}