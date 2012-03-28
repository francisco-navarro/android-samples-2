package com.alopec.rpg.world.engine;

import java.net.InetAddress;

import com.alopec.rpg.bbdd.UsersDao;
import com.alopec.rpg.bbdd.bean.UserBean;
import com.alopec.rpg.reflection.Tasks;
import com.alopec.rpg.reflection.util.ExecuteResponse;
import com.alopec.rpg.world.engine.util.BaseAction;
import com.alopec.rpg.world.engine.util.ExecuteEngineException;
import com.alopec.rpg.world.mobiles.PlayerMobile;
import com.alopec.rpg.ws.servlet.WorldServlet;
import com.alopec.rpg.ws.servlet.beans.Conexion;


public class Login extends BaseAction{
	
	
	
	private static ExecuteResponse LOGIN_OK = new ExecuteResponse(ExecuteResponse.CODE_OK, Tasks.RETURN_LOGIN_OK);
	private static ExecuteResponse LOGIN_KO = new ExecuteResponse(ExecuteResponse.CODE_OK, Tasks.RETURN_LOGIN_ERROR);

	@Override
	public ExecuteResponse execute(String[] args, InetAddress iPAddress) throws ExecuteEngineException{
		logger.info("Ejecutando Login");
		try{
			
			String user=new String(args[1]);
			String pass=new String(args[2]);
			
			return execute(user, pass, iPAddress);
		}catch (ExecuteEngineException ex) {
			throw ex;
		}catch (Exception e) {
			throw KO_INVALID_PARAMS;
		}
	}

	private ExecuteResponse execute(String user, String pass, InetAddress iPAddress) throws ExecuteEngineException {
		logger.info("Logeando ["+user+"]");
		
		UserBean userBean=UsersDao.loginUsuario(user, pass);
		
		if(userBean!=null){
			
			Conexion conexion=new Conexion(iPAddress,user);
			PlayerMobile player=new PlayerMobile(user);
			
			WorldServlet world=WorldServlet.getInstance();			
			world.getConexiones().addConexion(conexion);
			world.getWorldMobiles().addObject(player);
			
			return LOGIN_OK;
		}
		
		return LOGIN_KO;
	}


	
	

}
