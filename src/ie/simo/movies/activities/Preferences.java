package ie.simo.movies.activities;

import ie.simo.movies.R;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.widget.Toast;

public class Preferences extends PreferenceActivity {
	
	public static final String RATING_STYLE = "ratingStyle"; 
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preference);
            // Get the custom preference
            Preference ratingPref = (Preference) findPreference(RATING_STYLE);
            ratingPref.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {

            	@Override
				public boolean onPreferenceChange(Preference preference, Object newValue) {
	                    Toast.makeText(getBaseContext(),
	                                    "Your changes have been saved.",
	                                    Toast.LENGTH_LONG).show();
	                    SharedPreferences customSharedPreference = getSharedPreferences(
	                                    "movieManagerSharedPrefs", Activity.MODE_PRIVATE);
	                    SharedPreferences.Editor editor = customSharedPreference.edit();
	                    editor.putString(RATING_STYLE,
	                                    newValue.toString());
	                    editor.commit();
	                    return true;
	            }
	    });
    }

}
