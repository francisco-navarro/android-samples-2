package com.alopec.rpg.client.controls;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.camera.hud.controls.AnalogOnScreenControl;
import org.anddev.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.anddev.andengine.engine.camera.hud.controls.DigitalOnScreenControl;
import org.anddev.andengine.engine.camera.hud.controls.AnalogOnScreenControl.IAnalogOnScreenControlListener;
import org.anddev.andengine.engine.camera.hud.controls.BaseOnScreenControl.IOnScreenControlListener;
import org.anddev.andengine.entity.modifier.ScaleModifier;
import org.anddev.andengine.entity.modifier.SequenceEntityModifier;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

import com.alopec.rpg.client.WorldActivity;
import com.alopec.rpg.client.utils.CargaTexturas;
import com.alopec.rpg.client.world.World;
import com.alopec.rpg.client.world.mobiles.ThisPlayer;
import com.alopec.rpg.udp.UDPClient;

public class Stick extends DigitalOnScreenControl{
	
	private static final float pX=0;
	private static final float pY=WorldActivity.CAMERA_HEIGHT;
	private static final float pTimeBetweenUpdates=0.1f;
	
	private UDPClient cliente;

	public Stick(Camera pCamera,ThisPlayer player,World world,UDPClient cliente) {
		super(pX, pY-CargaTexturas.mOnScreenControlBaseTextureRegion.getHeight(),
				pCamera,
				CargaTexturas.mOnScreenControlBaseTextureRegion, CargaTexturas.mOnScreenControlKnobTextureRegion,
				pTimeBetweenUpdates, new StickListener(player,world,cliente));
		this.cliente=cliente;
		this.getControlBase().setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		this.getControlBase().setAlpha(0.5f);
	}



}


