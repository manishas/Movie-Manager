package ie.simo.movies.activities;

import ie.simo.movies.R;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public abstract class ActivityWithMenu extends Activity {

	 // Initiating Menu XML file (menu.xml)
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.mainmenu, menu);
        return true;
    }
 
    /**
     * Event Handling for Individual menu item selected
     * Identify single menu item by it's id
     * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
    	Intent intent = new Intent();
    	if(item.getTitle().equals("Quit")){
    		intent = new Intent(Intent.ACTION_MAIN);
    		intent.addCategory(Intent.CATEGORY_HOME);
    	}
    	else if(item.getTitle().equals("Main Menu")){
    		intent.setClass(this, StartActivity.class);
    	}
    	else{
    		intent.setClass(this, BoxOffice.class);
    	}
    	
        return true;
    }    
	
}
