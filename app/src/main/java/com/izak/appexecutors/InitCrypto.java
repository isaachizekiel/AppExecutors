package com.izak.appexecutors;

import android.os.SystemClock;

import java.util.Random;

class InitCrypto extends Crypto implements Runnable {
    private static final String TAG = "InitCrypto";
    Listener listener;

    InitCrypto(Listener listener) {
        this.listener = listener;
    }

    @Override
    public void run() {

        for (;;) {
            int leftLimit = 32; // character ' '
            int rightLimit = 127; //
            int targetStringLength = 16;
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
