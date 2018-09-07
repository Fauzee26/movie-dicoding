package fauzi.hilmy.submissionkeduakatalogfilmuiux.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class MovPreference {
    private String KEY_SEARCH = "query";
    private SharedPreferences pref;

    public MovPreference(Context context) {
        String PREFS_NAME = "movPref";
        pref = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void setName(String name) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(KEY_SEARCH, name);
        editor.apply();
    }

    public String getName() {
        return pref.getString(KEY_SEARCH, null);
    }
}
