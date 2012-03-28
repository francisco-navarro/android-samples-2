package com.alopec.rpg.client.world;

import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.AnimatedSprite.IAnimationListener;

import android.graphics.Movie;

public class AnimatedMobile extends Mobile{
	
	protected AnimatedSprite sprite;
	protected int posX,posY;
	protected float X=-2.0F,Y=-2.0F;
	
	
	public AnimatedSprite getSprite(){
		return sprite;
	}

	//Metodos de animate
	public AnimatedSprite animate(final long pFrameDurationEach) {
		return sprite.animate(pFrameDurationEach);
	}

	public AnimatedSprite animate(final long pFrameDurationEach, final boolean pLoop) {
		return sprite.animate(pFrameDurationEach,pLoop);
	}

	public AnimatedSprite animate(final long pFrameDurationEach, final int pLoopCount) {
		return sprite.animate(pFrameDurationEach,pLoopCount);
	}

	public AnimatedSprite animate(final long pFrameDurationEach, final boolean pLoop, final IAnimationListener pAnimationListener) {
		return sprite.animate(pFrameDurationEach,pLoop,pAnimationListener);
	}

	public AnimatedSprite animate(final long pFrameDurationEach, final int pLoopCount, final IAnimationListener pAnimationListener) {
		return sprite.animate(pFrameDurationEach,pLoopCount,pAnimationListener);
	}

	public AnimatedSprite animate(final long[] pFrameDurations) {
		return sprite.animate(pFrameDurations);
	}

	public AnimatedSprite animate(final long[] pFrameDurations, final boolean pLoop) {
		return sprite.animate(pFrameDurations,pLoop);
	}

	public AnimatedSprite animate(final long[] pFrameDurations, final int pLoopCount) {
		return sprite.animate(pFrameDurations);
	}

	public AnimatedSprite animate(final long[] pFrameDurations, final boolean pLoop, final IAnimationListener pAnimationListener) {
		return sprite.animate(pFrameDurations,pLoop,pAnimationListener);
	}

	public AnimatedSprite animate(final long[] pFrameDurations, final int pLoopCount, final IAnimationListener pAnimationListener) {
		return sprite.animate(pFrameDurations,pLoopCount,pAnimationListener);
	}

	public AnimatedSprite animate(final long[] pFrameDurations, final int pFirstTileIndex, final int pLastTileIndex, final boolean pLoop) {
		return sprite.animate(pFrameDurations,pFirstTileIndex,pLastTileIndex,pLoop);
	}

	public AnimatedSprite animate(final long[] pFrameDurations, final int pFirstTileIndex, final int pLastTileIndex, final int pLoopCount) {
		return sprite.animate(pFrameDurations,pFirstTileIndex,pLastTileIndex,pLastTileIndex);
	}

	public AnimatedSprite animate(final long[] pFrameDurations, final int[] pFrames, final int pLoopCount) {
		return sprite.animate(pFrameDurations,pFrames,pLoopCount);
	}
	
	public void stopAnimation(){
		sprite.stopAnimation();
	}
	
	public int getPosX() {
		return (int)sprite.getX();
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return (int)sprite.getY();
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}
	
	public void mover(float pValueX,float pValueY){	
		
		boolean stop=(pValueX==0 && pValueY==0);

			if(stop){
				this.stopAnimation();
			}else{
				if(pValueY<0 && pValueY!=Y){
					moveDown();  		            
				}else if(pValueY>0 && pValueY!=Y){
					moveUp();	
				}else if(pValueX>0 && pValueX!=X){
					moveRight();						
				}else if(pValueX<0 && pValueX!=X){
					moveLeft();
				}   
			}
		
		X=pValueX;
		Y=pValueY;
	}
	
	public void moveUp(){		
	}
	
	public void moveDown(){		
	}
	
	public void moveLeft(){		
	}
	
	public void moveRight(){		
	}
	
	public void moveStop(){
		this.stopAnimation(); 		
	}
	
	public synchronized boolean enMovimiento(){
		
//		if(Math.round(sprite.getX())%ESPACIADO !=0 && Math.round(sprite.getY())%ESPACIADO!=0)
//			return true;
		return false;
	}
	
	public void moveTo(int x,int y){
		sprite.setPosition(x, y);
	}
}
