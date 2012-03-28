package es.albandroid.feria11;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.Window;

public class PreferenciasActivity extends PreferenceActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
	}

}
