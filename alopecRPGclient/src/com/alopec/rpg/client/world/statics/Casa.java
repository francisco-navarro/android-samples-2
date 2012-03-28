package com.alopec.rpg.client.world.statics;

import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.BaseSprite;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import com.alopec.rpg.client.utils.CargaTexturas;
import com.alopec.rpg.client.world.Mobile;

public class Casa extends Mobile{
	
	protected Sprite sprite;
	
	
	public Casa(int x,int y){
		sprite=new Sprite(x,y,CargaTexturas.texturaCasa1);
	}

	@Override
	public BaseSprite getSprite() {
		return sprite;
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
