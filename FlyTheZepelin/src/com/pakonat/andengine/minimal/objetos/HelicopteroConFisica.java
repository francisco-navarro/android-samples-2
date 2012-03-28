package com.pakonat.andengine.minimal.objetos;

import org.anddev.andengine.engine.handler.physics.PhysicsHandler;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

import com.pakonat.andengine.minimal.PrincipalActivity;
import com.pakonat.andengine.minimal.utils.CargaTexturas;

public class HelicopteroConFisica extends Sprite {
	
	

	private static final float VELOCIDAD_Y=100;
	final PhysicsHandler mPhysicsHandler;
	
	
	private static boolean isPressed=false;
	private static boolean stop=false;
	
	
	public HelicopteroConFisica(float pX, float pY) {
		super(pX, pY, getTextura());
		this.setHeight(30f);
		this.mPhysicsHandler = new PhysicsHandler(this);		
		this.registerUpdateHandler(this.mPhysicsHandler);
		mPhysicsHandler.setVelocityY(VELOCIDAD_Y);
		
		
	}

	static TextureRegion getTextura(){
		return CargaTexturas.textureRegionHelicoptero;
	}
	
	public static boolean isPressed() {
		return isPressed;
	}

	public static void setPressed(boolean b) {
		isPressed=b;
	}
	
	public static void stop(){
		stop=true;		
	}
	
	@Override
	protected void onManagedUpdate(final float pSecondsElapsed) {
		
		if(stop){
			
			mPhysicsHandler.setVelocityY(0);

		}else {
			if (!isPressed) {

				mPhysicsHandler.setVelocityY(VELOCIDAD_Y);

			}else{
				
				mPhysicsHandler.setVelocityY(0);
				mPhysicsHandler.setVelocityY(-1.5f*VELOCIDAD_Y);
			}
		}
		super.onManagedUpdate(pSecondsElapsed);
			
		
	}
	
	
	
}
