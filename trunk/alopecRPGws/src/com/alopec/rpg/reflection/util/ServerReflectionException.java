package com.alopec.rpg.reflection.util;

public class ServerReflectionException extends Exception{

	public ExecuteResponse executeResponse;
	
	public ExecuteResponse getExecuteResponse() {
		return executeResponse;
	}

	public ServerReflectionException(ExecuteResponse executeResponse){
		super(executeResponse.getMsg());
		this.executeResponse=executeResponse;
		
	}
}
