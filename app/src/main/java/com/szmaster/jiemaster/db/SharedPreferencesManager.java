package com.szmaster.jiemaster.db;

import android.app.Activity;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.HashSet;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.szmaster.jiemaster.App;

/**
 * Created by jiangsiyu on 2016/6/15.
 */
public class SharedPreferencesManager {

    private static SharedPreferencesManager instance;
    public static final String KEY_ROOT = "com.jiemaster";
    private static final String KEYS = KEY_ROOT + ".keys";
    public HashSet<String> keys;
    private static Gson gson;

    public static SharedPreferencesManager getInstance() {
        if (instance == null) {
            instance = new SharedPreferencesManager();
            gson = new Gson();
        }
        return instance;
    }

    public SharedPreferences getSharedPreferences(String key) {
        if (TextUtils.isEmpty(key)) {
            return null;
        } else {
            addKey(key);
            return App.getContext().getSharedPreferences(key, Activity.MODE_PRIVATE);
        }
    }

    public <T> void putData(String key, T t) {
        putData(KEY_ROOT, key, t);
    }


    public <T> void putData(String groupName, String key, T t) {
        SharedPreferences.Editor editor = getSharedPreferences(groupName).edit();
        if (t instanceof String) {
            editor.putString(key, (String) t);
        } else if (t instanceof Boolean) {
            editor.putBoolean(key, (Boolean) t);
        } else if (t instanceof Float) {
            editor.putFloat(key, (Float) t);
        } else if (t instanceof Integer) {
            editor.putInt(key, (Integer) t);
        } else if (t instanceof Long) {
            editor.putLong(key, (Long) t);
        } else if (t instanceof Set) {
            editor.putStringSet(key, (Set<String>) t);
        } else {
            throw new RuntimeException("这个数据不科学");
        }
        editor.commit();
    }

    private HashSet<String> getKeys() {
        keys = gson.fromJson(
                getSharedPreferences(KEY_ROOT).getString(KEYS, ""),
                new TypeToken<HashSet<String>>() {
                }.getType());
        if (keys == null) {
            keys = new HashSet<>();
        }
        return keys;
    }


    private void addKey(String key) {
        if (TextUtils.isEmpty(key) || key.equals(KEY_ROOT) || getKeys().contains(key)) {
            return;
        } else {
            getKeys().add(key);
            String json = gson.toJson(getKeys(), new TypeToken<HashSet<String>>() {
            }.getType());
            SharedPreferences.Editor editor = getSharedPreferences(KEY_ROOT).edit();
            editor.putString(KEYS, json);
            editor.commit();
        }
    }

    public void cleanKeys() {
        for (String key : getKeys()) {
            SharedPreferences.Editor editor = getSharedPreferences(key).edit();
            editor.clear();
            editor.commit();
        }
    }

    public void cleanAllKeys() {
        cleanKeys();
        SharedPreferences.Editor editor = getSharedPreferences(KEY_ROOT).edit();
        editor.clear();
        editor.commit();

    }


    public String getString(String key) {
        return getString(KEY_ROOT, key);
    }

    public String getString(String groupName, String key) {
        return getSharedPreferences(groupName).getString(key, "");
    }

    public float getFloat(String key) {
        return getFloat(KEY_ROOT, key);
    }

    public float getFloat(String groupName, String key) {
        return getSharedPreferences(groupName).getFloat(key, 0f);
    }

    public int getInt(String key) {
        return getInt(KEY_ROOT, key);
    }

    public int getInt(String groupName, String key) {
        return getSharedPreferences(groupName).getInt(key, 0);
    }

    public long getLong(String key) {
        return getLong(KEY_ROOT, key);
    }

    public long getLong(String groupName, String key) {
        return getSharedPreferences(groupName).getLong(key, 0l);
    }

    public boolean getBoolean(String key) {
        return getBoolean(KEY_ROOT, key);
    }

    public boolean getBoolean(String groupName, String key) {
        return getSharedPreferences(groupName).getBoolean(key, false);
    }

    public Set getSet(String key) {
        return getSet(KEY_ROOT, key);
    }

    public Set getSet(String groupName, String key) {
        return getSharedPreferences(groupName).getStringSet(key, null);
    }

}
