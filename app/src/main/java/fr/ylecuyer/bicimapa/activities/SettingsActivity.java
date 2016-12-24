package fr.ylecuyer.bicimapa.activities;

import android.preference.PreferenceActivity;
import android.os.Bundle;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.PreferenceScreen;

import fr.ylecuyer.bicimapa.R;

@PreferenceScreen(R.xml.settings)
@EActivity
public class SettingsActivity extends PreferenceActivity {

    public static final String PREFS_NAME = "MyPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getPreferenceManager().setSharedPreferencesName(PREFS_NAME);
    }
}
