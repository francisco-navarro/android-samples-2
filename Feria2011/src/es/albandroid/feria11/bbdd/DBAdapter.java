package es.albandroid.feria11.bbdd;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import es.albandroid.feria11.beans.Evento;
import es.albandroid.feria11.util.Months;
import es.albandroid.feria11.util.images.Iconos;
import es.albandroid.feria11.util.maps.MapaPreferences;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.*;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

public class DBAdapter implements BDFeria{
	public static String DATABASE_PATH = "/data/data/es.albandroid.feria11/databases/";
	public static final String DATABASE_NAME = "feria2011.db";
	private static final String DATABASE_TABLE = "events";
	private static final int DATABASE_VERSION = 1;
	private SQLiteDatabase db;
	private final Context context;
	public static final String KEY_ID = "_id";
	public final static String KEY_COL1 = "nombre";
	public final static String KEY_COL2 = "descripcion1";
	public final static String KEY_COL3 = "descripcion2";
	public final static String KEY_COL4 = "coord_x";
	public final static String KEY_COL5 = "coord_y";
	public final static String KEY_COL_INICIO = "horainicio";
	public final static String KEY_COL_FIN = "horafin";
	public final static String KEY_COL8 = "tipo";
	public final static String KEY_COL9 = "icono";

	private static final String[] cols = new String[] { KEY_ID, KEY_COL1, KEY_COL2, KEY_COL3, KEY_COL4
		, KEY_COL5, KEY_COL_INICIO, KEY_COL_FIN, KEY_COL8, KEY_COL9};

	private DBOpenHelper dbHelper;

	public DBAdapter(Context _context) {
		this.context = _context;
		if(new File(DATABASE_PATH+DATABASE_NAME+".last").exists())
			dbHelper = new DBOpenHelper(context, DATABASE_NAME+".last",
					null, DATABASE_VERSION);
		else
			dbHelper = new DBOpenHelper(context, DATABASE_NAME,
				null, DATABASE_VERSION);
	}

	private static class DBOpenHelper extends SQLiteOpenHelper {

		private final Context mycontext;

		public DBOpenHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
			this.mycontext=context;
		}
		// SQL Statement to create a new database.
		private static final String DATABASE_CREATE = "create table if not exists " +
		DATABASE_TABLE + " (" + KEY_ID + " integer primary key, " +
		KEY_COL1 + " text not null, " 
		+ KEY_COL2 + " text not null, "
		+ KEY_COL3 +" text, "
		+ KEY_COL4 +" numeric, "
		+ KEY_COL5 +" numeric, "
		+ KEY_COL_INICIO +" numeric, "
		+ KEY_COL_FIN +" numeric"
		+ KEY_COL8 +" numeric"
		+ KEY_COL9 +" numeric"
		+");";

		@Override
		public void onCreate(SQLiteDatabase _db) {

		}

		public void createDataBase() throws IOException{

			boolean dbExist = this.checkDataBase();

			if(dbExist){
				this.getReadableDatabase();

				try {

					copyDataBase();

				} catch (IOException e) {

					throw new Error("Error copying database");

				}
			}else{

				//By calling this method and empty database will be created into the default system path
				//of your application so we are gonna be able to overwrite that database with our database.
				this.getReadableDatabase();

				try {

					copyDataBase();

				} catch (IOException e) {

					throw new Error("Error copying database");

				}
			}

		}

		private boolean checkDataBase(){

			SQLiteDatabase checkDB = null;

			try{
				String myPath = DATABASE_PATH + DATABASE_NAME;
				checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
			}catch(SQLiteException e){

				//database does't exist yet.

			}

			if(checkDB != null){

				checkDB.close();

			}

			return checkDB != null ? true : false;
		}

		private void copyDataBase() throws IOException{

			//Open your local db as the input stream
			InputStream myInput = mycontext.getAssets().open(DATABASE_NAME);

			// Path to the just created empty db
			String outFileName = DATABASE_PATH + DATABASE_NAME;

			//Open the empty db as the output stream
			OutputStream myOutput = new FileOutputStream(outFileName);

			//transfer bytes from the inputfile to the outputfile
			byte[] buffer = new byte[1024];
			int length;
			while ((length = myInput.read(buffer))>0){
				myOutput.write(buffer, 0, length);
			}

			//Close the streams
			myOutput.flush();
			myOutput.close();
			myInput.close();

		}

