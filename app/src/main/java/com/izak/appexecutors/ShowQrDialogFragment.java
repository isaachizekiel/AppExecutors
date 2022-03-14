package com.izak.appexecutors;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.budiyev.android.codescanner.BarcodeUtils;
import com.google.zxing.BarcodeFormat;

public class ShowQrDialogFragment extends DialogFragment {
    MainActivity mainActivity;
    View view;
    String qrText;
    Bitmap bitmap;

    public ShowQrDialogFragment(String qr) {
        qrText = qr;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
        bitmap = BarcodeUtils.encodeBitmap(qrText, BarcodeFormat.QR_CODE, 600, 600);
    }

    @SuppressLint("InflateParams")
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = mainActivity.getLayoutInflater();
        view = inflater.inflate(R.layout.show_qr_dialog, null);
        ImageView imageView = view.findViewById(R.id.qr);
        imageView.setImageBitmap(bitmap);
        AlertDialog.Builder b = new AlertDialog.Builder(mainActivity);
        b.setView(view);
        b.setCancelable(false);
        b.setPositiveButton("OK", null);
        return b.create();
    }


}
