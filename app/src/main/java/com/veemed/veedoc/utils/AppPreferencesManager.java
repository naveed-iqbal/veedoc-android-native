package com.veemed.veedoc.utils;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by Naveed Iqbal on 6/2/2018.
 */

public class AppPreferencesManager {
    public static enum STRING_ENUM_KEY {
        SELECTED_LOCATION,
        CARD_NUMBER,
        PHONE_NUMBER,
        ACCESS_TOKEN,
        REFRESH_TOKEN,
        USERNAME,
        PASSWORD;
    }
    public static enum INT_ENUM_KEY {
        TOKEN_EXPIRY
    }

    public static enum BOOLEAN_ENUM_KEY {
        ACCOUNT_REG_INITIATED,
        IS_FIRST_RUN,
        ACCOUNT_VERIFIED,
    }

    private static AppPreferencesManager instance;
    private Context context;

    private AppPreferencesManager(Context context) {
        this.context = context;
    }

    public static synchronized AppPreferencesManager getInstance(Context context) {
        if(instance == null) {
            instance = new  AppPreferencesManager(context);
        }

        return instance;
    }

    public void addOrUpdateIntegerPreference(INT_ENUM_KEY key, int value) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putInt(key.toString(), value).apply();
    }

    public int findIntegerPrferenceValue(STRING_ENUM_KEY key, int defaultValue) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(key.toString(), defaultValue);
    }

    public void addOrUpdateStringPreference(STRING_ENUM_KEY key, String value) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(key.toString(), value).apply();
    }

    public String findStringPrferenceValue(STRING_ENUM_KEY key, String defaultValue) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(key.toString(), defaultValue);
    }

    public void addOrUpdateBooleanPreference(BOOLEAN_ENUM_KEY key, boolean value) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(key.toString(), value).apply();
    }

    public boolean findBooleanPrferenceValue(BOOLEAN_ENUM_KEY key, boolean defaultValue) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(key.toString(), defaultValue);
    }
}