		@Override
		public void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion) {
			//			Log.w("DBAdapter", "Upgrading from version " +
			//					_oldVersion + " to " +
			//					_newVersion + ", which will destroy all old data");
			//			// Drop the old table.
			//			_db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			//			// Create a new one.
			//			onCreate(_db);
		}

	}

	@Override
	public synchronized void close() {	 
		if(db != null)
			db.close();
	}

	public void dropTable() {
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
	}

	@Override
	public void open() throws SQLiteException {
		//		try {
		//			db = dbHelper.getWritableDatabase();
		//			db.execSQL("create table if not exists " +
		//			DATABASE_TABLE + " (" + KEY_ID + " integer primary key, " +
		//			KEY_COL1 + " text not null, " 
		//			+ KEY_COL2 + " text not null, "
		//			+ KEY_COL3 +" text, "
		//			+ KEY_COL4 +" numeric, "
		//			+ KEY_COL5 +" numeric, "
		//			+ KEY_COL_INICIO +" numeric, "
		//			+ KEY_COL_FIN +" numeric"
		//			+");");
		try {
			dbHelper.createDataBase();
		} catch (IOException e) {
			throw new Error("Unable to create database");
		}
		String myPath = DATABASE_PATH + DATABASE_NAME;
		if(new File(myPath+".last").exists())
			myPath=myPath+".last";
		db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
		//		} catch (SQLiteException ex) {
		//			db = dbHelper.getReadableDatabase();
		//		}
	}
	//	 Insert a new task
	public long insertEvent(Integer id, 
			String nombre, String descripcion, 
			String descripcion2, Double Coordenada_x, 
			Double Coordenada_y, long horainicio, long horafin,
			Integer tipo, Integer icono) {
		// Create a new row of values to insert.
		ContentValues newValues = new ContentValues();
		// Assign values for each row.
		newValues.put(KEY_ID, id);
		newValues.put(KEY_COL1, nombre);
		newValues.put(KEY_COL3, descripcion);
		newValues.put(KEY_COL2, descripcion2);
		newValues.put(KEY_COL4, Coordenada_x);
		newValues.put(KEY_COL5, Coordenada_y);
		newValues.put(KEY_COL_INICIO, horainicio);
		newValues.put(KEY_COL_FIN, horafin);
		newValues.put(KEY_COL8, tipo);
		newValues.put(KEY_COL9, icono);
		// Insert the row.
		return db.insert(DATABASE_TABLE, null, newValues);
	}
	// Remove a task based on its index
	//	public boolean removeTask(long _rowIndex) {
	//		return db.delete(DATABASE_TABLE, KEY_ID + "=" + _rowIndex, null) > 0;
	//	}
	// Update a task
	//	public boolean updateSetting(long _rowIndex, int s, int def, String str) {
	//		ContentValues newValues = new ContentValues();
	//		if (s>-1){
	//			newValues.put(KEY_COL1,s);}
	//		else if (def>-1)
	//		{newValues.put(KEY_COL2, def);}
	//		else if (str.length()>0)
	//		{newValues.put(KEY_COL3, str);}
	//		return db.update(DATABASE_TABLE, newValues, KEY_ID + "=" + _rowIndex, null) > 0;
	//	}

	public Cursor getAllEventsItemsCursor() {
		return  db.query(DATABASE_TABLE,
				cols,
				null, null, null, null, KEY_ID);
	}
	public Cursor getCursorEventItem(long _rowIndex) throws SQLException {
		Cursor result = db.query(true, DATABASE_TABLE, cols,
				KEY_ID + "=" + _rowIndex, null, null, null,
				null, null);
		if ((result.getCount() == 0) || !result.moveToFirst()) {
			throw new SQLException("No items found for row: " + _rowIndex);
		}
		return result;
	}

	@Override
	public List<Evento> getEventoDia(Date date) {
		ArrayList<Evento> result = new ArrayList<Evento>();
		Date HoraInicio = new Date();
		Date HoraFin = new Date();
		GregorianCalendar dia1 = new GregorianCalendar();
		dia1.setTime(date);
//		Date cutover = new Date(Long.MAX_VALUE);
//		dia1.setGregorianChange(cutover);
		GregorianCalendar dia2 = new GregorianCalendar();
//		dia2.set(dia1.get(Calendar.YEAR), dia1.get(Calendar.MONTH), dia1.get(Calendar.DAY_OF_MONTH)+1, 
//				dia1.get(Calendar.HOUR_OF_DAY)+2, dia1.get(Calendar.MINUTE));
		dia2=(GregorianCalendar) dia1.clone();
		dia2.add(Calendar.HOUR, 24);
		Cursor events = db.query(DATABASE_TABLE,
				cols,
				KEY_COL_INICIO + " BETWEEN "+ dia1.getTimeInMillis()+ " AND "+ dia2.getTimeInMillis()
				, null, null, null, KEY_COL_INICIO);
		if (events.moveToFirst())
			do {
				HoraInicio= new Date(events.getLong(events.getColumnIndex(KEY_COL_INICIO)));
				HoraFin= new Date(events.getLong(events.getColumnIndex(KEY_COL_FIN)));
				result.add(new Evento(
						events.getInt(events.getColumnIndex(KEY_ID)),
						events.getString(events.getColumnIndex(KEY_COL1)),
						events.getString(events.getColumnIndex(KEY_COL2)),
						events.getString(events.getColumnIndex(KEY_COL3)),
						events.getDouble(events.getColumnIndex(KEY_COL4)),
						events.getDouble(events.getColumnIndex(KEY_COL5)),
						HoraInicio,
						HoraFin,
						events.getInt(events.getColumnIndex(KEY_COL8)),
						events.getInt(events.getColumnIndex(KEY_COL9))
				)
				);
			} while(events.moveToNext());
		return result;
	}

	@Override
	public List<Evento> getEventoDiaIcono(Date date, Integer icono) {
		ArrayList<Evento> result = new ArrayList<Evento>();
		Date HoraInicio = new Date();
		Date HoraFin = new Date();
		GregorianCalendar dia1 = new GregorianCalendar();
		dia1.setTime(date);
		Log.e("COJONES",""+date.getDate()+" de "+Months.get(date.getMonth()));
		GregorianCalendar dia2 = new GregorianCalendar();
		dia2.set(dia1.get(Calendar.YEAR), dia1.get(Calendar.MONTH), dia1.get(Calendar.DAY_OF_MONTH)+1, 
				dia1.get(Calendar.HOUR_OF_DAY)+2, dia1.get(Calendar.MINUTE));
		Cursor events = db.query(DATABASE_TABLE,
				cols,
				"(("+KEY_COL_INICIO + " BETWEEN "+ dia1.getTimeInMillis()+ " AND "+ dia2.getTimeInMillis()
				+") OR ("+KEY_COL_INICIO+"<"+dia1.getTimeInMillis()+" AND "+KEY_COL_FIN+" > " +dia1.getTimeInMillis()+"))" +
				" AND "+
				KEY_COL9+"="+icono
				, null, null, null, KEY_ID);
		Log.e("COJONES",""+events.getCount());
		if (events.moveToFirst())
			do {
				HoraInicio= new Date(events.getLong(events.getColumnIndex(KEY_COL_INICIO)));
				HoraFin= new Date(events.getLong(events.getColumnIndex(KEY_COL_FIN)));
				result.add(new Evento(
						events.getInt(events.getColumnIndex(KEY_ID)),
						events.getString(events.getColumnIndex(KEY_COL1)),
						events.getString(events.getColumnIndex(KEY_COL2)),
						events.getString(events.getColumnIndex(KEY_COL3)),
						events.getDouble(events.getColumnIndex(KEY_COL4)),
						events.getDouble(events.getColumnIndex(KEY_COL5)),
						HoraInicio,
						HoraFin,
						events.getInt(events.getColumnIndex(KEY_COL8)),
						events.getInt(events.getColumnIndex(KEY_COL9))
				)
				);
			} while(events.moveToNext());
		return result;
	}


	public List<Evento> getPruebas(Date date,Integer tipo, Integer icono) {
		ArrayList<Evento> result = new ArrayList<Evento>();
		Date HoraInicio = new Date();
		Date HoraFin = new Date();
		GregorianCalendar dia1 = new GregorianCalendar();
		dia1.setTime(date);
		Date cutover = new Date(Long.MAX_VALUE);
		dia1.setGregorianChange(cutover);
		GregorianCalendar dia2 = new GregorianCalendar();
		dia2.set(dia1.get(Calendar.YEAR), dia1.get(Calendar.MONTH), dia1.get(Calendar.DAY_OF_MONTH)+1, 
				dia1.get(Calendar.HOUR_OF_DAY)+2, dia1.get(Calendar.MINUTE));
		Cursor events = db.query(DATABASE_TABLE,
				cols,
				null
				, null, null, null, KEY_ID);
		if (events.moveToFirst())
			do {
				HoraInicio= new Date(events.getLong(events.getColumnIndex(KEY_COL_INICIO)));
				HoraFin= new Date(events.getLong(events.getColumnIndex(KEY_COL_FIN)));
				result.add(new Evento(
						events.getInt(events.getColumnIndex(KEY_ID)),
						events.getString(events.getColumnIndex(KEY_COL1)),
						events.getString(events.getColumnIndex(KEY_COL2)),
						events.getString(events.getColumnIndex(KEY_COL3)),
						events.getDouble(events.getColumnIndex(KEY_COL4)),
						events.getDouble(events.getColumnIndex(KEY_COL5)),
						HoraInicio,
						HoraFin,
						events.getInt(events.getColumnIndex(KEY_COL8)),
						events.getInt(events.getColumnIndex(KEY_COL9))
				)
				);
			} while(events.moveToNext());
		return result;
	}

	@Override
	public List<Evento> buscar(String query) {
		ArrayList<Evento> result = new ArrayList<Evento>();
		Date HoraInicio = new Date();
		Date HoraFin = new Date();
		Cursor events = db.query(DATABASE_TABLE,
				cols,
				"(("+ KEY_COL1 + " LIKE '%"+ query + "%') OR ("+ KEY_COL2 + " LIKE '%"+ query + "%'"
				+") OR ("+KEY_COL3 + " LIKE '%"+ query + "%'))"
				+" AND "+KEY_COL8+" NOT IN (0,21,22,23)"
				, null, null, null, KEY_COL_INICIO);
		if (events.moveToFirst())
			do {
				HoraInicio= new Date(events.getLong(events.getColumnIndex(KEY_COL_INICIO)));
				HoraFin= new Date(events.getLong(events.getColumnIndex(KEY_COL_FIN)));
				result.add(new Evento(
						events.getInt(events.getColumnIndex(KEY_ID)),
						events.getString(events.getColumnIndex(KEY_COL1)),
						events.getString(events.getColumnIndex(KEY_COL2)),
						events.getString(events.getColumnIndex(KEY_COL3)),
						events.getDouble(events.getColumnIndex(KEY_COL4)),
						events.getDouble(events.getColumnIndex(KEY_COL5)),
						HoraInicio,
						HoraFin,
						events.getInt(events.getColumnIndex(KEY_COL8)),
						events.getInt(events.getColumnIndex(KEY_COL9))
				)
				);
			} while(events.moveToNext());
		return result;
	}
	
	@Override
	public List<Evento> getEventoDiaTipo(Date date, Integer tipo) {
		ArrayList<Evento> result = new ArrayList<Evento>();
		Date HoraInicio = new Date();
		Date HoraFin = new Date();
		GregorianCalendar dia1 = new GregorianCalendar();
		dia1.setTime(date);
//		Date cutover = new Date(Long.MAX_VALUE);
//		dia1.setGregorianChange(cutover);
		GregorianCalendar dia2 = new GregorianCalendar();
//		dia2.set(dia1.get(Calendar.YEAR), dia1.get(Calendar.MONTH), dia1.get(Calendar.DAY_OF_MONTH)+1, 
//				2, dia1.get(Calendar.MINUTE));
		dia2=(GregorianCalendar) dia1.clone();
		dia2.add(Calendar.HOUR, 24);
		Cursor events = db.query(DATABASE_TABLE,
				cols,
				"(("+KEY_COL_INICIO + " BETWEEN "+ dia1.getTimeInMillis()+ " AND "+ dia2.getTimeInMillis()
				+") OR ("+KEY_COL_INICIO+"<"+dia1.getTimeInMillis()+" AND "+KEY_COL_FIN+" > " +dia1.getTimeInMillis()+"))" 
				+" AND "+KEY_COL8+"="+tipo
				, null, null, null, KEY_COL_INICIO);
		if (events.moveToFirst())
			do {
				HoraInicio= new Date(events.getLong(events.getColumnIndex(KEY_COL_INICIO)));
				HoraFin= new Date(events.getLong(events.getColumnIndex(KEY_COL_FIN)));
				result.add(new Evento(
						events.getInt(events.getColumnIndex(KEY_ID)),
						events.getString(events.getColumnIndex(KEY_COL1)),
						events.getString(events.getColumnIndex(KEY_COL2)),
						events.getString(events.getColumnIndex(KEY_COL3)),
						events.getDouble(events.getColumnIndex(KEY_COL4)),
						events.getDouble(events.getColumnIndex(KEY_COL5)),
						HoraInicio,
						HoraFin,
						events.getInt(events.getColumnIndex(KEY_COL8)),
						events.getInt(events.getColumnIndex(KEY_COL9))
				)
				);
			} while(events.moveToNext());
		return result;
	}
	
	public List<Evento> getEventoTipo(Integer tipo) {
		ArrayList<Evento> result = new ArrayList<Evento>();
		Date HoraInicio,HoraFin;
	

		Cursor events = db.query(DATABASE_TABLE,
				cols,KEY_COL8+"="+tipo
				, null, null, null, KEY_ID);
		if (events.moveToFirst())
			do {
				HoraInicio= new Date(events.getLong(events.getColumnIndex(KEY_COL_INICIO)));
				HoraFin= new Date(events.getLong(events.getColumnIndex(KEY_COL_FIN)));
				result.add(new Evento(
						events.getInt(events.getColumnIndex(KEY_ID)),
						events.getString(events.getColumnIndex(KEY_COL1)),
						events.getString(events.getColumnIndex(KEY_COL2)),
						events.getString(events.getColumnIndex(KEY_COL3)),
						events.getDouble(events.getColumnIndex(KEY_COL4)),
						events.getDouble(events.getColumnIndex(KEY_COL5)),
						HoraInicio,
						HoraFin,
						events.getInt(events.getColumnIndex(KEY_COL8)),
						events.getInt(events.getColumnIndex(KEY_COL9))
				)
				);
			} while(events.moveToNext());
		return result;
	}

