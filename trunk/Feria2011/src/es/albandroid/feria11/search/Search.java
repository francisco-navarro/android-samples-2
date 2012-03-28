package es.albandroid.feria11.search;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import es.albandroid.feria11.CreditosActivity;
import es.albandroid.feria11.DetalleEventoActivity;
import es.albandroid.feria11.HorariosActivity;
import es.albandroid.feria11.R;
import es.albandroid.feria11.bbdd.BDFeria;
import es.albandroid.feria11.bbdd.BDFeriaSqlite;
import es.albandroid.feria11.bbdd.DBAdapter;
import es.albandroid.feria11.beans.Evento;
import es.albandroid.feria11.util.Constants;
import es.albandroid.feria11.util.Fuentes;
import es.albandroid.feria11.util.ListAdapter;
import es.albandroid.feria11.util.ListAdapter2;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class Search extends ListActivity {

	ArrayList<HashMap<String,String>> Eventos;

	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy, HH:mm");
	List<String> notelist;
	String query;
	private BDFeria BD;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		Intent intent = getIntent();
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			query = intent.getStringExtra(SearchManager.QUERY);
			setContentView(R.layout.search);
			if(Constants.isPhysicalDB){
				BD=new DBAdapter(this);        	
			}else{
				BD=new BDFeriaSqlite();        	
			}  
			TextView text1=(TextView) findViewById(R.id.titulo);
			text1.setTypeface(Fuentes.get(getApplicationContext(), 1));
			TextView text2=(TextView) findViewById(android.R.id.empty);
			text2.setTypeface(Fuentes.get(getApplicationContext(), 1));
			BD.open();
			createList();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) { // Handle item selection 
		switch (item.getItemId()) { 
		case 1: 
			Intent a = new Intent (getApplicationContext(), HorariosActivity.class);
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
		menu.add(1,1,0,"Programa").setIcon(android.R.drawable.ic_menu_revert);
		menu.add(1,2,0,"Creditos").setIcon(android.R.drawable.ic_menu_info_details);
		return true;
	}

	private void createList(){
		this.getListView().setVisibility(View.GONE); 
		String[] from=new String[] {"Day","Time","Name","Desc"};
		int[] to=new int[]{R.id.fecha,R.id.hora,R.id.nombre,R.id.desc1};
		Eventos = new ArrayList<HashMap<String, String>>();

		if(Constants.isPhysicalDB){
			ArrayList<Evento> lista = new ArrayList<Evento>();
			lista =(ArrayList<Evento>) BD.buscar(query);
			if (lista!=null)
			for(Evento evento:lista){
				HashMap<String,String> datosEvento=new HashMap<String, String>();
				datosEvento.put("Day", evento.getDiaFormat());
				datosEvento.put("Time", evento.getHoraInicioFormat());
				datosEvento.put("Name", evento.getNombre());
				datosEvento.put("Desc", evento.getDescripcion());
				datosEvento.put("id", evento.getId().toString());
				Eventos.add(datosEvento);
			}
		}
		ListAdapter2 HorariosList=new ListAdapter2(this, Eventos, R.layout.search_row, from, to);
		setListAdapter(HorariosList);
		this.getListView().setVisibility(View.VISIBLE);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		Intent in = new Intent(this,DetalleEventoActivity.class);

		in.putExtra(Constants.PARAM_ID_EVENTO,
				Integer.parseInt(Eventos.get(position).get("id")));
		startActivity(in);
	}

	@Override
	public void onPause() {
		super.onPause();
		BD.close();
	}
	@Override
	public void onResume() {
		super.onResume();
		BD.open();
	}

}
