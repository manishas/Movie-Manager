package ie.simo.movies.activities;

import ie.simo.movies.R;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
 
public class BoxOfficeTab extends TabActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.boxofficetabs);
 
        TabHost tabHost = getTabHost();
 
        // Tab for Photos
        TabSpec photospec = tabHost.newTabSpec("All Time");
        // setting Title and Icon for the Tab
        photospec.setIndicator("All Time", getResources().getDrawable(R.drawable.ic_menu_movie));
        Intent photosIntent = new Intent(this, BoxOffice.class);
        photospec.setContent(photosIntent);
 
        // Tab for Songs
        TabSpec songspec = tabHost.newTabSpec("Your Career");
        songspec.setIndicator("Your Career", getResources().getDrawable(R.drawable.ic_menu_phone));
        Intent songsIntent = new Intent(this, BoxOffice.class);
        songspec.setContent(songsIntent);
 
        // Adding all TabSpec to TabHost
        tabHost.addTab(photospec); // Adding photos tab
        tabHost.addTab(songspec); // Adding songs tab
    }
}