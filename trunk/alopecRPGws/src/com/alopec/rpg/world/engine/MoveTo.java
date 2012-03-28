package com.alopec.rpg.world.engine;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;

import com.alopec.rpg.jaxb.MapData;
import com.alopec.rpg.jaxb.Player;
import com.alopec.rpg.reflection.util.ExecuteResponse;
import com.alopec.rpg.reflection.xml.XmlParserFactory;
import com.alopec.rpg.world.engine.util.BaseAction;
import com.alopec.rpg.world.engine.util.ExecuteEngineException;
import com.alopec.rpg.world.mobiles.PlayerMobile;
import com.alopec.rpg.ws.servlet.WorldServlet;
import com.alopec.rpg.ws.servlet.beans.Conexion;


public class MoveTo extends BaseAction{
	
	protected static ExecuteResponse POS=new ExecuteResponse(new Integer(0), "");
	
	protected static XmlParserFactory xmlFactory=new XmlParserFactory();

	@Override
	public ExecuteResponse execute(String[] args, InetAddress iPAddress) throws ExecuteEngineException {
		logger.info("Ejecutando MoveTo");
		try{
			String login=args[1];
			Integer x=new Integer(args[2]);
			Integer y=new Integer(args[3]);
			
			return execute(login,x, y,iPAddress);
		}catch (ExecuteEngineException ex) {
			logger.warn(ex);
			throw ex;
		}catch (Exception e) {
			throw KO_INVALID_PARAMS;
		}
		
	}


	public ExecuteResponse execute(String login,Integer x,Integer y, InetAddress iPAddress) throws ExecuteEngineException{
		logger.debug("Mover "+x+","+y);
		
		Conexion conexion=WorldServlet.getInstance().getConexionPorLoginIp(login, iPAddress); //Como con otras cosas, comprobar que por la ip es el login que hace
		
		if(conexion!=null){		
			HashMap<String, PlayerMobile> lista=WorldServlet.getInstance().getWorldMobiles().getListaMobilesPlayers();
			PlayerMobile player=lista.get(login);
			if(player!=null){
				player.setXY(x,y);			
			}else{
				throw KO_SECURITY_EXCEPTION;
			}
		}else{
			throw KO_SECURITY_EXCEPTION;
		}
		
		
		
		
		return OK;
	}
	

}
