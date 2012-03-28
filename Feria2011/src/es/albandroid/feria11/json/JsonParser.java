package es.albandroid.feria11.json;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.*;

import es.albandroid.feria11.beans.Noticia;

public class JsonParser {
	
	private static String pagina="pagina";
	private static String total_noticias="total_noticias";
	private static String numero_noticias="numero_noticias";
	private static String noticias_por_pagina="noticias_por_pagina";
	
	
	static String url ="http://www.lacerca.com/categories/json/132/";
	
	public static ArrayList<Noticia> parseaJson(int nPagina , int nNoticiasPagina) {
		ArrayList<Noticia> result=new ArrayList<Noticia>();
		try{
			JSONObject json = new JSONObject(leerDeUrl(url+(1+nPagina)+"/"+nNoticiasPagina));
		
			JSONArray arrayNoticias=json.getJSONArray("noticias");
			
			for(int i=0;i<arrayNoticias.length();i++){
				JSONObject item=(JSONObject) arrayNoticias.get(i);
				Noticia noticia=new Noticia(			
						item.getString("titulo"      ),
						item.getString("resumen"     ),
						item.getString("subtitulo"   ),
						item.getString("imagen"      ),
						item.getString("cuerpo"      ),
						item.getString("video"       ),
						item.getString("url"         ));
				result.add(noticia);
				
			}
			
		}catch (Exception e) {
			
		}
			return result;
		
	}
	
	
	private static String leerDeUrl(String ruta ) throws Exception{
		
		String salida="";
		URL url = new URL(ruta);
		URLConnection con;
		
		con = url.openConnection();
		
		BufferedReader in = new BufferedReader(
				new InputStreamReader(con.getInputStream()));

		String linea;
		while ((linea = in.readLine()) != null) {
			salida+=linea;			
		}
		
		return salida;
	}

}
