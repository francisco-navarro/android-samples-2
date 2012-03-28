package es.albandroid.feria11.util.maps.autobuses;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnMultiChoiceClickListener;

import es.albandroid.feria11.MapaFeriaActivity;

public class LineasPreferences {
	
	private static final String PERFIL="perfil";
	public static final String[] CLAVES_TIPOS={"Línea F-1","Línea F-2","Línea F-3"};
	private static boolean[] valores=null;
	
	private static final String Y="Y";
	private static final String N="N";

	private MapaFeriaActivity act;
	private static SharedPreferences settings=null;
	
	
	public LineasPreferences(MapaFeriaActivity mapActivity){
		act=mapActivity;
		
		settings = act.getSharedPreferences(PERFIL, Context.MODE_PRIVATE);
		valores=new boolean[CLAVES_TIPOS.length];
		
		cargarValores();
	}
	
	public static  void cargarValores() {
		for(int i=0;i<CLAVES_TIPOS.length;i++){
			String valor = settings.getString(CLAVES_TIPOS[i], Y);
			if(valor.equals(Y))
				valores[i]=true;
			else
				valores[i]=false;
		}
	}

	public void mostrarAlert(){
		
		AlertDialog.Builder builder = new AlertDialog.Builder(act);		
		builder.setTitle("Líneas BUS")
		.setCancelable(false)
		.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				editarPropiedades();
				act.mostrarEventosDia();
			}
		})
		.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
				cargarValores();
			}
		});
		builder.setMultiChoiceItems(CLAVES_TIPOS,valores,new OnMultiChoiceClickListener() {			
			@Override
			public void onClick(DialogInterface dialog, int n, boolean checked) {				
				setCheckedPropiedad(n,checked);
			}
		});

		AlertDialog alert = builder.create();
		alert.show();
	}
	
	public static void editarPropiedades(){
		SharedPreferences.Editor editor = settings.edit();
		for(int i=0;i<CLAVES_TIPOS.length;i++){
			if(valores[i])
				editor.putString(CLAVES_TIPOS[i],Y );
			else
				editor.putString(CLAVES_TIPOS[i],N );
		}
		editor.commit();
	}
	
	public static void setCheckedPropiedad(int orden, boolean checked){
		if(valores.length>=orden)
			valores[orden]=checked;
	}
	
	public boolean isEnabledLinea(int linea){
		return valores[linea];
	}
}
