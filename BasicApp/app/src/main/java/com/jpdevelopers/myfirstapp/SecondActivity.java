package com.jpdevelopers.myfirstapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class SecondActivity extends Activity {

    TextView viewName, viewLastname, viewAge, viewPhone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView (R.layout.activity_second);

        viewName = findViewById(R.id.viewName);
        viewLastname = findViewById(R.id.viewLastname);
        viewAge = findViewById(R.id.viewAge);
        viewPhone = findViewById(R.id.viewPhone);



    }

    /*Button btnInformation = findViewById (R.id.btnInformation);
        btnInformation.setOnClickListener (view -> {
        Intent intent = new Intent (getBaseContext (), SecondActivity.class);
        startActivity (intent);
    });*/

}