package es.albandroid.feria11;


import es.albandroid.feria11.updater.AutoUpdaterBD;
import es.albandroid.feria11.util.Fuentes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class MenuPrincipalActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		new AutoUpdaterBD(this.getApplicationContext()).execute("");
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		TextView text=(TextView) findViewById(R.id.textView1);
		text.setTypeface(Fuentes.get(this, 1));

	}

	public void clickHorarios(View v){
		startActivity(new Intent(this,HorariosActivity.class));
//		Intent intent = new Intent(this,CreditosActivity.class);
//		startActivity(intent);
	}

	public void clickMapa(View v){
		Intent intent = new Intent(this,MapaFeriaActivity.class);
//		Intent intent = new Intent(this,CreditosActivity.class);
		startActivity(intent);
	}

	public void clickCreditos(View v){
		Intent intent = new Intent(this,CreditosActivity.class);
		startActivity(intent);
	}

	public void clickMiFeria(View v){
		Intent intent = new Intent(this,MiFeriaActivity.class);
//		Intent intent = new Intent(this,CreditosActivity.class);
		startActivity(intent);
	}
	public void clickLaCerca(View v){
		Intent intent = new Intent(getApplicationContext(),CercaActivity.class);
//		Intent intent = new Intent(this,CreditosActivity.class);
		startActivity(intent);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) { // Handle item selection 
		switch (item.getItemId()) { 
		case 1: 
			Intent a = new Intent (getApplicationContext(), PreferenciasActivity.class);
			a.putExtra("file", "");
//			Intent a = new Intent(this,CreditosActivity.class);
			startActivity(a); 
			return true; 
		case 2: 
			Intent b = new Intent (getApplicationContext(), CreditosActivity.class);
			startActivity(b);
			return true;
		default: return super.onOptionsItemSelected(item); 
		} 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		menu.add(1,1,0,"Preferencias").setIcon(android.R.drawable.ic_menu_preferences);
		menu.add(1,2,0,"Creditos").setIcon(android.R.drawable.ic_menu_info_details);
		return true;
	}
}