package com.izak.appexecutors;

import android.util.Log;

public class Protocol implements ScanFragment.ScanFragmentListener {

    private static final String TAG = "Protocol";



    @Override
    public void onProtocol(String b64Json) {
        // read json
        // check fields
        // if only public key found -> (show send dialog with destination public key, ?(send) -> show qr, push to db.. ?(cancel) -> )
        // if only ciphertext and signature found (verify signature, decrypt ciphertext, push to db)

        Log.e(TAG, "onDecoded: ");
    }
}
