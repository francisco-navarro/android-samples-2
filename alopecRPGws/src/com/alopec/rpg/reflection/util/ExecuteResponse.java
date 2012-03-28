package com.alopec.rpg.reflection.util;

public class ExecuteResponse {
	
	public void setData(byte[] data) {
		this.data = data;
	}

	public static Integer CODE_OK=new Integer(0);
	public static Integer CODE_KO=new Integer(1);
	
	private Integer errorCode;
	private String msg;
	private byte[] data;
	
	
	public final static String _INIT_CODE_="<code>";
	public final static String _END_CODE_="</code>";
	public final static String _INIT_MSG_="<msg>";
	public final static String _END_MSG_="</msg>";
	
	public ExecuteResponse(){
		
	}
			
	public ExecuteResponse(Integer errorCode, String msg) {
		super();
		this.errorCode = errorCode;
		this.msg = msg;
	}
	public ExecuteResponse(byte[] data2) {
		{
			String cadena[]=new String(data2).split("\\<[A-z/]*>");
			errorCode=new Integer(cadena[1]);
			msg=cadena[3];
		}
		String resultData[]=new String(data2).split("</msg>");
		if(resultData.length>=2 && resultData[1].length()>0 &&  resultData[1].charAt(0)!=0){
			//Tiene datos
			resultData[1].length();
			this.data=resultData[1].getBytes();
		}
	}

	public ExecuteResponse(String message, Integer code) {
		super();
		this.errorCode = code;
		this.msg = message;
	}

	public Integer getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

	public byte[] getBytes() {
	
		String result=_INIT_CODE_+errorCode+_END_CODE_+_INIT_MSG_+msg+_END_MSG_+new String(getData())+_END_MSG_;
		return result.getBytes();
	}

	public byte[] getData() {
		if(data==null)
			return new byte[0];
		return data;
	}
	

}
