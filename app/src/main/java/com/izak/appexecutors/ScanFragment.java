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

import com.budiyev.android.codescanner.AutoFocusMode;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.budiyev.android.codescanner.ScanMode;
import com.google.zxing.Result;

public class ScanFragment extends Fragment implements DecodeCallback, View.OnClickListener, Protocol.ProtocolListener {
    private static final String TAG = "ScanFragment";
    private MainActivity activity;
    private CodeScanner codeScanner;
    ScanFragmentListener listener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MainActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_scan, container, false);
        CodeScannerView scannerView = root.findViewById(R.id.scanner_view);
        codeScanner = new CodeScanner(activity, scannerView);
        codeScanner.setCamera(CodeScanner.CAMERA_BACK);
        codeScanner.setFormats(CodeScanner.ALL_FORMATS);
        codeScanner.setAutoFocusMode(AutoFocusMode.SAFE);
        codeScanner.setScanMode(ScanMode.SINGLE);
        codeScanner.setDecodeCallback(this);
        scannerView.setOnClickListener(this);
        activity.checkPermission();
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        codeScanner.startPreview();
    }

    @Override
    public void onPause() {
        super.onPause();
        codeScanner.releaseResources();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (ScanFragmentListener) context;
    }

    @Override
    public void onDecoded(@NonNull Result result) {
        new Protocol(this).decode(result.getText());
    }

    @Override
    public void onProtocol(String protocol) {
        listener.onShowProtocol(protocol);
    }

    @Override
    public void onClick(View view) {
        codeScanner.startPreview();
    }

    interface ScanFragmentListener {
        void onShowProtocol(String protocol);
    }
}
