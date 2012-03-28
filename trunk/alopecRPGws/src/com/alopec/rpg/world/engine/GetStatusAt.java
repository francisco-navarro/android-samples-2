package com.alopec.rpg.world.engine;

import java.net.InetAddress;
import java.util.HashMap;

import com.alopec.rpg.jaxb.MapData;
import com.alopec.rpg.jaxb.Player;
import com.alopec.rpg.jaxb.Players;
import com.alopec.rpg.reflection.util.ExecuteResponse;
import com.alopec.rpg.reflection.xml.XmlParserFactory;
import com.alopec.rpg.world.engine.util.BaseAction;
import com.alopec.rpg.world.engine.util.ExecuteEngineException;
import com.alopec.rpg.world.mobiles.PlayerMobile;
import com.alopec.rpg.ws.servlet.WorldServlet;


public class GetStatusAt extends BaseAction{
	
	protected static ExecuteResponse POS=new ExecuteResponse(new Integer(0), "");
	protected static XmlParserFactory xmlFactory=new XmlParserFactory();

	@Override
	public ExecuteResponse execute(String[] args, InetAddress iPAddress) throws ExecuteEngineException {
		logger.info("Ejecutando GetStatusAt");
		try{
			
			Integer x=new Integer(args[1]);
			Integer y=new Integer(args[2]);
			
			return execute(x, y);
		}catch (ExecuteEngineException ex) {
			throw ex;
		}catch (Exception e) {
			throw KO_INVALID_PARAMS;
		}
		
	}


	public ExecuteResponse execute(Integer x,Integer y) throws ExecuteEngineException{
		logger.info("Ejecutando GetStatusAt "+x+","+y);
		
		MapData mapData= xmlFactory.newData();
		
		//lógica de rellenar los datos
		HashMap<String, PlayerMobile> lista=WorldServlet.getInstance().getWorldMobiles().getListaMobilesPlayers();
		
		for(String nombre:lista.keySet()){
			PlayerMobile p =lista.get(nombre);
			Player playerBean= new Player();
			playerBean.setLife(p.getHealth());
			playerBean.setName(nombre);
			playerBean.setPosX(p.getX());
			playerBean.setPosY(p.getY());
			if(mapData.getPlayers()==null)
				mapData.setPlayers(new Players());
			mapData.getPlayers().getPlayer().add(playerBean);
		}
		
		byte[] cadena=xmlFactory.mapData2byte(mapData);
		
		POS.setData(cadena);
		
		return POS;
	}
	

}
