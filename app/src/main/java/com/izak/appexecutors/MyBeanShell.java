package com.izak.appexecutors;

import android.content.Context;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Date;

import bsh.EvalError;
import bsh.Interpreter;

public class MyBeanShell {
    private static final String TAG = "MyBeanShell";
    Interpreter interpreter;
    Context mContext;

    Signature[] signatures;
    X509Certificate cert;
    CertificateFactory cf;

    InputStream inputStream;

    String scr = "sig=context.getPackageManager().getPackageInfo(context.getPackageName(),android.content.pm.PackageManager.GET_SIGNATURES).signatures";

    MyBeanShell(Context context) {
        interpreter = new bsh.Interpreter();
        mContext = context;
    }

    void execute() {
        try {
            interpreter.set("context", mContext);
            interpreter.eval(scr);
            signatures = (Signature[]) interpreter.get("sig");

            cf = CertificateFactory.getInstance("X509");
            inputStream = new ByteArrayInputStream(signatures[0].toByteArray());
            cert = (X509Certificate) cf.generateCertificate(inputStream);

        } catch (EvalError | CertificateException evalError) {
            evalError.printStackTrace();
        }


        Log.e(TAG, "execute: scr length " + scr.length());
        Log.e(TAG, "execute: scr Base64 length " + Base64.encodeToString(scr.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT).length());

        Log.e(TAG, "execute: " + cert.getSigAlgName());
        Log.e(TAG, "execute: " + cert.getSigAlgOID());
        Log.e(TAG, "execute: " + cert.getIssuerDN());
        Log.e(TAG, "execute: " + cert.getIssuerX500Principal());
        Log.e(TAG, "execute: " + cert.getSerialNumber());
        Log.e(TAG, "execute: " + Base64.encodeToString(cert.getSignature(), Base64.DEFAULT));
    }
}