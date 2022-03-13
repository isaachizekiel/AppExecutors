package com.izak.appexecutors;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements InitCrypto.Listener {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppExecutors.getInstance().cryptoIO().execute(new InitCrypto(this));

        // main thread
        AppExecutors.getInstance().mainThread().execute(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    @Override
    public void onStatus(String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView textView = findViewById(R.id.hello);
                textView.setText(message);
            }
        });
    }

}