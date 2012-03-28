package com.alopec.rpg.client.controls;

import java.io.ByteArrayInputStream;

import org.anddev.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.anddev.andengine.engine.camera.hud.controls.BaseOnScreenControl.IOnScreenControlListener;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import android.os.AsyncTask;

import com.alopec.rpg.client.world.World;
import com.alopec.rpg.client.world.mobiles.ThisPlayer;
import com.alopec.rpg.jaxb.MapData;
import com.alopec.rpg.reflection.Tasks;
import com.alopec.rpg.reflection.util.ExecuteResponse;
import com.alopec.rpg.udp.UDPClient;

public class StickListener implements IOnScreenControlListener {
	
	private ThisPlayer player;
	private World world;
	private UDPClient cliente;
	
	public StickListener(ThisPlayer player,World world,UDPClient cliente){
		this.player=player;
		this.world=world;
		this.cliente=cliente;
		
	}
	
	float X=0,Y=0;
	@Override
	public void onControlChange(final BaseOnScreenControl pBaseOnScreenControl, final float pValueX, final float pValueY) {
		
		if(pValueX!=0.0 || pValueY!=0.0){
			new MoveToAsyncTask().execute(new Integer((int) player.getPosX()),new Integer((int) player.getPosY()));
		}
		player.mover(pValueX, pValueY);
		//world.mover(pValueX, pValueY);
	}  
	
	public void actualizaData(){
		world.actualizaData();
	}
	
	private class MoveToAsyncTask extends AsyncTask<Integer, Void, ExecuteResponse> {

		@Override
		protected ExecuteResponse doInBackground(Integer... arg0) {
			if(arg0.length>1){
				ExecuteResponse respuesta=cliente.run(
						Tasks.MoveTo(player.getLogin() ,arg0[0],arg0[1] ));
				return respuesta;
			}
			return null;
		}
	
	}
		
}