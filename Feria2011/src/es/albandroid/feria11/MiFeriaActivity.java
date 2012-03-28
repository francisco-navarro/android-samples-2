package es.albandroid.feria11;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import es.albandroid.feria11.bbdd.AlarmAdapter;
import es.albandroid.feria11.bbdd.DBAdapter;
import es.albandroid.feria11.beans.Alarma;
import es.albandroid.feria11.beans.Evento;
import es.albandroid.feria11.util.Constants;
import es.albandroid.feria11.util.Fuentes;
import es.albandroid.feria11.util.ListAdapter;
import es.albandroid.feria11.util.ListAdapter2;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

public class MiFeriaActivity extends ListActivity {
	
	private ListView lista;
	private AlarmAdapter BD;
	private Date fecha;
	
	ArrayList<HashMap<String,String>> Eventos;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setContentView(R.layout.mi_feria);
	   
	    BD=new AlarmAdapter(this); 
	    
	    fecha=new Date();
	    fecha.setTime(1283838603000l);
	    TextView text1=(TextView) findViewById(R.id.titulo);
		text1.setTypeface(Fuentes.get(getApplicationContext(), 1));
		TextView text2=(TextView) findViewById(android.R.id.empty);
		text2.setTypeface(Fuentes.get(getApplicationContext(), 1));
	    
	    lista=(ListView)findViewById(android.R.id.list);
	    lista.setAdapter(mostrarEventos());
	}
	
	
	public ListAdapter2 mostrarEventos(){
		
		String[] from=new String[] {"Day","Time","Name","Desc"};
		int[] to=new int[]{R.id.fecha,R.id.hora,R.id.nombre,R.id.desc1};
		Eventos = new ArrayList<HashMap<String, String>>();
		if(Constants.isPhysicalDB){
			List<Alarma> lista = null;
			
			BD.open();			
			DBAdapter datosEventos=new DBAdapter(this);
			datosEventos.open();
			
			lista=BD.getAlarmas();
			
			for(Alarma alarma:lista){
				Evento evento=datosEventos.getEventoById(alarma.getIdEvento());
				
				HashMap<String,String> datosEvento=new HashMap<String, String>();
				datosEvento.put("Day", evento.getDiaFormat());
				datosEvento.put("Time", evento.getHoraInicioFormat());
				datosEvento.put("Name", evento.getNombre());
				datosEvento.put("Desc", evento.getDescripcion());
				datosEvento.put("id", alarma.getIdEvento().toString());
				Eventos.add(datosEvento);
			}
			datosEventos.close();
			BD.close();
		}

		ListAdapter2 HorariosList=new ListAdapter2(this, Eventos, R.layout.search_row, from, to);

		
		return HorariosList;

	}

	
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		
			super.onListItemClick(l, v, position, id);

			Intent in = new Intent(this,DetalleEventoActivity.class);

			in.putExtra(Constants.PARAM_ID_EVENTO,
					Integer.parseInt(Eventos.get(position).get("id")));
			startActivity(in);
		
	}
}
