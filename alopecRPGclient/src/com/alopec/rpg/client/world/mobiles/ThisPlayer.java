package com.alopec.rpg.client.world.mobiles;



import org.anddev.andengine.engine.handler.physics.PhysicsHandler;
import org.anddev.andengine.entity.sprite.AnimatedSprite;


import com.alopec.rpg.client.utils.CargaTexturas;
import com.alopec.rpg.client.world.AnimatedMobile;

public class ThisPlayer extends AnimatedMobile{
	
	
	
	private final int width=16;
	private final int height=32;
	private String login;

	private PhysicsHandler physicsHandler;
	
	public ThisPlayer(int CAMERA_WIDTH, int CAMERA_HEIGHT,int posX,int posY,String login){
		
		this.login=login;
		this.posX=posX;
		this.posY=posY;
        int centerX = (CAMERA_WIDTH - this.width) / 2;
        int centerY = (CAMERA_HEIGHT - this.height) / 2;
        centerX=centerX-centerX%ESPACIADO;
        centerY=centerY-centerY%ESPACIADO;
		
		sprite= new AnimatedSprite(centerX, centerY, CargaTexturas.spritesPlayer);
		sprite.animate(new long[]{200L,200L},0,1,false);
		
		physicsHandler = new PhysicsHandler(sprite);
		sprite.registerUpdateHandler(physicsHandler);
		
	}
	
	
	public AnimatedSprite getSprite(){
		return sprite;
	}
	
	public PhysicsHandler getPhysicsHandler(){
		return physicsHandler;
	}
	
	@Override
	public void mover(float pValueX,float pValueY){
			
		boolean igual=(pValueX==X && pValueY==Y);
		if(!igual){
			while(enMovimiento())
				System.out.println(Math.round(sprite.getX())+","+Math.round(sprite.getY()));
			if(!enMovimiento()){
				System.out.println("despues "+Math.round(sprite.getX())+","+Math.round(sprite.getY()));
				this.getPhysicsHandler().setVelocity(pValueX * VELOCIDAD, pValueY * VELOCIDAD);
				super.mover(pValueX, pValueY);
			}
		}
	}
	
	public void moveStop(){
		this.stopAnimation(); 		
	}
	
	public void moveUp(){
		this.animate(new long[]{200L,200L,200L},0,2,true); 
	}
	
	public void moveDown(){
		this.animate(new long[]{200L,200L,200L,200L,200L,200L},9,14,true); 
	}
	
	public void moveLeft(){
		this.animate(new long[]{200L,200L,200L},21,23,true);    		
	}
	
	public void moveRight(){
		this.animate(new long[]{200L,200L,200L},3,5,true);    
	}
	
	public String getLogin(){
		return login;
	}
}
