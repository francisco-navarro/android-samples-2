package es.albandroid.feria11.beans;

import java.io.Serializable;
import java.util.ArrayList;

public class Noticia implements Serializable{

	private static final long serialVersionUID = 1L;
	private String titulo;
	private String resumen;
	private String subtitulo;
	private String cuerpo;
	private String imagen;
	private String video;
	private String url;
	
	public Noticia (String tit, String res, String sub, String img, String cuer, String vid, String ur) {
		
		super();
		titulo=tit;
		resumen=res;
		subtitulo=sub;
		cuerpo=cuer;
		imagen=img;
		video=vid;
		url=ur;
		
	}
	
	public String getTitulo(){
		return titulo;
	}
	
	public String getResumen(){
		return resumen; 
	}
	public String getSubtitulo(){
		return subtitulo;
	}
	
	public String getCuerpo(){
		return cuerpo;
	}
	
	public String getImagen(){
		return imagen;
	}
	
	public String getVideo(){
		return video;
	}
	
	public String getURL(){
		return url;
	}
	
	//METODO QUE DEVUELVE TODAS LAS NOTICIAS DE UNA PÁGINA 
	//EJEMPLO DE JSON Y ESTRUCTURA:
	//	{
	//		pagina: 1,
	//		total_noticias: 20,
	//		numero_noticias: 10,
	//		noticias_por_pagina: 15,
	//		noticias: {
	//			0: {
	//				titulo: "El titulo",
	//				resumen: "Un texto corto, aquí me dices tu más o
	//	menos cuantas palabras, para mostrar en formato lista",
	//				subtitulo: "El subtitulo, que puede que en algún
	//	caso no haya",
	//				cuerpo: "El cuerpo de la noticia",
	//				imagen: "URL de la imagen, que puede tener o no, en
	//	cualquier caso sería una imagen adaptada para móviles",
	//				video: "URL del video en Youtube, que puede tener o
	//	no",
	//				url: "URL de la noticia"
	//			},
	//			1: {
	//				titulo: "El titulo",
	//				resumen: "Un texto corto, aquí me dices tu más o
	//	menos cuantas palabras, para mostrar en formato lista",
	//				subtitulo: "El subtitulo, que puede que en algún
	//	caso no haya",
	//				cuerpo: "El cuerpo de la noticia",
	//				imagen: "URL de la imagen, que puede tener o no, en
	//	cualquier caso sería una imagen adaptada para móviles",
	//				video: "URL del video en Youtube, que puede tener o
	//	no",
	//				url: "URL de la noticia"
	//			},
	//			...
	//			...
	//			...
	//			9: {
	//				titulo: "El titulo",
	//				resumen: "Un texto corto, aquí me dices tu más o
	//	menos cuantas palabras, para mostrar en formato lista",
	//				subtitulo: "El subtitulo, que puede que en algún
	//	caso no haya",
	//				cuerpo: "El cuerpo de la noticia",
	//				imagen: "URL de la imagen, que puede tener o no, en
	//	cualquier caso sería una imagen adaptada para móviles",
	//				video: "URL del video en Youtube, que puede tener o
	//	no",
	//				url: "URL de la noticia"
	//			}
	//		}
	//	}

	public static ArrayList<Noticia> getNoticias(Integer pag) {
		ArrayList<Noticia> result = new ArrayList<Noticia>();
		return result;
	}
}
