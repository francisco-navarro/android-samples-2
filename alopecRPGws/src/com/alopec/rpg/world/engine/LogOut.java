package com.alopec.rpg.world.engine;

import java.net.InetAddress;

import com.alopec.rpg.reflection.util.ExecuteResponse;
import com.alopec.rpg.world.engine.util.BaseAction;
import com.alopec.rpg.world.engine.util.ExecuteEngineException;

public class LogOut extends BaseAction{

	@Override
	public ExecuteResponse execute(String[] args, InetAddress iPAddress) throws ExecuteEngineException{
		logger.info("Ejecutando LogOut");
		try{
			
			String user=new String(args[1]);
			String uid=new String(args[2]);
			
			return execute(user, uid);
		}catch (ExecuteEngineException ex) {
			throw ex;
		}catch (Exception e) {
			throw KO_INVALID_PARAMS;
		}
	}

	private ExecuteResponse execute(String user, String uid) throws ExecuteEngineException {
		
		return OK;
	}


}
