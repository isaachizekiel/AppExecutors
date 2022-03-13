package com.izak.appexecutors;

import android.os.SystemClock;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Random;

class InitCrypto implements Runnable {
    private static final String TAG = "InitCrypto";
    Listener listener;

    InitCrypto(Listener listener) {
        this.listener = listener;
    }

    @Override
    public void run() {
        for (int i = 0; i < 50; i++) {
            int leftLimit = 97; // letter 'a'
            int rightLimit = 122; // letter 'z'
            int targetStringLength = 10;
            Random random = new Random();
            String generatedString = random.ints(leftLimit, rightLimit + 1)
                    .limit(targetStringLength)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();
            listener.onStatus(generatedString);
            SystemClock.sleep(100);
        }
    }

    interface Listener {
        void onStatus(String message);
    }
}
