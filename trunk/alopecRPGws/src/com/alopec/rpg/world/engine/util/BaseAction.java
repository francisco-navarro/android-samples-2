package com.alopec.rpg.world.engine.util;

import java.net.InetAddress;

import org.apache.log4j.Logger;

import com.alopec.rpg.reflection.Tasks;
import com.alopec.rpg.reflection.util.ExecuteResponse;


public abstract class BaseAction {
	
	protected static Logger logger = Logger.getLogger(BaseAction.class);
	
	public final static String SEPARATOR=Tasks.SEPARATOR;
	
	public final static ExecuteEngineException KO_INVALID_PARAMS=new ExecuteEngineException("Invalid params",ExecuteEngineException.PARAMS_INVALID);
	public final static ExecuteEngineException OTHER_KO=new ExecuteEngineException("Undefined KO",ExecuteEngineException.OTHER_KO);
	public final static ExecuteEngineException KO_SECURITY_EXCEPTION=new ExecuteEngineException("Security exception",ExecuteEngineException.SECURITY_EXCEPTION_KO);

	
	protected static final ExecuteResponse OK=new ExecuteResponse(new Integer(0), "");
	protected static final ExecuteResponse KO=new ExecuteResponse(new Integer(-1), "");
	

	//Este es el metodo que va a ejecutar el motor de reflexión
	public abstract ExecuteResponse execute(String args[], InetAddress iPAddress) throws ExecuteEngineException;
	

}
