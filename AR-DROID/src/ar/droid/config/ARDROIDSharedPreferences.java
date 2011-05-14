package ar.droid.config;

import android.content.Context;
import android.content.SharedPreferences;

public class ARDROIDSharedPreferences {

	private Context context;
	private static ARDROIDSharedPreferences arDROIDSharedPreferences = null;
	
	protected ARDROIDSharedPreferences(Context context) {
		this.context = context;
	}
	
	public static final String PREFS_NAME = "ARDROID-CONFIG";
	 
	 public String getServer(){
		  SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, 0);
	      return settings.getString("SERVER", "ERROR");
	 }
	 
	 public static ARDROIDSharedPreferences getInstance(Context context){
		 if (arDROIDSharedPreferences == null){
			 arDROIDSharedPreferences = new ARDROIDSharedPreferences(context);
		 }
		  return arDROIDSharedPreferences;
		 
	 }
	 
	 
	 
	 

}
