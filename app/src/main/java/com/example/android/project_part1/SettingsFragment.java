package com.example.android.project_part1;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import java.util.HashSet;
import java.util.Set;

public class SettingsFragment extends PreferenceFragment {
    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_general);

        Preference languagePreference = findPreference("language");
        Preference adultPreference = findPreference("adult_content");
        Preference originalTitlePreference = findPreference("original_title");


        languagePreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {

                MainActivity.current_language = newValue.toString();

                return true;
            }

        });


        adultPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
               MainActivity.current_adult = (boolean) newValue;
               return true;
            }
        });
        originalTitlePreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                MainActivity.current_original_title = (boolean) newValue;
                return true;
            }
        });





    }

}
