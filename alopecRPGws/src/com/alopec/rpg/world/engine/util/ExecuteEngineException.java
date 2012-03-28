package com.alopec.rpg.world.engine.util;

public class ExecuteEngineException extends Exception{
	
	public static final Integer PARAMS_INVALID=new Integer(-2);
	public static final Integer OTHER_KO=new Integer(-999);
	public static final Integer SECURITY_EXCEPTION_KO=new Integer(-4);
	
	private String msg;
	private Integer code;

	public ExecuteEngineException(String msg,Integer code){
		this.msg=msg;
		this.code=code;
	}
	
	public String toString(){
		return msg;
	}
}
