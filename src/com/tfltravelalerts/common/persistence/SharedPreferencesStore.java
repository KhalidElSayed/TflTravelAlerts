
package com.tfltravelalerts.common.persistence;

import java.lang.reflect.Type;

import org.holoeverywhere.preference.PreferenceManager;

import android.content.SharedPreferences;
import android.util.SparseArray;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tfltravelalerts.TflApplication;

public class SharedPreferencesStore<T> implements Store<T> {

    private final Type mDataType;
    private final String mSharedPreferenceKey;
    private Gson mGson;

    public SharedPreferencesStore(Type dataType, String sharedPreferenceKey) {
        mDataType = dataType;
        mSharedPreferenceKey = sharedPreferenceKey;
    }

    @Override
    public void save(T object) {
        SharedPreferences preferences = getSharedPreferences();
        String json = getGson().toJson(object);
        preferences.edit().putString(mSharedPreferenceKey, json).commit();
    }

    @Override
    @SuppressWarnings("unchecked")
    public T load() {
        SharedPreferences preferences = getSharedPreferences();
        String json = preferences.getString(mSharedPreferenceKey, null);
        if (json != null) {
            return (T) getGson().fromJson(json, mDataType);
        }

        return null;
    }

    private Gson getGson() {
        if (mGson == null) {
            mGson = new GsonBuilder()
                    .registerTypeAdapter(ImmutableList.class, new ImmutableListDeserializer())
                    .registerTypeAdapter(ImmutableSet.class, new ImmutableSetDeserializer())
                    .registerTypeAdapter(SparseArray.class, new SparseArrayDeserializer())
                    .registerTypeAdapter(SparseArray.class, new SparseArraySerializer())
                    .create();
        }

        return mGson;
    }

    private SharedPreferences getSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(TflApplication.getLastInstance());
    }

}
