package ie.simo.movies.activities;

import ie.simo.movies.R;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.widget.Toast;

public class Preferences extends PreferenceActivity {
	
	public static final String RATING_STYLE = "ratingStyle"; 
	public static final String MENTAL_DESCRIPTIONS = "mental";
	public static final String SHOW_PLOT = "showPlot";
	
	private Preference ratingPref;
	private Preference mentalContent;
	private Preference showPlot;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preference);
            // Get the custom preference
            findAllPrefs();
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
        mentalContent.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {

            	@Override
				public boolean onPreferenceChange(Preference preference, Object newValue) {
	                    Toast.makeText(getBaseContext(),
	                                    "Your changes have been saved.",
	                                    Toast.LENGTH_LONG).show();
	                    SharedPreferences customSharedPreference = getSharedPreferences(
	                                    "movieManagerSharedPrefs", Activity.MODE_PRIVATE);
	                    SharedPreferences.Editor editor = customSharedPreference.edit();
	                    editor.putBoolean(MENTAL_DESCRIPTIONS, (Boolean) newValue);
	                    editor.commit();
	                    return true;
	            }
	    });
        
        showPlot.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {

        	@Override
			public boolean onPreferenceChange(Preference preference, Object newValue) {
                    Toast.makeText(getBaseContext(),
                                    "Your changes have been saved.",
                                    Toast.LENGTH_LONG).show();
                    SharedPreferences customSharedPreference = getSharedPreferences(
                                    "movieManagerSharedPrefs", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = customSharedPreference.edit();
                    editor.putBoolean(SHOW_PLOT, (Boolean) newValue);
                    editor.commit();
                    return true;
            }
    });
    }

	private void findAllPrefs() {
		ratingPref = (Preference) findPreference(RATING_STYLE);
		mentalContent = (Preference) findPreference(MENTAL_DESCRIPTIONS);
		showPlot = (Preference) findPreference(SHOW_PLOT);
	}

}
