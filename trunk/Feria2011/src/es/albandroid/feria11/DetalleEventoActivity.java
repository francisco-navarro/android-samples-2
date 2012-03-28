package es.albandroid.feria11;

import java.util.Date;
import es.albandroid.feria11.R;

import es.albandroid.feria11.bbdd.AlarmAdapter;
import es.albandroid.feria11.bbdd.BDFeria;
import es.albandroid.feria11.bbdd.BDFeriaSqlite;
import es.albandroid.feria11.bbdd.DBAdapter;
import es.albandroid.feria11.beans.Alarma;
import es.albandroid.feria11.beans.Evento;
import es.albandroid.feria11.util.Constants;
import es.albandroid.feria11.util.FormatoFechas;
import es.albandroid.feria11.util.Fuentes;
import es.albandroid.feria11.util.Months;
import es.albandroid.feria11.util.calendar.FabricaGoogleCalendar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DetalleEventoActivity extends Activity {

	private BDFeria BD;
	private AlarmAdapter alarmasBD;
	Evento evento;
	private Integer idEvento;
	private FabricaGoogleCalendar fabrica;
	private Alarma alarma;
	private boolean checked=false;
	Button btnAlarma;
	private static final String PERFIL="perfil";
	SharedPreferences settings;
	Boolean EsEvento=true;
	TextView img;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.detalle_evento);
		idEvento=this.getIntent().getExtras().getInt(Constants.PARAM_ID_EVENTO);
		btnAlarma= (Button) findViewById(R.id.detalle_evento_botonRecordar);
		btnAlarma.setTypeface(Fuentes.get(this, 2));
		Button btnMapa= (Button) findViewById(R.id.detalle_botonMapa);
		btnMapa.setTypeface(Fuentes.get(this, 2));
		Button btnShare= (Button) findViewById(R.id.detalle_botonCalendar);
		btnShare.setTypeface(Fuentes.get(this, 2));

		if(Constants.isPhysicalDB){
			BD=new DBAdapter(this); 
			alarmasBD=new AlarmAdapter(this);
		}else{
			BD=new BDFeriaSqlite();        	
		}

		cargaDatosPantalla();

		fabrica=new FabricaGoogleCalendar(this);	

	}
	public void setChecked(Boolean chk){
		checked=chk;
		if (chk)
			btnAlarma.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_detalle_alarm_on));

		else 
			btnAlarma.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_detalle_alarm_off));
	}

	public boolean isChecked(){
		return checked;
	}
	public void verEnMapa(View v){
		Intent i = new Intent (this, MapaFeriaActivity.class);
		i.putExtra(Constants.PARAM_ID_EVENTO, idEvento);
		startActivity(i);
	}

	private void cargaDatosPantalla(){
		img = (TextView) findViewById (R.id.imageIconoCalendar);
		if(BD!=null){
			BD.open();
			evento=BD.getEventoById(idEvento);
			BD.close();
			setTituloEvento(evento.getNombre());
			if(!(evento.getHoraInicio()==null || evento.getHoraInicio().getTime()==1000l )) {
				img.setVisibility(TextView.VISIBLE);
				setDiaEvento(Integer.toString(evento.getHoraInicio().getDate()));

				setHoraEvento( 
						FormatoFechas.getHora(evento.getHoraInicio())
						+" - "+
						FormatoFechas.getHora(new Date(evento.getHoraInicio().getTime()+(2*3600*1000)))   				
				);
				EsEvento=true;
			}
			else {
				EsEvento=false;
				img.setVisibility(TextView.INVISIBLE);
				setDiaEvento ("X");
				setHoraEvento("");
				btnAlarma.setClickable(false);
			}
			setDescripcionEvento(evento.getDescripcion2());
			if(evento.getTipo().intValue()>20)
				setHoraEvento("        ");
			//			setDescripcion2Evento(evento.getDescripcion2());

			//comprobar si existe la alarma, y entonces activo el boton
			try{
				alarmasBD.open();
				alarma=alarmasBD.getAlarmabyIdAlarma(90+evento.getId());
				alarmasBD.close();
				if(alarma!=null && alarma.getIdEvento().intValue()>0)
					setChecked(true);
				else 
					setChecked(false);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}    
	}

	private void setTituloEvento(String s){    
		TextView text=(TextView) findViewById(R.id.detalle_textTitulo);
		text.setTypeface(Fuentes.get(this, 1));
		text.setText(s);
	}

	private void setDiaEvento(String s){    
		TextView text=(TextView) findViewById(R.id.imageIconoCalendar);
		text.setTypeface(Fuentes.get(this, 2));
		text.setText(s);
	}
	private void setHoraEvento(String s){    
		TextView text=(TextView) findViewById(R.id.detalle_textHora);
		text.setTypeface(Fuentes.get(this, 1));
		text.setText(s);
	}
	private void setDescripcionEvento(String s){    
		TextView text=(TextView) findViewById(R.id.detalle_textDesc1);
		text.setMovementMethod(new ScrollingMovementMethod());
		text.setTypeface(Fuentes.get(this, 3));
		text.setText(s);
	}

	public void crearAlarma(View v){
		BD.open();
		Evento evento=BD.getEventoById(idEvento);
		BD.close();
		int ALARM_REQUEST_CODE=90+evento.getId();

		if(isChecked()){			

			Intent intent = new Intent(getApplicationContext(), OnAlarmReceiver.class);
			PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),ALARM_REQUEST_CODE,
					intent, PendingIntent.FLAG_CANCEL_CURRENT);
			intent.putExtra("parametro", ALARM_REQUEST_CODE+" ");
			AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
			alarmManager.cancel(pendingIntent);
			if(alarma.getIdAlarma()!=null && alarma.getIdAlarma().intValue()>0){
				alarmasBD.open();
				if (alarmasBD.removeAlarma(alarma.getIdEvento())){
					Toast.makeText(getApplicationContext(), R.string.detalle_evento_evento_alarma_eliminado, Toast.LENGTH_LONG).show();			
					setChecked(false);
				}
				alarmasBD.close();
			}
		}else 
			if(evento.getHoraInicio().before(new Date(System.currentTimeMillis())) ){

				Toast.makeText(getApplicationContext(), R.string.detalle_evento_evento_alarma_outatime, Toast.LENGTH_LONG).show();
				setChecked(false);

			}else{
				settings = getApplicationContext().getSharedPreferences(PERFIL, Context.MODE_PRIVATE);
				long time = settings.getInt("Tiempo", 30000);
				Intent intent = new Intent(getApplicationContext(), OnAlarmReceiver.class);
				PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), ALARM_REQUEST_CODE, intent, 1);
				AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
				alarmManager.set(AlarmManager.RTC_WAKEUP,evento.getHoraInicio().getTime()-time,pendingIntent);

				alarmasBD.open();
				alarmasBD.insertAlarma(evento.getId(), ALARM_REQUEST_CODE);
				alarmasBD.close();

				Toast.makeText(getApplicationContext(), R.string.detalle_evento_evento_alarma_guardado, Toast.LENGTH_LONG).show();
				setChecked(true);
			}
	}

	public void insertarCalendar(View v){
		final Intent intent = new Intent(Intent.ACTION_SEND);
		String str=new String();
		if (EsEvento) {
		Date dia = evento.getHoraInicio();
		str = "El día "+dia.getDate()+" de "+Months.get(dia.getMonth())
		+" a las "+evento.getHoraInicioFormat()
		+" asistiré al evento: "+evento.getNombre()
		+" (más Info: https://market.android.com/details?id=es.albandroid.feria11 )";
		}
		else {
			str = "Podréis encontrarme en " +evento.getNombre()
			+" (más Info: https://market.android.com/details?id=es.albandroid.feria11 )";
		}
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_SUBJECT, "Feria de Albacete 2011");
		intent.putExtra(Intent.EXTRA_TEXT, str);
		
		startActivity(Intent.createChooser(intent, getString(R.string.detalle_evento_compartir)));



		//		BD.open();
		//		Evento evento=BD.getEventoById(idEvento);
		//		BD.close();
		//		
		//		if(fabrica==null)
		//			fabrica=new FabricaGoogleCalendar(this);
		//		fabrica.setAct(this);
		//		fabrica.insertaEvento(evento);
	}


	public void borrarCalendar(View v){

		BD.open();
		Evento evento=BD.getEventoById(idEvento);
		BD.close();

		if(fabrica==null)
			fabrica=new FabricaGoogleCalendar(this);	
		fabrica.setAct(this);
		fabrica.borraEvento(evento);
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