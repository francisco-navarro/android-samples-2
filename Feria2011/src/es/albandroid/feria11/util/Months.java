package es.albandroid.feria11.util;

import java.util.HashMap;

public class Months {
	
	public static final HashMap<Integer,String> listaMeses=new HashMap<Integer, String>();
	
	static{
		
		listaMeses.put(new Integer(0), "Enero");
		listaMeses.put(new Integer(1), "Febrero");
		listaMeses.put(new Integer(2), "Marzo");
		listaMeses.put(new Integer(3), "Abril");
		listaMeses.put(new Integer(4), "Mayo");
		listaMeses.put(new Integer(5), "Junio");
		listaMeses.put(new Integer(6), "Julio");
		listaMeses.put(new Integer(7), "Agosto");
		listaMeses.put(new Integer(8), "Septiembre");
		listaMeses.put(new Integer(9), "Octubre");
		listaMeses.put(new Integer(10), "Noviembre");
		listaMeses.put(new Integer(11), "Diciembre");

	}

	public static String get(Integer n){
		return listaMeses.get(n);
	}

	public static int getMaxIcons(){
		return 3;
	}
}
