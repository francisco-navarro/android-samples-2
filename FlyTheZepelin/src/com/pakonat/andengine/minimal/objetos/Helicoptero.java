package com.pakonat.andengine.minimal.objetos;

import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

import com.pakonat.andengine.minimal.PrincipalActivity;
import com.pakonat.andengine.minimal.utils.CargaTexturas;

public class Helicoptero extends Sprite {
	
	
	private static final int CANTIDAD_SUBIDA=5;
	
	private static boolean isPressed=false;
	
	
	public Helicoptero(float pX, float pY) {
		super(pX, pY, getTextura());
		
	}

	static TextureRegion getTextura(){
		return CargaTexturas.textureRegionHelicoptero;
	}
	

	public void mover() {
		
		if(mX>PrincipalActivity.CAMERA_WIDTH){
			this.setPosition(0,mY);
		}else{ 
			if(isPressed())
				setPosition(mX+2, mY-CANTIDAD_SUBIDA);
			else
				setPosition(mX+2, mY+2);
		}
	}
	
	
	
	public static boolean isPressed() {
		return isPressed;
	}

	public static void setPressed(boolean b) {
		isPressed=b;
	}
}
