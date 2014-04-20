package ie.simo.movies.activities;

import static ie.simo.movies.util.Consts.COMPANY;

import java.util.UUID;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import ie.simo.movies.R;
import ie.simo.movies.dao.ProductionCompanyDbAdapter;
import ie.simo.movies.dao.cursor.ProductionCompanyCursorAdapter;
import ie.simo.movies.util.DBConsts.ProductionCompany;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

@EActivity(R.layout.continuegame)
public class ContinueGameActivity extends ListActivity {
	
	private ProductionCompanyDbAdapter db;

	@AfterViews
	public void onCreate() {
		
		db = new ProductionCompanyDbAdapter(this);
		db.openReadable();
		Cursor c = db.getUserGames();
		
		ListAdapter adapter = new ProductionCompanyCursorAdapter(this,c);
        setListAdapter(adapter);
		        
		ListView list = getListView();
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View arg1, int position,	long id) {
				ProductionCompanyCursorAdapter adapter = (ProductionCompanyCursorAdapter) parent.getAdapter();
				Cursor c = adapter.getCursor();
				c.moveToPosition(position);
				
				final ie.simo.movies.domain.ProductionCompany pc = new ie.simo.movies.domain.ProductionCompany(c.getString(c.getColumnIndex(ProductionCompany.name)), 
															c.getInt(c.getColumnIndex(ProductionCompany.budget)),
															c.getInt(c.getColumnIndex(ProductionCompany.reputation)),
															c.getString(c.getColumnIndex(ProductionCompany.lastModified)), 
															UUID.fromString(c.getString(c.getColumnIndex(ProductionCompany.id))),
															null, 
															null);
				
				AlertDialog.Builder builder = new AlertDialog.Builder(ContinueGameActivity.this)
		        .setTitle(c.getString(c.getColumnIndex(ProductionCompany.name)))
		        .setMessage("Company Budget: $"+ c.getString(c.getColumnIndex(ProductionCompany.budget))+ "M")
		        .setPositiveButton("Load This Game", new Dialog.OnClickListener()
		        {
		        	@Override
		            public void onClick(DialogInterface dialogInterface, int i) {
		        		Intent intent = new Intent();
		        		intent.setClass(ContinueGameActivity.this, MakeFilmActivity.class);
		        		intent.putExtra(COMPANY, pc);
		        		startActivity(intent);
		        	}
		        }).setNegativeButton(android.R.string.cancel, new Dialog.OnClickListener()
		        {
		        	@Override
		            public void onClick(DialogInterface dialogInterface, int i) {

		        	}
		        });
		    	
		    	builder.create().show();
			}
		});

        db.close();
	}
	
	
    @Override  
    protected void onListItemClick(ListView l, View v, int position, long id) {  
    	
    	super.onListItemClick(l, v, position, id);
    	
    	Cursor c = (Cursor) getListView().getItemAtPosition(position);
    	
    	
    	
    	AlertDialog.Builder builder = new AlertDialog.Builder(this)
        .setTitle(c.getString(c.getColumnIndex(ProductionCompany.name)))//l.getSelectedItemPosition())
        .setMessage("Reputation: "+ c.getString(c.getColumnIndex(ProductionCompany.reputation)));
    	
    	builder.create().show();
      
  
    }  

}
