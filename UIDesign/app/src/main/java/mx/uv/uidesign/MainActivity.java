package mx.uv.uidesign;

/*
* @authors:
* Oskar Ramses Beltran Magaña
* Joel Jacome Pioquinto
* */
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MainActivity extends Activity {
    EditText etName;
    EditText etLastN;
    EditText etAge;
    EditText etAdress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView (R.layout.activity_main);

        etName = findViewById(R.id.etName);
        etLastN = findViewById(R.id.etLastN);
        etAge = findViewById(R.id.etAge);
        etAdress = findViewById(R.id.etAdress);

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("name", etName.getText().toString());
        outState.putString("last_name", etLastN.getText().toString());
        outState.putString("age", etAge.getText().toString());
        outState.putString("address", etAdress.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        String name = savedInstanceState.getString("name");
        String last_name = savedInstanceState.getString("last_name");
        String age = savedInstanceState.getString("age");
        String adress = savedInstanceState.getString("adress");
        etName.setText(name);
        etLastN.setText(last_name);
        etAge.setText(age);
        etAdress.setText(adress);
    }
}