//	@Override
//	public List<Evento> getEventoDiaTipoIcono(Date date, Integer tipo, Integer icono) {
//		ArrayList<Evento> result = new ArrayList<Evento>();
//		Date HoraInicio = new Date();
//		Date HoraFin = new Date();
//		GregorianCalendar dia1 = new GregorianCalendar();
//		dia1.setTime(date);
//		Date cutover = new Date(Long.MAX_VALUE);
//		dia1.setGregorianChange(cutover);
//		GregorianCalendar dia2 = new GregorianCalendar();
//		dia2.set(dia1.get(Calendar.YEAR), dia1.get(Calendar.MONTH), dia1.get(Calendar.DAY_OF_MONTH)+1, 
//				dia1.get(Calendar.HOUR_OF_DAY)+2, dia1.get(Calendar.MINUTE));
//		Cursor events = db.query(DATABASE_TABLE,
//				cols,
//				"(("+KEY_COL_INICIO + " BETWEEN "+ dia1.getTimeInMillis()+ " AND "+ dia2.getTimeInMillis()
//				+") OR ("+KEY_COL_INICIO+"<"+dia1.getTimeInMillis()+" AND "+KEY_COL_FIN+" > " +dia1.getTimeInMillis()+"))" +
//				" AND "+KEY_COL8+"="+tipo+" AND "+KEY_COL9+"="+icono
//				, null, null, null, KEY_ID);
//		if (events.moveToFirst())
//			do {
//				HoraInicio= new Date(events.getLong(events.getColumnIndex(KEY_COL_INICIO)));
//				HoraFin= new Date(events.getLong(events.getColumnIndex(KEY_COL_FIN)));
//				result.add(new Evento(
//						events.getInt(events.getColumnIndex(KEY_ID)),
//						events.getString(events.getColumnIndex(KEY_COL1)),
//						events.getString(events.getColumnIndex(KEY_COL2)),
//						events.getString(events.getColumnIndex(KEY_COL3)),
//						events.getDouble(events.getColumnIndex(KEY_COL4)),
//						events.getDouble(events.getColumnIndex(KEY_COL5)),
//						HoraInicio,
//						HoraFin,
//						events.getInt(events.getColumnIndex(KEY_COL8)),
//						events.getInt(events.getColumnIndex(KEY_COL9))
//				)
//				);
//			} while(events.moveToNext());
//		return result;
//	}

	@Override
	public ArrayList<Evento>[] getEventoDiaTipoIcono(Date date, Integer tipo, Integer icono) {
		int maxicon = Iconos.getMaxIcons();
		int maxtipo = MapaPreferences.CLAVES_TIPOS.length;
		ArrayList<Evento>[] result = new ArrayList[maxtipo];
		for (int i=0;i<maxtipo;i++){
			result[i]=new ArrayList<Evento>();
		}
		Date HoraInicio = new Date();
		Date HoraFin = new Date();
		GregorianCalendar dia1 = new GregorianCalendar();
		dia1.setTime(date);
		Date cutover = new Date(Long.MAX_VALUE);
		dia1.setGregorianChange(cutover);
		GregorianCalendar dia2 = new GregorianCalendar();
		dia2.set(dia1.get(Calendar.YEAR), dia1.get(Calendar.MONTH), dia1.get(Calendar.DAY_OF_MONTH)+1, 
				5, dia1.get(Calendar.MINUTE));
		Cursor events = db.query(DATABASE_TABLE,
				cols,
//				"(("+KEY_COL_INICIO + " BETWEEN "+ dia1.getTimeInMillis()+ " AND "+ dia2.getTimeInMillis()
//				+") OR ("+KEY_COL_INICIO+"<"+dia1.getTimeInMillis()+" AND "+KEY_COL_FIN+" > " +dia1.getTimeInMillis()+"))" 
//				+
//				" AND "+KEY_COL8+"="+tipo+" AND "+
				KEY_COL9+"="+icono
				, null, null, null, KEY_ID);
		if (events.moveToFirst())
			do {
				HoraInicio= new Date(events.getLong(events.getColumnIndex(KEY_COL_INICIO)));
				HoraFin= new Date(events.getLong(events.getColumnIndex(KEY_COL_FIN)));
				Log.e("COJONES",""+events.getInt(events.getColumnIndex(KEY_COL8)) );
				result[events.getInt(events.getColumnIndex(KEY_COL8))]
				       .add( new Evento(
						events.getInt(events.getColumnIndex(KEY_ID)),
						events.getString(events.getColumnIndex(KEY_COL1)),
						events.getString(events.getColumnIndex(KEY_COL2)),
						events.getString(events.getColumnIndex(KEY_COL3)),
						events.getDouble(events.getColumnIndex(KEY_COL4)),
						events.getDouble(events.getColumnIndex(KEY_COL5)),
						HoraInicio,
						HoraFin,
						events.getInt(events.getColumnIndex(KEY_COL8)),
						events.getInt(events.getColumnIndex(KEY_COL9))
				)
				);
			} while(events.moveToNext());
		return result;
	}

	
	@Override
	public List<Evento> getEventoHora(Date date) {
		
		return null;
	}

	@Override
	public Evento getEventoById(Integer id) {
		Evento event = new Evento();
		Date HoraInicio = new Date();
		Date HoraFin = new Date();
		Cursor result = db.query(true, DATABASE_TABLE,
				cols,
				KEY_ID + "=" + id, null, null, null,
				null, null);
		if ((result.getCount() == 0) || !result.moveToFirst()) {
			throw new SQLException("No items found for row: " + id);
		}
		if (result.moveToFirst()) {
			HoraInicio.setTime(result.getLong(result.getColumnIndex(KEY_COL_INICIO)));
			HoraFin.setTime(result.getLong(result.getColumnIndex(KEY_COL_FIN)));
			event = new Evento(
					result.getInt(result.getColumnIndex(KEY_ID)),
					result.getString(result.getColumnIndex(KEY_COL1)),
					result.getString(result.getColumnIndex(KEY_COL2)),
					result.getString(result.getColumnIndex(KEY_COL3)),
					result.getDouble(result.getColumnIndex(KEY_COL4)),
					result.getDouble(result.getColumnIndex(KEY_COL5)),
					HoraInicio,
					HoraFin,
					result.getInt(result.getColumnIndex(KEY_COL8)),
					result.getInt(result.getColumnIndex(KEY_COL9))
			);
		}
		return event;
	}
}