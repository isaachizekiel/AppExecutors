package com.izak.appexecutors;

public class Protocol {
    private static final String TAG = "Protocol";
    ProtocolListener listener;

    Protocol(ProtocolListener listener) {this.listener = listener; }

    void decode(String protocol) {

        // decode json


        listener.onProtocol(protocol);
    }




    interface ProtocolListener {
        void onProtocol(String protocol);
    }
}
