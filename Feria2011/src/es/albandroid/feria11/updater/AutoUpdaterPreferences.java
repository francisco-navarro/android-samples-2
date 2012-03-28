package es.albandroid.feria11.updater;

import es.albandroid.feria11.util.Constants;

import android.content.Context;
import android.content.SharedPreferences;

public class AutoUpdaterPreferences {
	
	public static final String PREFERENCE_KEY_DB_VERSION="DB_VERSION";
	
	private static final String PROFILE="ProfileUpdater";
	
	private Context context;
	private SharedPreferences settings=null;
	
	public AutoUpdaterPreferences(Context context){
		
		this.context=context;		
		settings=this.context.getSharedPreferences(PROFILE, Context.MODE_PRIVATE);
		
	}
	
	public String getDB_Version(){
		return settings.getString(PREFERENCE_KEY_DB_VERSION,Constants.VERSION_DB_ACTUAL);
	}
	
	public void setDB_Version(String valor){
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(PREFERENCE_KEY_DB_VERSION,valor);
		editor.commit();
	}

}
