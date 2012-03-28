package com.pakonat.andengine.minimal.objetos.escenario;

import org.anddev.andengine.opengl.texture.region.TextureRegion;

import com.pakonat.andengine.minimal.utils.CargaTexturas;

public class EscenarioSimple extends EscenarioInterface {
	
	private int alto,ancho,posicion;

	public EscenarioSimple(float pX, float pY, int i) {
		super(pX, pY, getTextura(i));
		posicion=-1;
		alto=CargaTexturas.getAltoScn(i);
		ancho=CargaTexturas.getAnchoScn(i);
	}

	public EscenarioSimple(float pX, float pY, int i,int alto,int ancho) {
		super(pX, pY, getTextura(i));
		this.alto=alto;
		this.ancho=ancho;
		posicion=-1;
	}
	
	
	private static TextureRegion getTextura(int i){
		return CargaTexturas.textureRegionScn[i];
	}



	@Override
	public int getAlto() {		
		return alto;
	}



	@Override
	public int getAncho() {		
		return ancho;
	}

	@Override
	public int getPosicion() {		
		return posicion;
	}

	@Override
	public void setPosicion(int p) {
		posicion=p;
	}

	public static void stop(){
		EscenarioSimple.stop=true;
	}
	
}
