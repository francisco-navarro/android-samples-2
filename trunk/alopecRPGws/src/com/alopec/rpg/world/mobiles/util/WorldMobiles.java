package com.alopec.rpg.world.mobiles.util;

import java.util.ArrayList;
import java.util.HashMap;

import com.alopec.rpg.world.mobiles.PlayerMobile;



public class WorldMobiles {
	
	private ArrayList<WorldObject> listaMobiles;
	private HashMap<String,PlayerMobile> listaMobilesPlayers;
	
	public WorldMobiles(){
		listaMobiles= new ArrayList<WorldObject>();
		listaMobilesPlayers= new HashMap<String, PlayerMobile>();
	}
	
	public void addObject(WorldObject item){
		listaMobiles.add(item);
		if(PlayerMobile.class.isInstance(item)){
			PlayerMobile player=(PlayerMobile)item;
			listaMobilesPlayers.put(player.getLogin(), player);
		}
	}

	public ArrayList<WorldObject> getListaMobiles() {
		return listaMobiles;
	}

	public void setListaMobiles(ArrayList<WorldObject> listaMobiles) {
		this.listaMobiles = listaMobiles;
	}

	public HashMap<String, PlayerMobile> getListaMobilesPlayers() {
		return listaMobilesPlayers;
	}

	public void setListaMobilesPlayers(HashMap<String, PlayerMobile> listaMobilesPlayers) {
		this.listaMobilesPlayers = listaMobilesPlayers;
	}

}
