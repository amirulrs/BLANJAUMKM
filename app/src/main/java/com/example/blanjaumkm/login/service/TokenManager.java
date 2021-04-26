package com.example.blanjaumkm.login.service;

import android.content.SharedPreferences;

import com.example.blanjaumkm.login.model.Token;

public class TokenManager {

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    private static TokenManager INSTANCE = null;

    private TokenManager(SharedPreferences prefs) {
        this.prefs = prefs;
        this.editor = prefs.edit();
    }

    public static synchronized TokenManager getInstance(SharedPreferences prefs) {
        if (INSTANCE == null) {
            INSTANCE = new TokenManager(prefs);
        }
        return INSTANCE;
    }

    public void saveToken(Token token) {
        editor.putString("API_TOKEN", token.getToken()).commit();
    }

    public void deleteToken() {
        editor.remove("API_TOKEN").commit();
    }

    public Token getToken() {
        Token token = new Token();
        token.setToken(prefs.getString("API_TOKEN", null));
        return token;
    }
}
