package com.izak.appexecutors;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements Crypto2.Init.InitListener {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mySetContentView(0);
        AppExecutors.getInstance().cryptoIO().execute(new Crypto2.Init(this));
    }

    @Override
    public void onInitStatus(String status) {
        runOnUiThread(() -> {
            TextView textView = findViewById(R.id.progress_status);
            textView.setText(status);
        });
    }

    @Override
    public void onInitDone() {
        runOnUiThread(() -> mySetContentView(1));
    }

    void mySetContentView(int  res) {
        switch (res) {
            case 1:
                setContentView(R.layout.activity_main);
                break;
            case 0:
            default:
                setContentView(R.layout.progress);
                break;

        }
    }
}