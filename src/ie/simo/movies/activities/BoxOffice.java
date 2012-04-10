package ie.simo.movies.activities;

import ie.simo.movies.R;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
 
public class BoxOffice extends TabActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.boxofficetabs);
 
        TabHost tabHost = getTabHost();
 
        // Tab for Photos
        TabSpec allTime = tabHost.newTabSpec("All Time");
        // setting Title and Icon for the Tab
        allTime.setIndicator("All Time", getResources().getDrawable(R.drawable.ic_menu_movie));
        Intent photosIntent = new Intent(this, AllTimeBoxOffice.class);
        allTime.setContent(photosIntent);
 
        // Tab for Songs
        TabSpec yourMovies = tabHost.newTabSpec("Your Movies");
        yourMovies.setIndicator("Your Movies", getResources().getDrawable(R.drawable.ic_menu_phone));
        Intent songsIntent = new Intent(this, PersonalBest.class);
        yourMovies.setContent(songsIntent);
 
        // Adding all TabSpec to TabHost
        tabHost.addTab(allTime); // Adding photos tab
        tabHost.addTab(yourMovies); // Adding songs tab
    }
}