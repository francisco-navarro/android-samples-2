package es.albandroid.feria11.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import es.albandroid.feria11.HorariosActivity;

public class TiposPreferences {

	private static final String PERFIL="filtrohorarios";
	public static final String[] CLAVES_TIPOS={"Todos","Conciertos","Toros","Infantil","Exposiciones","Teatro","Torneos"};

	private HorariosActivity ctx;

	private static SharedPreferences settings=null;


	public TiposPreferences(HorariosActivity ctx){

		this.ctx=ctx;

		settings = ctx.getSharedPreferences(PERFIL, Context.MODE_PRIVATE);

	}

	public void mostrarAlert(){
		int selected = settings.getInt("Tipo", 0);
		if (selected<0) selected=0;
		else selected=selected-4;
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
		builder.setTitle("Ver solo ...");
		builder.setSingleChoiceItems(CLAVES_TIPOS, selected, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int item) {
				if(item!=0)
				editarPropiedades(item+4);
				else 
					editarPropiedades(-1);
				dialog.cancel();
				ctx.mostrarEventos(ctx.getFecha(),0);
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	public static int getTipo(){
		return settings.getInt("Tipo", -1);
	}

	public static void editarPropiedades(int tipo){
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt("Tipo",tipo);
		editor.commit();
	}
}

