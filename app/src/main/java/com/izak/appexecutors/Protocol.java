package com.izak.appexecutors;

public class Protocol {
    private static final String TAG = "Protocol";
    ProtocolListener listener;

    Protocol(ProtocolListener listener) {this.listener = listener; }

    void decode(String protocol) {
        // decode json
        // ?(protocol only pk) -> { show send fund dialog..
        // ?(send)-> { encrypt_value, sign, ~push_to_db, ~show_qr }
        // ?(cancel)-> }
        //
        // ?(protocol only cip and sig) -> { verify, decrypt, push to db}


        listener.onProtocol(protocol);
    }




    interface ProtocolListener {
        void onProtocol(String protocol);
    }
}
