package com.alopec.rpg.client.utils;

import org.anddev.andengine.entity.scene.background.RepeatingSpriteBackground;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.font.FontFactory;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.atlas.bitmap.source.AssetBitmapTextureAtlasSource;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import android.graphics.Color;
import android.graphics.Typeface;

import com.alopec.rpg.client.world.statics.Fountain;
import com.alopec.rpg.client.world.statics.Grass;
import com.alopec.rpg.client.world.statics.Tree;

public class CargaTexturas {

	public static final float CAMERA_WIDTH = 720 ;
	public static final float CAMERA_HEIGHT = 480;
	
	public static final int FONT_SIZE = 12;

	public static RepeatingSpriteBackground texturaFondo;
	public static TextureRegion mBackgroundTextureRegion;
	
	public static BitmapTextureAtlas mBitmapTextureAtlas32;	
	
	public static BitmapTextureAtlas mBitmapTextureAtlasPlayer;	
	public static TiledTextureRegion spritesPlayer;
	public static BitmapTextureAtlas mBitmapTextureAtlasPlayerGreen;	
	public static TiledTextureRegion spritesPlayerGreen;
	
	
	public static BitmapTextureAtlas mBitmapTextureAtlas512_256;	
	public static TextureRegion texturaCasa1;
	
	
	//Texturas control
	public static BitmapTextureAtlas mOnScreenControlTexture;
	public static TextureRegion mOnScreenControlBaseTextureRegion;
	public static TextureRegion mOnScreenControlKnobTextureRegion;
	
	//Texturas fuentes
	public static  Texture mFontTexture_white;
	public static  Font mFont_white;
	public static  Texture mFontTexture_black;
	public static  Font mFont_black;
	
	public static void load(BaseGameActivity baseGame){
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");		
		mBitmapTextureAtlas32 = new BitmapTextureAtlas(32, 32, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		 
		texturaFondo= new RepeatingSpriteBackground(
				CAMERA_WIDTH, CAMERA_HEIGHT, baseGame.getTextureManager(),
				new AssetBitmapTextureAtlasSource(baseGame, "gfx/backgroundgrass.png"),1);
		
		//Carga sprites para los mobiles
		mBitmapTextureAtlasPlayer = new BitmapTextureAtlas(256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mBitmapTextureAtlasPlayerGreen = new BitmapTextureAtlas(256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		
		spritesPlayer=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
				mBitmapTextureAtlasPlayer,baseGame,"pkayer_sprites.png",0,0,8,4);
		baseGame.getTextureManager().loadTexture(mBitmapTextureAtlasPlayer);
		
		spritesPlayerGreen=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
				mBitmapTextureAtlasPlayerGreen,baseGame,"pkayer_green_sprites.png",0,0,8,4);
		baseGame.getTextureManager().loadTexture(mBitmapTextureAtlasPlayerGreen);
		
		loadTexturesCasas(baseGame);
		
		loadTexturesStick(baseGame);
		
		Grass.loadTexture(baseGame);
		Fountain.loadTexture(baseGame);
		Tree.loadTexture(baseGame);
		
		loadFontTextures(baseGame);
	}
	
	private static void loadTexturesCasas(BaseGameActivity baseGame) {
		mBitmapTextureAtlas512_256 = new BitmapTextureAtlas(256, 64, TextureOptions.BILINEAR_PREMULTIPLYALPHA);		
		texturaCasa1=BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBitmapTextureAtlas512_256, baseGame, "casas.png", 0, 0);
		
		baseGame.getEngine().getTextureManager().loadTexture(mBitmapTextureAtlas512_256);
	}

	public static void loadTexturesStick(BaseGameActivity baseGame){
		
        mOnScreenControlTexture = new BitmapTextureAtlas(128, 64, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        mOnScreenControlBaseTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mOnScreenControlTexture, baseGame, "onscreen_control_base.png", 0, 0);
        mOnScreenControlKnobTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mOnScreenControlTexture, baseGame, "onscreen_control_knob.png", 64, 0);

        baseGame.getEngine().getTextureManager().loadTextures(mBitmapTextureAtlas32, mOnScreenControlTexture);
	}
	
	public static void loadFontTextures(BaseGameActivity baseGame){
		
		FontFactory.setAssetBasePath("font/");
		
		mFontTexture_white = new BitmapTextureAtlas(256, 256,   TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mFont_white = new Font(mFontTexture_white, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), FONT_SIZE, true, Color.WHITE);

        baseGame.getEngine().getTextureManager().loadTextures(mFontTexture_white);
        baseGame.getEngine().getFontManager().loadFont(mFont_white);
        
        mFontTexture_black = new BitmapTextureAtlas(256, 256,   TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mFont_black = new Font(mFontTexture_black, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), FONT_SIZE, true, Color.BLACK);

        baseGame.getEngine().getTextureManager().loadTextures(mFontTexture_black);
        baseGame.getEngine().getFontManager().loadFont(mFont_black);
	}


}
