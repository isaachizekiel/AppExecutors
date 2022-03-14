package com.izak.appexecutors;

import android.util.Log;

public class Protocol {
    private static final String TAG = "Protocol";
    ProtocolListener listener;

    Protocol(ProtocolListener listener) {this.listener = listener; }

    void decode(String protocol) {
        listener.onProtocol();
    }

    interface ProtocolListener {
        void onProtocol();
    }
}
