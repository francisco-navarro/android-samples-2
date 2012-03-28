package es.albandroid.feria11.util.calendar;

import es.albandroid.feria11.R;

import es.albandroid.feria11.beans.Evento;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.widget.Toast;

public class FabricaGoogleCalendar {

	private String idCalendarioPersonal;
	private ContentResolver contentResolver;
	private Activity act;
	
	
	public void setAct(Activity act) {
		this.act = act;
	}

	public FabricaGoogleCalendar(Activity act){

		contentResolver = act.getContentResolver();		 

		final Cursor cursor = contentResolver.query(Uri.parse("content://com.android.calendar/calendars"),
				(new String[] { "_id", "displayName", "selected" }), "selected=1", null, null);
		if (cursor!=null){ 
			while (cursor.moveToNext()) {

				final String _id = cursor.getString(0);
				final String displayName = cursor.getString(1);

				if(displayName.contains("@"))
					idCalendarioPersonal=_id;
			}

		}
	}

	public void insertaEvento(Evento evento){

		if(isEventoInserted(evento))
			return;

		ContentValues event = new ContentValues();
		event.put("calendar_id", idCalendarioPersonal);

		event.put("title", evento.getNombre());
		event.put("description",  evento.getDescripcion());
		long startTime = evento.getHoraInicio().getTime();
		long endTime = evento.getHoraInicio().getTime()+3600000;
		event.put("dtstart", startTime);
		event.put("dtend", endTime);

		event.put("hasAlarm", 1);


		Uri eventsUri = Uri.parse("content://com.android.calendar/events");
		Uri newEvent = contentResolver.insert(eventsUri, event);

		if (newEvent != null) {
			long id = Long.parseLong( newEvent.getLastPathSegment() );
			ContentValues values = new ContentValues();
			values.put( "event_id", id );
			values.put( "method", 1 );
			values.put( "minutes", 15 ); // 15 minutos antes
			contentResolver.insert( Uri.parse( "content://com.android.calendar/reminders" ), values );
			//si no es froyo
			//cr.insert( Uri.parse( "content://calendar/reminders" ), values );

		}

		Toast.makeText(act, R.string.detalle_evento_guardado_google_calendar, Toast.LENGTH_LONG).show();

	}

	public void borraEvento(Evento evento) {


	}

	private boolean isEventoInserted(Evento evento){

		Uri eventsUri = Uri.parse("content://com.android.calendar/events");
		Cursor cursor=contentResolver.query(eventsUri,(new String[] { "title","dtstart"}), 				
				"title= ? AND dtstart= ?"
				, (new String[] { evento.getNombre(),String.valueOf(evento.getHoraInicio().getTime())}), null);

		if (cursor!=null){
			int titleColumn = cursor.getColumnIndex("title");

			if(cursor.moveToFirst())
				do {


					System.out.println(cursor.getString(titleColumn));

					return true;
				}while(cursor.moveToNext());
		}
		return false;
	}


}
