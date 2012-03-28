package com.alopec.rpg.client.world;

import java.io.Serializable;

import org.anddev.andengine.entity.sprite.BaseSprite;

public abstract class Mobile implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	protected static final int VELOCIDAD=40;
	protected static final int ESPACIADO=20;
	
	public abstract BaseSprite getSprite();
	
	public abstract void moveUp();
	
	public abstract void moveDown();
	
	public abstract void moveLeft();
	
	public abstract void moveRight();
	
	public abstract void moveStop();

	public abstract void stopAnimation();
	
	public abstract void mover(float pValueX,float pValueY);
	
	public abstract void moveTo(int x,int y);

}
