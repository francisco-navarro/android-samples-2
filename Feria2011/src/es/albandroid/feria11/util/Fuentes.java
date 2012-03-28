package es.albandroid.feria11.util;

import java.util.HashMap;

import android.content.Context;
import android.graphics.Typeface;

public class Fuentes {
public static final HashMap<Integer,String> listaFuentes=new HashMap<Integer, String>();
	
	static{
		
		
		listaFuentes.put(new Integer(0), "fonts/DroidSerif-Regular.ttf");
		listaFuentes.put(new Integer(1), "fonts/DroidSerif-Bold.ttf");
		listaFuentes.put(new Integer(2), "fonts/DroidSans-Bold.ttf");
		listaFuentes.put(new Integer(3), "fonts/DroidSans.ttf");

	}

	public static Typeface get(Context c,Integer n){
		return Typeface.createFromAsset(c.getAssets(), listaFuentes.get(n));
	}

}
