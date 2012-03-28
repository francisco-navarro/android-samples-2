package es.albandroid.feria11;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import es.albandroid.feria11.R;

import es.albandroid.feria11.bbdd.BDFeria;
import es.albandroid.feria11.bbdd.BDFeriaSqlite;
import es.albandroid.feria11.bbdd.DBAdapter;
import es.albandroid.feria11.beans.Evento;
import es.albandroid.feria11.listener.DragTouchListener;
import es.albandroid.feria11.util.Constants;
import es.albandroid.feria11.util.Fuentes;
import es.albandroid.feria11.util.ListAdapter;
import es.albandroid.feria11.util.TiposPreferences;

public class HorariosActivity extends ListActivity {

	private BDFeria BD;
	Date fecha = new Date();

	int inicio=2;
	int fin=25;
	
	TiposPreferences tipos;
	int tipo;

	ArrayList<HashMap<String,String>> Eventos;
	ArrayList<Date> fechas = new ArrayList<Date>();
	Spinner s;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		if(Constants.isPhysicalDB){
			BD=new DBAdapter(this);        	
		}else{
			BD=new BDFeriaSqlite();        	
		}  
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.horarios);

		Button text3=(Button) findViewById(R.id.filtro);
		text3.setTypeface(Fuentes.get(getApplicationContext(), 1));
		Button text4=(Button) findViewById(R.id.buscar);
		text4.setTypeface(Fuentes.get(getApplicationContext(), 1));
		TextView text2=(TextView) findViewById(android.R.id.empty);
		text2.setTypeface(Fuentes.get(getApplicationContext(), 1));
		tipos = new TiposPreferences(this);

		s = (Spinner) findViewById(R.id.spinner);
		MyArrayAdapter adapter = new MyArrayAdapter(this,
				R.layout.spinner, header());

		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		s.setAdapter(adapter);

		s.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				fecha = new Date();
				fecha.setTime(fechas.get(arg2).getTime());
				mostrarEventos(fecha,0);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}

		});
//		GregorianCalendar hoy = new GregorianCalendar();
//		hoy.setTimeInMillis(System.currentTimeMillis());
//		int dia = hoy.get(Calendar.DAY_OF_MONTH);
//		s.setSelection(dia-inicio);
		s.setSelection(8);
		fecha = new Date();
		fecha.setTime(fechas.get(s.getSelectedItemPosition()).getTime());
		BD.open();
		
		mostrarEventos(fecha, 0);

		findViewById(android.R.id.list).setOnTouchListener(new DragTouchListener(this));
	}

	private class MyArrayAdapter extends ArrayAdapter<String> {

		public MyArrayAdapter(Context context, int textViewResourceId, String[] objects) {
			super(context, textViewResourceId, objects);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = super.getView(position, convertView, parent);
			TextView text=(TextView) view.findViewById(android.R.id.text1);
			text.setTypeface(Fuentes.get(getApplicationContext(), 1));
			return view;
		}
		@Override
		public View getDropDownView(int position, View convertView, ViewGroup parent) {
			View view = super.getDropDownView(position, convertView, parent);
			TextView text=(TextView) view.findViewById(android.R.id.text1);
			text.setTypeface(Fuentes.get(getApplicationContext(), 1));
			return view;
		}

	}

	public String[] header(){
		fechas = new ArrayList<Date>();
		String[] headerData = new String[(fin-inicio)+1];
		int j=0;
		GregorianCalendar dia;
		for (int i=inicio;i<=fin;i++){
			dia = new GregorianCalendar();
			dia.set(2011, 8, i, 5, 0);
			headerData[j]= i+" de Sept.";			
			fechas.add(new Date(dia.getTimeInMillis()));
			j++;
		}
		return headerData;
	}

	public void botonAtras(View v){
		int i = s.getSelectedItemPosition();
		if(i>=0) {
			s.setSelection(i-1);
			mostrarEventos(fecha,-1);
		}
	}

	public void botonSiguiente(View v){
		int i = s.getSelectedItemPosition();
		if(i>=0 && i<=25) {
			s.setSelection(i+1);
			mostrarEventos(fecha,1);
		}
	}


	public Date getFecha() {
		return fecha;
	}

	public void mostrarEventos(Date fecha,int op){

		fecha.setTime(fecha.getTime()+(op*24*60*60*1000)); //puede dar problemas
		
		tipo = TiposPreferences.getTipo();

		String[] from=new String[] {"Time","Name","Desc"};
		int[] to=new int[]{R.id.hora,R.id.nombre,R.id.desc1};
		Eventos = new ArrayList<HashMap<String, String>>();

		if(Constants.isPhysicalDB){
			ArrayList<Evento> lista = new ArrayList<Evento>();
			if (tipo>=0){
				lista =(ArrayList<Evento>) BD.getEventoDiaTipo(fecha, tipo);
			}
			else {
				lista=(ArrayList<Evento>) BD.getEventoDia(fecha);
			}
			for(Evento evento:lista){
				HashMap<String,String> datosEvento=new HashMap<String, String>();
				datosEvento.put("Time", evento.getHoraInicioFormat());
				datosEvento.put("Name", evento.getNombre());
				datosEvento.put("Desc", evento.getDescripcion());
				datosEvento.put("id", evento.getId().toString());
				Eventos.add(datosEvento);
			}

		}

		ListAdapter HorariosList=new ListAdapter(this, Eventos, R.layout.horarios_row, from, to);

		setListAdapter(HorariosList);

	}
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		Intent in = new Intent(this,DetalleEventoActivity.class);

		in.putExtra(Constants.PARAM_ID_EVENTO,
				Integer.parseInt(Eventos.get(position).get("id")));
		startActivity(in);
	}

	public void filtrar(View v){
		tipos.mostrarAlert();
	}
	
	public void buscar(View v){
		onSearchRequested();
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
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) { // Handle item selection 
		switch (item.getItemId()) { 
		case 1: 
			Intent a = new Intent (getApplicationContext(), MenuPrincipalActivity.class);
			a.putExtra("file", "");
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
		menu.add(1,1,0,"Principal").setIcon(android.R.drawable.ic_menu_revert);
		menu.add(1,2,0,"Creditos").setIcon(android.R.drawable.ic_menu_info_details);
		return true;
	}
}