package com.alopec.rpg.client.world;

import java.util.ArrayList;

import org.anddev.andengine.entity.scene.Scene;

import android.content.SyncResult;
import android.os.AsyncTask;

import com.alopec.rpg.client.WorldActivity;
import com.alopec.rpg.client.utils.XML;
import com.alopec.rpg.client.world.mobiles.Player;
import com.alopec.rpg.client.world.mobiles.ThisPlayer;
import com.alopec.rpg.jaxb.MapData;
import com.alopec.rpg.reflection.Tasks;
import com.alopec.rpg.reflection.util.ExecuteResponse;
import com.alopec.rpg.udp.UDPClient;

public class World {
	
	public static boolean inUpdateProcess=false;

	private ArrayList<Mobile> mobiles;
	private Scene scene;
	private UDPClient cliente;
	private ThisPlayer player;

	public World(Scene scene, int x, int y){
		mobiles=new ArrayList<Mobile>();
		this.scene=scene;
	}

	public void add(Mobile mobile){
		mobiles.add(mobile);
		scene.attachChild(mobile.getSprite());
	}

	public Mobile get(int i){
		return mobiles.get(i);
	}

	public void remove(int i){
		scene.detachChild(mobiles.get(i).getSprite()) ;
		mobiles.remove(i);
	}

	public void addPlayer(String nombre,int x,int y){
		Player player=new Player(nombre,x,y);
		this.add(player);
	}

	public void mover(float pValueX, float pValueY) {
		for(Mobile m:mobiles)
			m.mover(pValueX,pValueY);
	}

	public void moveUp(){
		for(Mobile m:mobiles)
			m.moveUp(); 
	}

	public void moveDown(){		
		for(Mobile m:mobiles)
			m.moveDown(); 
	}

	public void moveLeft(){		
		for(Mobile m:mobiles)
			m.moveLeft(); 
	}

	public void moveRight(){
		for(Mobile m:mobiles)
			m.moveRight(); 
	}

	public void moveStop(){
		for(Mobile m:mobiles)
			m.stopAnimation(); 		
	}

	public void actualizaData() {
		if(inUpdateProcess==false){		
			inUpdateProcess=true;
			new UpdateWorldAsyncTask().execute();
		}
	}
	
	private class UpdateWorldAsyncTask extends AsyncTask<Void, Void, Void> {
		
		
		@Override
		protected Void doInBackground(Void... args) {			
			try{
				ExecuteResponse respuesta=cliente.run(
						Tasks.getStatusAt(player.getPosX(), player.getPosY()));
				if(respuesta!=null && respuesta.getData()!=null){
					MapData data = XML.deserializaXml(respuesta.getData());				
					for(Mobile m:mobiles){
						if(Player.class.isInstance(m)){
							Player p=(Player) m;
							for(int i=data.getPlayers().size()-1;i>=0;i--){
								com.alopec.rpg.jaxb.Player l=data.getPlayers().get(i);
								if(p.getNombre().equals(l.getName())){
									p.moveTo(l.getPosX(),l.getPosY());
									data.getPlayers().remove(i);
									break;
								}
							}
						}
					}
					for(com.alopec.rpg.jaxb.Player l:data.getPlayers()){
						if(!l.getName().equals(WorldActivity.login)){
							Player player=new Player(l.getName(),l.getPosX(),l.getPosY());
							mobiles.add(player);
							scene.attachChild(player.getSprite());
						}
					}
				}		
			}catch (Exception e) {
				e.printStackTrace();
			}finally{
				World.inUpdateProcess=false;
			}
			return null;
		}
		
	}
	

	//Obligatorio ejecutarlo para inicializar el cliente
	public void firstUpdate(ThisPlayer player, UDPClient cliente){
		this.cliente=cliente;
		this.player=player;		
		new UpdateWorldAsyncTask().doInBackground();
	}

}
