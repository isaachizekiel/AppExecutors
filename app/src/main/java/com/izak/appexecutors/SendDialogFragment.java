package com.izak.appexecutors;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class SendDialogFragment extends DialogFragment {
    MainActivity activity;
    View view;
    String qrText;
    public SendDialogFragment(String qr) {
        qrText = qr;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MainActivity) getActivity();
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = activity.getLayoutInflater();
        AlertDialog.Builder b = new AlertDialog.Builder(activity);
        view = inflater.inflate(R.layout.dialog_send, null);
        b.setView(view);
        b.setTitle(R.string.app_name);
        b.setCancelable(true);
        b.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                EditText value = view.findViewById(R.id.input);
                new Protocol((MainActivity)getActivity()).in(value.getText().toString());
            }
        });
        b.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        return b.create();
    }

}
