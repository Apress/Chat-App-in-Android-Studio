package com.radefffactory.chatapp;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ChatActivity extends AppCompatActivity {

    EditText et_message;
    Button b_send;
    TextView tv_history;

    String status, statusRefresh, name, message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        SharedPreferences preferences = getSharedPreferences("PREFS", 0);
        name = preferences.getString("name", "");

        et_message = findViewById(R.id.et_message);
        tv_history = findViewById(R.id.tv_history);
        b_send = findViewById(R.id.b_send);

        b_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message = et_message.getText().toString().trim();
                et_message.setText("");
                if (!message.isEmpty()) {
                    message = name + ": " + message;
                    new SendData().execute();
                }
            }
        });

        new RefreshData().execute();
    }

    private class SendData extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            status = NetworkHelper.sendData("chat", message);
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);

            switch (status) {
                case "SUCCESS":
                    //message sent
                    break;
                case "ERROR":
                    Toast.makeText(ChatActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    private class RefreshData extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            statusRefresh = NetworkHelper.downloadData("chat");
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);

            if (!statusRefresh.isEmpty()) {
                tv_history.setText(statusRefresh);
            }

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    new RefreshData().execute();
                }
            }, 1000);
        }
    }
}