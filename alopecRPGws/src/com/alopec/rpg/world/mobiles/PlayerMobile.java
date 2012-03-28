package com.alopec.rpg.world.mobiles;

import java.io.Serializable;

import com.alopec.rpg.world.mobiles.util.WorldObject;

public class PlayerMobile extends WorldObject{

	private Integer x,y;
	private String login;
	public Integer getX() {
		return x;
	}

	public void setX(Integer x) {
		this.x = x;
	}

	public void setXY(Integer x,Integer y) {
		this.x = x;
		this.y = y;
	}
	
	public Integer getY() {
		return y;
	}

	public void setY(Integer y) {
		this.y = y;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	private int health;
	
	public PlayerMobile(String login){
		this.login=login;
		x=new Integer(0);
		y=new Integer(0);
	}
}
