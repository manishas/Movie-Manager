package ie.simo.movies.activities;

import ie.simo.movies.R;
import ie.simo.movies.dao.BoxOfficeDbAdapter;
import ie.simo.movies.dao.cursor.BoxOfficeCursorAdapter;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListAdapter;

public class PersonalBest extends ListActivity {
	private BoxOfficeDbAdapter db;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dbtest);

		db = new BoxOfficeDbAdapter(this);
		db.open();

		// Get users movies
		Cursor c = db.fetchUserMovies();
					
		ListAdapter adapter = new BoxOfficeCursorAdapter(this,c);
        setListAdapter(adapter);
        
//        ListView list = getListView();
//        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position,
//                    long id) {
//            	parent.
//            	Cursor cursor = (Cursor) parent.getAdapter();
//            	cursor.moveToPosition(position);
//            	//long categoryId = cursor.getLong(cursor.getColumnIndex(CategoryDataHelper.ID));
//
//            }});

                
        db.close();
	}
/*
    @Override  
    protected void onListItemClick(ListView l, View v, int position, long id) {  
    	
    	super.onListItemClick(l, v, position, id);
    	
    	Cursor c = (Cursor) getListView().getItemAtPosition(position);
    	
    	
    	
    	AlertDialog.Builder builder = new AlertDialog.Builder(this)
        .setTitle(c.getString(c.getColumnIndex("movie_name")))//l.getSelectedItemPosition())
        .setMessage("Directed by: "+ c.getString(c.getColumnIndex("director_id")))
        .setPositiveButton(android.R.string.ok, new Dialog.OnClickListener() {

            public void onClick(DialogInterface dialogInterface, int i) {
               
                dialogInterface.dismiss();
            }
        }
        );
    	
    	builder.create().show();
      
  
    }  
	
    */
	@Override
	protected void onPause() {
		db.close();
		super.onPause();
	}
	
	@Override
	protected void onDestroy(){
	
		db.close();
		super.onDestroy();
	}
}
