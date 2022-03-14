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
            // input(pk);
            Log.e(TAG, "decode: ");
        } else if (pk.length() == 1 && ciphertext.length() != 1 && signature.length() != 1) {
            // verify
            // decrypt
            // push to db
            Log.e(TAG, "decode: not supposed to be here for now" );
        } else {
            Log.e(TAG, "decode: protocol error");
        }

    }

    public void display(String data) {
        listener.onProtocolOutput(data);
    }


    public void in(String b64json) {
        // verify
        // decrypt
        // push
    }

    public void out(String b64json) {
        // encrypt
        // sign
        // push
        // show
    }


    interface ProtocolListener {
        void onProtocolOutput(String protocol);
        void onProtocolInput(String protocol);
    }
}
