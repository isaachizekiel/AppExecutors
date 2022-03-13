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
        setContentView(R.layout.activity_main);



        AppExecutors.getInstance().cryptoIO().execute(new Crypto2.Init(this));

        // main thread
        AppExecutors.getInstance().mainThread().execute(new Runnable() {
            @Override
            public void run() {

            }
        });
    }


    @Override
    public void onInitStatusUpdate(String status) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView textView = findViewById(R.id.status_message);
                textView.setText(status);
            }
        });
    }

    @Override
    public void onInitFinished() {
        Log.e(TAG, "onInitFinished: ");
    }
}