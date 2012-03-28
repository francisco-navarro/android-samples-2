package com.alopec.rpg.reflection;

import java.net.InetAddress;

import com.alopec.rpg.reflection.util.ExecuteResponse;
import com.alopec.rpg.reflection.util.ServerReflectionException;
import com.alopec.rpg.world.engine.util.BaseAction;

public class ReflectionFactory {
	
	private static final ExecuteResponse OK=new ExecuteResponse(new Integer(0),"");
	private static final ExecuteResponse KO=new ExecuteResponse(new Integer(-1),"Undefined Error");
	private static final ExecuteResponse FORMAT_KO=new ExecuteResponse(new Integer(-5),"Incorrect format in petition.");
	
	public ReflectionFactory(){
		
	}
	
	@SuppressWarnings("rawtypes")
	public ExecuteResponse executeAction(String datosRecibidos, InetAddress iPAddress) throws ServerReflectionException{
		
		String[] args=datosRecibidos.split(BaseAction.SEPARATOR);
		if(args.length<=1)
			throw new ServerReflectionException(FORMAT_KO);
		
		try{			
			Class clase=Class.forName(args[0]);
			BaseAction action=(BaseAction) clase.newInstance();
			return action.execute(args,iPAddress);
		}catch (Exception e) {
			e.printStackTrace();
			throw new ServerReflectionException(KO);
		}
		
		
	}

}
