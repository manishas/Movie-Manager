package ie.simo.movies.activities;

import ie.simo.movies.R;
import ie.simo.movies.dao.ProductionCompanyDbAdapter;
import ie.simo.movies.domain.ProductionCompany;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public abstract class ActivityWithMenu extends Activity {
	
	private ProductionCompany pc;

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
    		save();
    		intent.setClass(this, StartActivity.class);
    	}
    	else{
    		save();
    		intent.setClass(this, BoxOffice.class);
    	}
    	
    	startActivity(intent);
    	
        return true;
    } 
    
    private void save(){
    	ProductionCompanyDbAdapter companyDb = new ProductionCompanyDbAdapter(this);
		companyDb.openWritable();
		companyDb.updateCompanyDetails(pc);
		companyDb.close();
    }

	/**
	 * @return the current production company
	 */
	public ProductionCompany getPc() {
		return pc;
	}

	/**
	 * @param pc the production company to set
	 */
	public void setPc(ProductionCompany pc) {
		this.pc = pc;
	}
	
}
