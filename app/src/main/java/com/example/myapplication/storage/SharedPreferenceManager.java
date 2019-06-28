package com.example.myapplication.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.myapplication.models.User;

public class SharedPreferenceManager {

    private static final String SHARED_PREFERNCES_NAME = "my_shared_preferences";

    private static SharedPreferenceManager msharedPreferenceManager;
    private Context mcontext;

    private SharedPreferenceManager(Context context) {
        this.mcontext = context;
    }

    public static synchronized SharedPreferenceManager getInstance(Context context) {
        if (msharedPreferenceManager == null) {
            msharedPreferenceManager = new SharedPreferenceManager(context);
        }

        return msharedPreferenceManager;
    }

    public void saveUser(User user) {
        SharedPreferences sharedPreferences = mcontext.getSharedPreferences(SHARED_PREFERNCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("id", user.getId());
        editor.putString("email", user.getEmail());
        editor.putString("phone", user.getPhone());
        editor.putString("city", user.getCity());
        editor.putString("name", user.getName());

        editor.apply();

    }

    public boolean isLoggedIn() {

        SharedPreferences sharedPreferences = mcontext.getSharedPreferences(SHARED_PREFERNCES_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt("id", -1) != -1;
    }

    public User getUser() {
        SharedPreferences sharedPreferences = mcontext.getSharedPreferences(SHARED_PREFERNCES_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getInt("id", -1),
                sharedPreferences.getString("name", null),
                sharedPreferences.getString("email", null),
                sharedPreferences.getString("phone", null),
                sharedPreferences.getString("city", null)
        );
    }

    public void clear() {
        SharedPreferences sharedPreferences = mcontext.getSharedPreferences(SHARED_PREFERNCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
