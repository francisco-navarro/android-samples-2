package com.pakonat.andengine.minimal.utils;

import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;

public class CargaTexturas {

	public static  Texture mTextureHelicoptero;
	public static TextureRegion textureRegionHelicoptero;
	
	
	public static final int countTrozosTexturaScn=4;
	public static  Texture[] mTextureScn=new Texture[countTrozosTexturaScn];
	public static TextureRegion[] textureRegionScn=new TextureRegion[countTrozosTexturaScn];
	private static int[][] dimensiones=new int[countTrozosTexturaScn][2]; //Guardamos alto y ancho de textura
	
	public static  Texture mTextureCloud;
	public static TiledTextureRegion textureRegionCloud;
	
	
	public static void load(BaseGameActivity baseGame){
		
		mTextureHelicoptero= new Texture(64, 64, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		textureRegionHelicoptero = TextureRegionFactory.createFromAsset(mTextureHelicoptero, baseGame, "gfx/heli.png", 0, 0);
		baseGame.getEngine().getTextureManager().loadTexture(mTextureHelicoptero);
		
		
		//Ejemplo de cargar un sprite en vez de textura normal
		mTextureCloud = new Texture(256,256,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		textureRegionCloud=TextureRegionFactory.createTiledFromAsset(mTextureCloud,baseGame,"gfx/cloud.png",0,0,
																												4,4);//Filas y columnas del sprite
		baseGame.getEngine().getTextureManager().loadTexture(mTextureCloud);
		
		//--CARGA DE TEXTURAS DE ESCENARIO
		
		mTextureScn[0]= new Texture(32, 32, TextureOptions.BILINEAR_PREMULTIPLYALPHA);		
		textureRegionScn[0]= TextureRegionFactory.createFromAsset(mTextureScn[0], baseGame, "gfx/scn/scn1.png", 0, 0);
		baseGame.getEngine().getTextureManager().loadTexture(mTextureScn[0]);
		dimensiones[0]=new int[]{48 ,30};
		
		mTextureScn[1]= new Texture(64, 64, TextureOptions.BILINEAR_PREMULTIPLYALPHA);		
		textureRegionScn[1]= TextureRegionFactory.createFromAsset(mTextureScn[1], baseGame, "gfx/scn/scn2.png", 0, 0);
		baseGame.getEngine().getTextureManager().loadTexture(mTextureScn[1]);
		dimensiones[1]=new int[]{48 ,45};
		
		mTextureScn[2]= new Texture(32, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);		
		textureRegionScn[2]= TextureRegionFactory.createFromAsset(mTextureScn[2], baseGame, "gfx/scn/scn3.png", 0, 0);
		baseGame.getEngine().getTextureManager().loadTexture(mTextureScn[2]);
		dimensiones[2]=new int[]{48 ,128};
		
		mTextureScn[3]= new Texture(32, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);		
		textureRegionScn[3]= TextureRegionFactory.createFromAsset(mTextureScn[3], baseGame, "gfx/scn/scn4.png", 0, 0);
		baseGame.getEngine().getTextureManager().loadTexture(mTextureScn[3]);
		dimensiones[3]=new int[]{48 ,220};
		
	}


	public static int getAltoScn(int i) {		
		return dimensiones[i][1];
	}
	
	public static int getAnchoScn(int i) {		
		return dimensiones[i][0];
	}
}
