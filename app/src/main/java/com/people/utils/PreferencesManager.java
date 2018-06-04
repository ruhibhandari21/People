package com.people.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class PreferencesManager {

    public static final String SHARED_PREFERENCES_NAME = "MyPOasMatePref";

    /**
     * Instance
     */
    private static PreferencesManager preferencesManager = null;

    /**
     * Shared Preferences
     */
    private static SharedPreferences sharedPreferences;

    /**
     * Preferences variables
     */

    private String terminalId = "terminalId";
    private String terminalIp = "terminalIp";
    private String uniqueId = "uniqueId";
    private String username = "username";
    private String password = "password";
    private String isConnected = "isConnected";
    private String isAuthenticated="isAuthenticated";
    private String isAlipaySelected="isAlipaySelected";
    private String isWechatSelected="isWechatSelected";
    private String isUnipaySelected="isUnipaySelected";
    private String isVisaSlelected="isVisaSlelected";



    private PreferencesManager(Context context) {
        sharedPreferences = context.getSharedPreferences(
                SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public static PreferencesManager getInstance(Context context) {

        if (preferencesManager == null) {
            Log.v("Preference status", "new object of " + context);
            preferencesManager = new PreferencesManager(context);
        } else {
            Log.v("Preference status", "old object of " + context);
        }

        return preferencesManager;
    }

    public boolean isAlipaySelected() {
        return sharedPreferences.getBoolean(isAlipaySelected, false);
    }

    public void setisAlipaySelected(boolean text) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(isAlipaySelected, text);
        editor.apply();

    }

    public boolean isWechatSelected() {
        return sharedPreferences.getBoolean(isWechatSelected, false);
    }

    public void setisWechatSelected(boolean text) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(isWechatSelected, text);
        editor.apply();

    }

    public boolean isUnipaySelected() {
        return sharedPreferences.getBoolean(isUnipaySelected, false);
    }

    public void setisUnipaySelected(boolean text) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(isUnipaySelected, text);
        editor.apply();

    }



    public boolean isVisaSlelected() {
        return sharedPreferences.getBoolean(isVisaSlelected, false);
    }

    public void setisVisaSlelected(boolean text) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(isVisaSlelected, text);
        editor.apply();

    }



    public boolean isConnected() {
        return sharedPreferences.getBoolean(isConnected, false);
    }

    public void setIsConnected(boolean text) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(isConnected, text);
        editor.apply();

    }
    public boolean isAuthenticated() {
        return sharedPreferences.getBoolean(isAuthenticated, false);
    }

    public void setIsAuthenticated(boolean text) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(isAuthenticated, text);
        editor.apply();

    }

    public String getterminalId() {
        return sharedPreferences.getString(terminalId, "");
    }

    public void setterminalId(String text) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(terminalId, text);
        editor.apply();

    }

    public String getterminalIp() {
        return sharedPreferences.getString(terminalIp, "");
    }

    public void setterminalIp(String text) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(terminalIp, text);
        editor.apply();

    }

    public String getuniqueId() {
        return sharedPreferences.getString(uniqueId, "");
    }

    public void setuniqueId(String text) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(uniqueId, text);
        editor.apply();

    }

    public String getUsername() {
        return sharedPreferences.getString(username, "");
    }

    public void setUsername(String text) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(username, text);
        editor.apply();

    }

    public String getPassword() {
        return sharedPreferences.getString(password, "");
    }

    public void setPassword(String text) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(password, text);
        editor.apply();

    }
    public void clearPreferences() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("ACCESS_TOKEN", "");
        editor.putString("ACCESS_TOKEN_SECRET", "");
        editor.clear();
        editor.apply();
    }

    public void putString(String key, String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String getString(String key) {
        return sharedPreferences.getString(key, "");
    }

    public void putBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public boolean getBoolean(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    public void putInt(String key, int value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public int getInt(String key) {
        return sharedPreferences.getInt(key, 0);
    }

}
