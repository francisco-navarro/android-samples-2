package com.pakonat.andengine.minimal.objetos.escenario;

import org.anddev.andengine.engine.handler.physics.PhysicsHandler;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

import com.pakonat.andengine.minimal.objetos.HelicopteroConFisica;

public abstract class EscenarioInterface extends Sprite {
	
	protected static boolean stop=false;
	protected final PhysicsHandler mPhysicsHandler;
	protected static final float VELOCIDAD_X=-30;

	public EscenarioInterface(float pX, float pY, TextureRegion pTextureRegion) {
		super(pX, pY, pTextureRegion);		
		this.mPhysicsHandler = new PhysicsHandler(this);		
		this.registerUpdateHandler(this.mPhysicsHandler);
		this.mPhysicsHandler.setVelocityX(VELOCIDAD_X);
	}


	
	public abstract int getAncho();
	public abstract int getAlto();
	
	public abstract int getPosicion();
	public abstract void setPosicion(int p);
	
	
	@Override
	public void onManagedUpdate(final float pSecondsElapsed) {
		
			super.onManagedUpdate(pSecondsElapsed);
		
		if(stop){
			
			mPhysicsHandler.setVelocityX(0);
		}
	}
}
