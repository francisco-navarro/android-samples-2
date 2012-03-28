package com.alopec.rpg.client.world.statics;

import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.BaseSprite;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import com.alopec.rpg.client.world.Mobile;

public class Grass extends Mobile{
	
	protected Sprite sprite;
	
	public static BitmapTextureAtlas mBitmapTextureAtlas64;	
	public static TextureRegion textura;
	
	public static void loadTexture( BaseGameActivity baseGame){
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		mBitmapTextureAtlas64 = new BitmapTextureAtlas(64, 64, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		textura=BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBitmapTextureAtlas64, baseGame, "backgroundgrass.png", 0, 0);
	}
	
	public Grass(int x,int y){
		
	}

	@Override
	public BaseSprite getSprite() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void moveDown() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveLeft() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveRight() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveStop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveUp() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mover(float pValueX, float pValueY) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stopAnimation() {
		// TODO Auto-generated method stub
		
	}
	public void moveTo(int x,int y){
		sprite.setPosition(x, y);
	}
}
