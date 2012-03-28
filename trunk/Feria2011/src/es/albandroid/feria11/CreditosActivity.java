package es.albandroid.feria11;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.view.Window;

public class CreditosActivity extends PreferenceActivity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creditos);
        addPreferencesFromResource(R.xml.creditos);
        Preference setting = findPreference("misanmen");
        String url = "http://blog.netrunners.es/informacion/";  
        Intent i = new Intent(Intent.ACTION_VIEW);  
        i.setData(Uri.parse(url));  
		setting.setIntent(i);
	}

}
