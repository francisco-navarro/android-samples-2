package es.albandroid.feria11.bbdd;

import java.util.ArrayList;
import java.util.List;

import es.albandroid.feria11.beans.Alarma;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.*;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

public class AlarmAdapter {
	public static final String DATABASE_NAME = "memento";
	private static final String DATABASE_TABLE = "alarma";
	private static final int DATABASE_VERSION = 1;
	private SQLiteDatabase db;
	private final Context context;
	public static final String KEY_ID = "_id";

	public final static String KEY_COL1 = "alarma";

	private static final String[] cols = new String[] { KEY_ID, KEY_COL1 };

	private DBOpenHelper dbHelper;

	public AlarmAdapter(Context _context) {
		this.context = _context;
		dbHelper = new DBOpenHelper(context, DATABASE_NAME,
				null, DATABASE_VERSION);
	}

	public static final String DATABASE_CREATE = "create table if not exists " +
	DATABASE_TABLE + " (" + KEY_ID + " integer primary key, " +
	KEY_COL1 + " numeric not null " +
	");";

	private static class DBOpenHelper extends SQLiteOpenHelper {

		public DBOpenHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase _db) {
			_db.execSQL(DATABASE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion) {
			Log.w("DBAdapter", "Upgrading from version " +
					_oldVersion + " to " +
					_newVersion + ", which will destroy all old data");
			// Drop the old table.
			_db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			// Create a new one.
			onCreate(_db);
		}
	}

	public synchronized void close() {	 
		if(db != null)
			db.close();
	}

	public void dropTable() {
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
	}

	public void open() throws SQLiteException {
		try {
			db = dbHelper.getWritableDatabase();
			db.execSQL(DATABASE_CREATE);
		} catch (SQLiteException ex) {
			db=dbHelper.getReadableDatabase();
		}
	}

	public Alarma getAlarmabyId(Integer id) { //Si la alarma no existe, devuelve una alarma con valores -1 y -1
		Alarma alarm = new Alarma();
		Cursor result = db.query(true, DATABASE_TABLE,
				cols,
				KEY_ID + "=" + id, null, null, null,
				null, null);
		if ((result.getCount() == 0) || !result.moveToFirst()) {
			alarm = new Alarma(-1,-1);
		} else {
		if (result.moveToFirst()) {
			alarm = new Alarma(
					result.getInt(result.getColumnIndex(KEY_ID)),
					result.getInt(result.getColumnIndex(KEY_COL1))
			);
		}
		}
		return alarm;
	}
	
	public Alarma getAlarmabyIdAlarma(Integer id) { //Si la alarma no existe, devuelve una alarma con valores -1 y -1
		Alarma alarm = new Alarma();
		Cursor result = db.query(true, DATABASE_TABLE,
				cols,
				KEY_COL1 + "=" + id, null, null, null,
				null, null);
		if ((result.getCount() == 0) || !result.moveToFirst()) {
			alarm = new Alarma(-1,-1);
		} else {
		if (result.moveToFirst()) {
			alarm = new Alarma(
					result.getInt(result.getColumnIndex(KEY_ID)),
					result.getInt(result.getColumnIndex(KEY_COL1))
			);
		}
		}
		return alarm;
	}

	public List<Alarma> getAlarmas() {
		ArrayList<Alarma> alarms = new ArrayList<Alarma>();
		Cursor result = db.query(DATABASE_TABLE,
				cols,
				null, null, null, null, KEY_ID);
		if (result.moveToFirst())
			do {
				alarms.add(new Alarma(
						result.getInt(result.getColumnIndex(KEY_ID)),
						result.getInt(result.getColumnIndex(KEY_COL1))
				)
				);
			} while(result.moveToNext());
		return alarms;
	}

	public long insertAlarma(Integer id, Integer alarma) {
		ContentValues newValues = new ContentValues();
		newValues.put(KEY_ID, id);
		newValues.put(KEY_COL1, alarma);
		return db.insert(DATABASE_TABLE, null, newValues);
	}

	public boolean removeAlarma(long _rowIndex) {
		return db.delete(DATABASE_TABLE, KEY_ID + "=" + _rowIndex, null) > 0;
	}

	public boolean updateAlarm(Integer _rowIndex, Integer s) {
		ContentValues newValues = new ContentValues();
		newValues.put(KEY_COL1,s);
		return db.update(DATABASE_TABLE, newValues, KEY_ID + "=" + _rowIndex, null) > 0;
	}

}