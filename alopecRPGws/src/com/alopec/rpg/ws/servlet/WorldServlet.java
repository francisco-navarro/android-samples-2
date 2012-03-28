package com.alopec.rpg.ws.servlet;

import java.net.InetAddress;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.alopec.rpg.tcp.Servidor;
import com.alopec.rpg.udp.*;
import com.alopec.rpg.world.mobiles.util.WorldMobiles;
import com.alopec.rpg.ws.servlet.beans.Conexion;

import org.apache.log4j.Logger;

import com.alopec.rpg.Connection;

public class WorldServlet extends HttpServlet{
	
	static Logger logger = Logger.getLogger(WorldServlet.class);
	
	public static WorldServlet instance;
	
	private Conexiones conexiones;
	private WorldMobiles worldMobiles;
	
	
	private UDPServer server;
	private Servidor servidor;

	@Override
	public void init() throws ServletException {
		logger.info("Arrancando servidor...");
		super.init();
		instance=this;
		
		conexiones= new Conexiones();
		worldMobiles = new WorldMobiles();
		
		logger.info("Arrancando servidor UDP en puerto "+UDPServer.PUERTO_ESCUCHA);
		
		server=new UDPServer();
		server.start();
		//Hacer asincrono
		//servidor=new Servidor();
		
		logger.info("Servidor arrancado.");
	}


	public WorldMobiles getWorldMobiles() {
		return worldMobiles;
	}


	public void setWorldMobiles(WorldMobiles worldMobiles) {
		this.worldMobiles = worldMobiles;
	}


	public static WorldServlet getInstance() {
		return instance;
	}
	
	public int getNumberConnections(){
		return conexiones.size();
	}
	
	public Conexiones getConexiones(){
		return conexiones;
	}
	
	public ArrayList<Conexion> getListaConexiones() {
		return conexiones.getListaConexiones();
	}
	
	public Conexion getConexionPorLoginIp(String login,InetAddress iPAddres){
		return conexiones.getConexionPorLoginIp(login,iPAddres);
	}

}
