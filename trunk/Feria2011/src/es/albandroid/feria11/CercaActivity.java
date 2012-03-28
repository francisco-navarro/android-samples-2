package es.albandroid.feria11;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import es.albandroid.feria11.bbdd.BDFeria;
import es.albandroid.feria11.bbdd.BDFeriaSqlite;
import es.albandroid.feria11.bbdd.DBAdapter;
import es.albandroid.feria11.beans.Noticia;
import es.albandroid.feria11.json.JsonParser;
import es.albandroid.feria11.util.Constants;
import es.albandroid.feria11.util.Fuentes;
import es.albandroid.feria11.util.ListAdapter;
import es.albandroid.feria11.util.ListAdapter2;
import es.albandroid.feria11.util.ListAdapter3;
import es.albandroid.feria11.util.TiposPreferences;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class CercaActivity extends ListActivity {

	Date fecha = new Date();

	int inicio=1;
	int fin=25;


	int PAGINA = 0;
	int NOTICIAS_PAGINA = 10;

	TiposPreferences tipos;
	int tipo;
	private static final String PERFIL="perfil";
	ArrayList<HashMap<String,String>> Noticias;
	ArrayList<Date> fechas = new ArrayList<Date>();
	ListView list;
	String[] from=new String[] {"Name","Desc"};
	int[] to=new int[]{R.id.nombre,R.id.desc1};
	Spinner s;
	Boolean sw=false;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.cerca);
		SharedPreferences settings = getSharedPreferences(PERFIL, Context.MODE_PRIVATE);
   	 	NOTICIAS_PAGINA = settings.getInt("Noticias", 10);
		list = (ListView)findViewById(android.R.id.list);
		s = (Spinner) findViewById(R.id.spinner);
		MyArrayAdapter adapter = new MyArrayAdapter(getApplicationContext(),
				R.layout.spinner, header());
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		TextView text2=(TextView) findViewById(android.R.id.empty);
		text2.setTypeface(Fuentes.get(getApplicationContext(), 1));
		s.setAdapter(adapter);
		s.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				PAGINA = arg2;
				CargarNoticias(0);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}

		});
		//		PAGINA = s.getSelectedItemPosition();

		CargarNoticias(0);
	}

	private void CargarNoticias(final int op){
		final ProgressDialog pd = ProgressDialog.show(this,
				"",
				"Cargando Noticias ...",
				true);
		pd.setOnDismissListener(new OnDismissListener() {

			public void onDismiss(DialogInterface arg0) {
				Log.i("COJONES"," "+Noticias.size());

				ListAdapter3 NoticiasList=new ListAdapter3(getApplicationContext(), Noticias, R.layout.cerca_row, from, to);
				setListAdapter(NoticiasList);

			}
		});

		new Thread(new Runnable(){
			public void run(){
				mostrarNoticias(PAGINA, op);
				pd.dismiss();

			}
		}).start(); 
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
			headerData[j]= "  Página " + i;			
			j++;
		}
		return headerData;
	}

	public void botonAtras(View v){
		int i = s.getSelectedItemPosition();
		if(i>=0) {
			s.setSelection(i-1);
			CargarNoticias(-1);
		}
	}

	public void botonSiguiente(View v){
		int i = s.getSelectedItemPosition();
		if(i>=0) {
			s.setSelection(i+1);
			CargarNoticias(1);
		}
	}

	public void lacerca(View v){
		String url = "http://www.lacerca.com";	
		Intent i = new Intent(Intent.ACTION_VIEW);  
		i.setData(Uri.parse(url));  
		startActivity(i);
	}


	public Date getFecha() {
		return fecha;
	}

	public void mostrarNoticias(int pagina, int op){

		pagina=pagina+op; 

		Noticias = new ArrayList<HashMap<String, String>>();

		if(Constants.isPhysicalDB){
			ArrayList<Noticia> lista = new ArrayList<Noticia>();
			lista=JsonParser.parseaJson(pagina, NOTICIAS_PAGINA);
			//	lista = Noticia.getNoticias(tipo);
			for(Noticia noticia:lista){
				HashMap<String,String> datosEvento=new HashMap<String, String>();
				datosEvento.put("Name", noticia.getTitulo());
				datosEvento.put("Desc", noticia.getResumen());
				datosEvento.put("url", noticia.getURL().toString());
				Noticias.add(datosEvento);
			}
		}

		//		ListAdapter3 NoticiasList=new ListAdapter3(getApplicationContext(), Noticias, R.layout.cerca_row, from, to);
		//		setListAdapter(NoticiasList);


	}




	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		//		Intent in = new Intent(this,DetalleEventoActivity.class);
		//
		//		in.putExtra(Constants.PARAM_ID_NOTICIA,
		//				position);
		//		in.putExtra(Constants.PARAM_PAGINA,
		//				PAGINA);
		//		startActivity(in);

		String url = Noticias.get(position).get("url");	
		Intent i = new Intent(Intent.ACTION_VIEW);  
		i.setData(Uri.parse(url));  
		startActivity(i);
	}

	@Override
	public void onPause() {
		super.onPause();
	}
	@Override
	public void onResume() {
		super.onResume();
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


