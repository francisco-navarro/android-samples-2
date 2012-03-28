package es.albandroid.feria11.util.images;

import java.util.HashMap;

import es.albandroid.feria11.R;
import android.content.Context;
import android.graphics.drawable.Drawable;

public class Iconos {
	
	public static final HashMap<Integer,Integer> listaIconos=new HashMap<Integer, Integer>();
	public static final HashMap<Integer,Integer> listaIconosBus=new HashMap<Integer, Integer>();
	
	static{
		
		
		listaIconos.put(new Integer(0), R.drawable.marker0);
		listaIconos.put(new Integer(1), R.drawable.marker1);
		listaIconos.put(new Integer(2), R.drawable.marker2);
		listaIconos.put(new Integer(3), R.drawable.marker3);
		listaIconos.put(new Integer(4), R.drawable.marker4);
		listaIconos.put(new Integer(5), R.drawable.marker5);
		listaIconos.put(new Integer(6), R.drawable.marker6);
		listaIconos.put(new Integer(7), R.drawable.marker7);
		listaIconos.put(new Integer(8), R.drawable.marker8);
		
		
		listaIconos.put(new Integer(9), R.drawable.marker);
		listaIconos.put(new Integer(10), R.drawable.hoteles32);
		
		listaIconosBus.put(new Integer(1), R.drawable.bus1);
		listaIconosBus.put(new Integer(2), R.drawable.bus2);
		listaIconosBus.put(new Integer(3), R.drawable.bus3);
		
		listaIconos.put(new Integer(11), R.drawable.marker11);//taxi	
		listaIconos.put(new Integer(12), R.drawable.marker12);//asistencia medica
		listaIconos.put(new Integer(13), R.drawable.marker13);//pincho
	
	}
	

	public static Drawable get(Context c,Integer n){
		return c.getResources().getDrawable(listaIconos.get(n));
	}
	public static Drawable get(Context c,int n){
		return c.getResources().getDrawable(listaIconos.get(new Integer(n)));
	}
	public static Drawable getBus(Context c,Integer n){
		return c.getResources().getDrawable(listaIconosBus.get(n));
	}
	public static Drawable getBus(Context c,int n){
		return c.getResources().getDrawable(listaIconosBus.get(new Integer(n)));
	}
	public static int getMaxIcons(){
		return listaIconos.size()-1; 
	}
}
