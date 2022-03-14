package com.izak.appexecutors;

import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;

import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.security.auth.x500.X500Principal;

public class Crypto2 {
    static KeyStore ks;
    public static final String KEY_ALIAS = "BT251Wallet";
    public static KeyPairGenerator kpg;
    public static PublicKey publicKey;
    public static String statusMsg;

    static class Init implements Runnable {
        InitListener listener;
        Init(InitListener listener) {
            this.listener = listener;
        }

        @Override
        public void run() {
            try {
                ks = KeyStore.getInstance(SecurityConstants.KEYSTORE_PROVIDER_ANDROID_KEYSTORE);
            } catch (KeyStoreException e) {
                e.printStackTrace();
            }

            try {
                ks.load(null);
            } catch (CertificateException | IOException | NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            //if (ks.containsAlias(KEY_ALIAS)) return;
            statusMsg = "Preparing wallet";
            listener.onInitStatus(statusMsg);
            Calendar start = new GregorianCalendar();
            Calendar end = new GregorianCalendar();
            end.add(Calendar.YEAR, 1);
            try {
                kpg = KeyPairGenerator.getInstance(SecurityConstants.TYPE_RSA,
                        SecurityConstants.KEYSTORE_PROVIDER_ANDROID_KEYSTORE);
            } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
                e.printStackTrace();
            }
            AlgorithmParameterSpec spec = new KeyGenParameterSpec
                    .Builder(KEY_ALIAS,
                    KeyProperties.PURPOSE_SIGN
                            | KeyProperties.PURPOSE_VERIFY
                            | KeyProperties.PURPOSE_ENCRYPT
                            | KeyProperties.PURPOSE_DECRYPT )
                    .setCertificateSubject(new X500Principal("CN=" + KEY_ALIAS))
                    .setDigests(KeyProperties.DIGEST_SHA256)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1)
                    .setSignaturePaddings(KeyProperties.SIGNATURE_PADDING_RSA_PKCS1)
                    .setCertificateSerialNumber(BigInteger.valueOf(1337))
                    .setCertificateNotBefore(start.getTime())
                    .setCertificateNotAfter(end.getTime())
                    .build();
            try {
                kpg.initialize(spec);
            } catch (InvalidAlgorithmParameterException e) {
                e.printStackTrace();
            }
            publicKey = kpg.generateKeyPair().getPublic();
            statusMsg = "Done";
            listener.onInitStatus(statusMsg);
            listener.onInitDone();
        }

        interface InitListener {
            void onInitStatus(String status);
            void onInitDone();
        }
    }
}
