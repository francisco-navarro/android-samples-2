package com.alopec.rpg.ws.servlet.beans;

import java.io.Serializable;
import java.net.InetAddress;

public class Conexion implements Serializable{

	public Conexion(InetAddress dir_ip, String login) {
		super();
		this.dir_ip = dir_ip;
		this.login = login;
	}

	private InetAddress dir_ip;
	private String login;
	
	public InetAddress getDir_ip() {
		return dir_ip;
	}

	public void setDir_ip(InetAddress dir_ip) {
		this.dir_ip = dir_ip;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public Conexion(){
		
	}
	
	public String toString(){
		return "["+dir_ip+"] "+login;
	}

	public void createPlayer() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean equals(Object obj) {
		if(this.getClass().equals(obj.getClass())){
			if(((Conexion)obj).getDir_ip().getHostAddress().equals(this.getDir_ip().getHostAddress())){
				return true;
			}				
		}
		return false;
	}
	
}
