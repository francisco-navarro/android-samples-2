package com.pakonat.andengine.minimal;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.handler.IUpdateHandler;
import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import com.pakonat.andengine.minimal.objetos.Helicoptero;
import com.pakonat.andengine.minimal.objetos.HelicopteroConFisica;
import com.pakonat.andengine.minimal.objetos.Nube;
import com.pakonat.andengine.minimal.objetos.escenario.EscenarioInterface;
import com.pakonat.andengine.minimal.objetos.escenario.EscenarioSimple;
import com.pakonat.andengine.minimal.objetos.escenario.utils.CrearEscenarios;
import com.pakonat.andengine.minimal.utils.CargaTexturas;

public class PrincipalActivity extends BaseGameActivity implements IOnSceneTouchListener  {

	public static final int CAMERA_WIDTH = 720;
	public static final int CAMERA_HEIGHT = 480;
	private static ColorBackground colorFondo=new ColorBackground(0.09804f, 0.6274f, 0.8784f);
    
    public static final float mEffectSpawnDelay=0.05f;
    
    private Camera mCamera;

    //Objetos de la animacion    
    private HelicopteroConFisica helicoptero2;
    private EscenarioInterface[] escenarioTop;
    private EscenarioInterface[] escenarioBottom;
    
   

    @Override
    public Engine onLoadEngine() {
            this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
            return new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera));
    }

    @Override
    public void onLoadResources() {

    		CargaTexturas.load(this);
    }

    @Override
    public Scene onLoadScene() {
            this.mEngine.registerUpdateHandler(new FPSLogger());

            final Scene scene = new Scene(1);
            scene.setBackground(colorFondo);
            
            helicoptero2=new HelicopteroConFisica(15, CAMERA_HEIGHT / 2-40);   
            escenarioTop=CrearEscenarios.generaTopEscenario();
            escenarioBottom=CrearEscenarios.generaBottomEscenario();
            
            scene.getLastChild().attachChild(helicoptero2);
            
            for(EscenarioInterface s:escenarioTop) 
            	scene.getLastChild().attachChild(s);
            for(EscenarioInterface s:escenarioBottom) 
            	scene.getLastChild().attachChild(s);

        
            
            scene.registerUpdateHandler(new IUpdateHandler() {
    		    @Override
    		    public void onUpdate(float arg0) {
    		    	for(EscenarioInterface esc:escenarioTop)
    		    		if(helicoptero2.collidesWith(esc)){
    		    			HelicopteroConFisica.stop();
    		    			EscenarioSimple.stop();
    		    		}
    		    	for(EscenarioInterface esc:escenarioBottom)
    		    		if(helicoptero2.collidesWith(esc)){
    		    			HelicopteroConFisica.stop();
    		    			EscenarioSimple.stop();
    		    		}
    		    }
    		    
    		    @Override
    		    public void reset() {
    		    	//Vacio
    		    }
    		  });

            
            activaListenersEventos(scene);
            
            return scene;
    }
    
    private void activaListenersEventos(Scene scene){
    	
    	scene.setOnAreaTouchTraversalFrontToBack();
        
    	
        scene.setOnSceneTouchListener(this);
        scene.setTouchAreaBindingEnabled(true);
    }

    @Override
    public void onLoadComplete() {

    }
    
    

   
    public Engine getEngine(){
    	return this.mEngine;
    }
    
   

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		

		
		if(pSceneTouchEvent.isActionDown()){			
			System.out.println("Accion DOWN");
			Helicoptero.setPressed(true);
			HelicopteroConFisica.setPressed(true);
			
		}else if(pSceneTouchEvent.isActionMove()){
			System.out.println("Accion MOVE");
			
		}else if(pSceneTouchEvent.isActionUp()){
			System.out.println("Accion MOVE");
			Helicoptero.setPressed(false);
			HelicopteroConFisica.setPressed(false);
			
		}

		return true;
	}

	
}