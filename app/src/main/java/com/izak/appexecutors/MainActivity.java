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
        setContentView(R.layout.progress);

        AppExecutors.getInstance().cryptoIO().execute(new Crypto2.Init(this));



    }


    @Override
    public void onInitStatusUpdate(String status) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView textView = findViewById(R.id.progress_status);
                textView.setText(status);
            }
        });
    }

    @Override
    public void onInitFinished() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setContentView(R.layout.activity_main);
            }
        });

    }
}