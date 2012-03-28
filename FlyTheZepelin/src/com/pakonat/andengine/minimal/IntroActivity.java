package com.pakonat.andengine.minimal;

import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.opengl.texture.source.AssetTextureSource;
import org.anddev.andengine.opengl.texture.source.ITextureSource;
import org.anddev.andengine.ui.activity.BaseSplashActivity;

import android.app.Activity;

public class IntroActivity extends BaseSplashActivity {

	
	private static final int SPLASH_DURATION = 2;
	private static final float SPLASH_SCALE_FROM = 1f;
	
	@Override
	protected Class<? extends Activity> getFollowUpActivity() {

		return PrincipalActivity.class;
	}

	@Override
	protected ScreenOrientation getScreenOrientation() {
		return ScreenOrientation.LANDSCAPE;
	}

	@Override
	protected float getSplashDuration() {
		return SPLASH_DURATION;
	}

	@Override
	protected ITextureSource onGetSplashTextureSource() {

		return new AssetTextureSource(this, "gfx/splash.png");

	}



	protected float getSplashScaleFrom() {

		return SPLASH_SCALE_FROM;

	}

}
