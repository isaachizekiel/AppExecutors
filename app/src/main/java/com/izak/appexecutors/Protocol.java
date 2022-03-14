package com.izak.appexecutors;

import android.util.Base64;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class Protocol {
    private static final String TAG = "Protocol";
    ProtocolListener listener;

    private String pk, ciphertext, signature;

    Protocol(ProtocolListener listener) {this.listener = listener; }

    // decode json
    // ?(protocol only pk) -> { show send fund dialog..
    // ?(send)-> { encrypt_value, sign, ~push_to_db, ~show_qr }
    // ?(cancel)-> }
    //
    // ?(protocol only cip and sig) -> { verify, decrypt, push to db}
    void decode(String protocol) {
        String protocolJson = new String(Base64.decode(protocol, Base64.DEFAULT));
        Log.e(TAG, "decode: " + protocolJson );

        try {
            JSONObject jsonObject = new JSONObject(protocolJson);
            pk = jsonObject.getString("p");
            ciphertext = jsonObject.getString("c");
            signature = jsonObject.getString("s");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (pk.length() > 1 && ciphertext.length() == 1 && signature.length() == 1) {
            // show send fund
            Log.e(TAG, "decode: show send fund");
            input(pk);
        } else if (pk.length() == 1 && ciphertext.length() != 1 && signature.length() != 1) {
            // verify
            // decrypt
            // push to db
            Log.e(TAG, "decode: not supposed to be here for now" );
        } else {
            Log.e(TAG, "decode: protocol error");
        }

    }

    private void input(String data) {
        listener.onProtocolInput(data);
    }

    public void output(String data) {
        // encrypt value
        // sign cipher text
        // push to db
        listener.onProtocolOutput(data);
    }

    interface ProtocolListener {
        void onProtocolOutput(String protocol);
        void onProtocolInput(String protocol);
    }
}
