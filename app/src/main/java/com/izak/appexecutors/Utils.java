package com.izak.appexecutors;

import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;

public final class Utils {
    public static String encodePK(byte [] pk) {
        String pkStr = Base64.encodeToString(pk, Base64.DEFAULT);
        JSONObject json = new JSONObject();
        try {
            json.put("p", pkStr);
            json.put("c", "c");
            json.put("s", "s");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return Base64.encodeToString(json.toString().getBytes(StandardCharsets.UTF_8), Base64.DEFAULT);
    }
}
