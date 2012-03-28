package com.alopec.rpg.client.world.mobiles;



import org.anddev.andengine.engine.handler.physics.PhysicsHandler;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.util.HorizontalAlign;


import com.alopec.rpg.client.utils.CargaTexturas;
import com.alopec.rpg.client.world.AnimatedMobile;

public class Player extends AnimatedMobile{
	
	//private AnimatedSprite sprite;
	private PhysicsHandler physicsHandler;
	private String nombre;
	private Text textTitulo;
	private Text textTituloSombra;

	private static Font mFont;
	private static Font mFont2;
	

	public Player(String nombre,int x,int y){
		
		int centerX=x-x%ESPACIADO;
		int centerY=y-y%ESPACIADO;
	        
		this.nombre=nombre;
		sprite= new AnimatedSprite(centerX, centerY, CargaTexturas.spritesPlayerGreen);
		sprite.animate(new long[]{200L,200L},0,1,false);
		
		physicsHandler = new PhysicsHandler(sprite);
		sprite.registerUpdateHandler(physicsHandler);
		
		mFont2=CargaTexturas.mFont_white;
		mFont=CargaTexturas.mFont_black;
		
		textTituloSombra = new Text(0, 0,  mFont2, nombre, HorizontalAlign.CENTER);
		textTituloSombra.setPosition(8.0F-textTituloSombra.getWidth()/2, 
							-5.0F); //pixeles por encima del sprite
		sprite.attachChild(textTituloSombra);
		
		textTitulo = new Text(0, 0,  mFont, nombre, HorizontalAlign.CENTER);
		textTitulo.setPosition(9.0F-textTitulo.getWidth()/2, 
				-6.0F); //pixeles por encima del sprite
		sprite.attachChild(textTitulo);
	}

	@Override
	public void mover(float pValueX,float pValueY){
		
		super.mover(pValueX, pValueY);
		this.getPhysicsHandler().setVelocity(pValueX * VELOCIDAD, pValueY * VELOCIDAD);
		
	}
	
	public AnimatedSprite getSprite(){
		return sprite;
	}
	
	public PhysicsHandler getPhysicsHandler(){
		return physicsHandler;
	}
	
	public String getNombre() {
		return nombre;
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
	
	

}
