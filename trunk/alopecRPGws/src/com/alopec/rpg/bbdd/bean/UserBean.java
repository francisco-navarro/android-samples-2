package com.alopec.rpg.bbdd.bean;

public class UserBean {
	
	private Integer id;
	private String login;
	private String pass;
	private java.util.Date last_login;
	private Integer x;
	private Integer y;
	
	public UserBean(){
		
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public java.util.Date getLast_login() {
		return last_login;
	}
	public void setLast_login(java.util.Date last_login) {
		this.last_login = last_login;
	}
	public Integer getX() {
		return x;
	}
	public void setX(Integer x) {
		this.x = x;
	}
	public Integer getY() {
		return y;
	}
	public void setY(Integer y) {
		this.y = y;
	}
	
	

}
