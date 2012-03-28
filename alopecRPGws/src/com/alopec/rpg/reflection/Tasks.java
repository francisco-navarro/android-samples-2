package com.alopec.rpg.reflection;



/**
 * @author Pakonat
 *
 */
public class Tasks {
	
	private static final String package_engine="com.alopec.rpg.world.engine";
	public static final String SEPARATOR="##";
	
	private static final String TASK_LOGIN=package_engine+".Login";
	public static String doLogin(String user,String pass){
		return TASK_LOGIN+SEPARATOR+user+SEPARATOR+pass+SEPARATOR;
	}
	public static String RETURN_LOGIN_OK="LOGIN[1]";
	public static String RETURN_LOGIN_ERROR="LOGIN[2]";
	
	private static final String TASK_LOGOUT=package_engine+".LogOut";
	public static String doLogout(String user,String uid){
		return TASK_LOGOUT+SEPARATOR+user+SEPARATOR+uid+SEPARATOR;
	}	
	
	private static final String TASK_GET_STATUS_AT=package_engine+".GetStatusAt";
	public static String getStatusAt(Integer x,Integer y){
		return TASK_GET_STATUS_AT+SEPARATOR+x+SEPARATOR+y+SEPARATOR;
	}
	
	private static final String TASK_MOVE_TO=package_engine+".MoveTo";
	public static String MoveTo(String login,Integer x,Integer y){
		return TASK_GET_STATUS_AT+SEPARATOR+login+SEPARATOR+x+SEPARATOR+y+SEPARATOR;
	}
	
}
