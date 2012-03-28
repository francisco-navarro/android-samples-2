package es.albandroid.feria11.util.maps;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnMultiChoiceClickListener;

import es.albandroid.feria11.MapaFeriaActivity;

public class MapaPreferences {
	
	private static final String PERFIL="perfil";
	public static final String[] CLAVES_TIPOS={"Servicios","Hoteles","Stands","Bares / Restaurantes","Carpas","Conciertos","Toros","Infantil","Exposiciones","Teatro","Torneos"};
	private static boolean[] valores=null;
	private static boolean satelite;
	
	private static final String Y="Y";
	private static final String N="N";

	private MapaFeriaActivity act;
	private static SharedPreferences settings=null;
	
	
	public MapaPreferences(MapaFeriaActivity mapActivity){
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
		satelite = settings.getBoolean("Satelite", true);
	}

	public void mostrarAlert(){
		
		AlertDialog.Builder builder = new AlertDialog.Builder(act);		
		builder.setTitle("Ver / Ocultar")
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
	
	public void editarSatelite(Boolean sat){
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean("Satelite",sat);
		editor.commit();
	}
	
	public boolean isSateliteEnabled(){
		return satelite;
	}
	
	public static void setCheckedPropiedad(int orden, boolean checked){
		if(valores.length>=orden)
			valores[orden]=checked;
	}
	
	public boolean isEnabledTipo(int tipo){
		return valores[tipo];
	}
}
