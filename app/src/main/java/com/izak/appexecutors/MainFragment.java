package com.izak.appexecutors;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainFragment extends Fragment {
    private static final String TAG = "MainFragment";
    MainActivity activity;
    Listener listener;
    private final String b64Json = Utils.encodePK(Crypto2.publicKey.getEncoded());


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MainActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_main, container, false);
        FloatingActionButton fab = view.findViewById(R.id.wallet_address);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showB64Json(Utils.encodePK(Crypto2.publicKey.getEncoded()));
            }
        });
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (Listener) context;
    }

    void showB64Json(String b64Json) {
        listener.onShowB64Json(b64Json);
    }

    interface Listener {
        void onShowB64Json(String b64Json);
    }
}
