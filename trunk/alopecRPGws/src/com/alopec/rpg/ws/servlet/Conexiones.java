package com.alopec.rpg.ws.servlet;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;

import com.alopec.rpg.ws.servlet.beans.Conexion;

public class Conexiones {

	private ArrayList<Conexion> listaConexiones;
	private HashMap<String,Conexion> listaPorLogin;
	
	public Conexiones(){
		listaConexiones=new ArrayList<Conexion>();
		listaPorLogin=new HashMap<String, Conexion>();
	}
	
	public synchronized void addConexion(Conexion conexion){
		conexion.createPlayer();
		if(listaConexiones.contains(conexion) || listaPorLogin.containsKey(conexion.getLogin())){
			//logger.warn("Ya estaba");
		}else{
			listaConexiones.add(conexion);
		}
		listaPorLogin.put(conexion.getLogin(), conexion);
	}
	
	
	public ArrayList<Conexion> getListaConexiones() {
		return listaConexiones;
	}

	public int size(){
		return listaConexiones.size();
	}
	
	public Conexion getConexionPorLogin(String login){
		return listaPorLogin.get(login);
	}
	
	public Conexion getConexionPorLoginIp(String login,InetAddress iPAddres){
		Conexion conexion= listaPorLogin.get(login);
		if(conexion.getDir_ip().getHostAddress().equals(iPAddres.getHostAddress()))
			return conexion;
		return null;
	}
}
