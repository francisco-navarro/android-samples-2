package com.pakonat.andengine.minimal.objetos;

import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.pakonat.andengine.minimal.utils.CargaTexturas;

public class Nube extends  AnimatedSprite  { 

	//Esto vale como ejemplo de sprite animado, pero nada mas porque es cutre
	
	
	
	public Nube(float pX, float pY) {
		super(pX, pY, getTextura());
		this.animate(100);
	}

	static TiledTextureRegion getTextura(){
		return CargaTexturas.textureRegionCloud;
	}
	
	

}
