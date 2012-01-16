package ie.simo.movies.dao;

import ie.simo.movies.activities.R;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper {
	
	private static final String DATABASE_NAME = "moviemanager";
	private static final int DATABASE_VERSION = 1;

	 protected Context context;
	        
        public DatabaseHelper(Context context) {
                super(context, DATABASE_NAME, null, 1);
                this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
                String s;
                try {
                        Toast.makeText(context, "1", 2000).show();
                        InputStream in = context.getResources().openRawResource(R.raw.sql);
                        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                        Document doc = builder.parse(in, null);
                        NodeList statements = doc.getElementsByTagName("statement");
                        for (int i=0; i<statements.getLength(); i++) {
                                s = statements.item(i).getChildNodes().item(0).getNodeValue();
                                db.execSQL(s);
                                Log.d("DBHELPER","sql executed");
                        }
                } catch (Throwable t) {
                        Toast.makeText(context, t.toString(), 50000).show();
                }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                db.execSQL("DROP TABLE IF EXISTS movie");
                db.execSQL("DROP TABLE IF EXISTS actor");
                db.execSQL("DROP TABLE IF EXISTS director");
                db.execSQL("DROP TABLE IF EXISTS genre");
                //db.execSQL("DROP TABLE IF EXISTS producer");
                db.execSQL("DROP TABLE IF EXISTS cast");
                db.execSQL("DROP TABLE IF EXISTS distributor");
                
                onCreate(db);
        }
        
}
