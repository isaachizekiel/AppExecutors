package com.izak.appexecutors;

import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.PublicKey;

public abstract class Crypto {
    static KeyStore ks;
    public static final int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
    public static final String KEY_ALIAS = "BT251Wallet";
    public static KeyPairGenerator kpg;
    public static PublicKey publicKey;
}
