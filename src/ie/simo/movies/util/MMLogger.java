package ie.simo.movies.util;

import android.util.Log;

public class MMLogger{

	private static boolean loggingEnabled = true;
	
	public static void v(String tag, String message){
		if(loggingEnabled){
			Log.v(tag, message);
		}
	}
	
	public static void e(String tag, String message){
		if(loggingEnabled){
			Log.e(tag, message);
		}
	}
	
	public static void wtf(String tag, String message){
		if(loggingEnabled){
			Log.wtf(tag, message);
		}
	}
	
	public static void d(String tag, String message){
		if(loggingEnabled){
			Log.d(tag, message);
		}
	}
}
