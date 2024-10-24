package com.radefffactory.chatapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    EditText et_name;
    Button b_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = getSharedPreferences("PREFS", 0);
        String name = preferences.getString("name", "");

        if (!name.isEmpty()) {
            startActivity(new Intent(getApplicationContext(), ChatActivity.class));
            finish();
        }

        et_name = findViewById(R.id.et_name);
        b_login = findViewById(R.id.b_login);

        b_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!et_name.getText().toString().isEmpty()) {
                    SharedPreferences preferences = getSharedPreferences("PREFS", 0);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("name", et_name.getText().toString());
                    editor.apply();

                    startActivity(new Intent(getApplicationContext(), ChatActivity.class));
                    finish();
                }
            }
        });
    }
}