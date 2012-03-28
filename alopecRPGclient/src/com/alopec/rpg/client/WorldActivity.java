package com.alopec.rpg.client;


import java.io.ByteArrayInputStream;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.camera.hud.controls.DigitalOnScreenControl;
import org.anddev.andengine.engine.handler.IUpdateHandler;
import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.ui.activity.BaseGameActivity;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;



import com.alopec.rpg.client.controls.Stick;
import com.alopec.rpg.client.utils.CargaTexturas;
import com.alopec.rpg.client.utils.XML;
import com.alopec.rpg.client.world.World;
import com.alopec.rpg.client.world.mobiles.ThisPlayer;
import com.alopec.rpg.client.world.statics.Casa;
import com.alopec.rpg.client.world.statics.Fountain;
import com.alopec.rpg.client.world.statics.Tree;
import com.alopec.rpg.jaxb.MapData;
import com.alopec.rpg.reflection.Tasks;
import com.alopec.rpg.reflection.util.ExecuteResponse;
import com.alopec.rpg.udp.UDPClient;

public class WorldActivity extends BaseGameActivity {
	
	public static final int CAMERA_WIDTH = 427 ;//427 //360
	public static final int CAMERA_HEIGHT = 240;
	public static final ColorBackground colorFondo=new ColorBackground(0.09804f, 0.6274f, 0.8784f);

	private UDPClient cliente;
	private Camera mCamera;//mMainScene.setBackground(colorFondo);
    protected Scene mMainScene;
    
    private World world;

    private Stick mDigitalOnScreenControl;
    
    public static String login;

    @Override
    public Engine onLoadEngine() {
    	cliente = new UDPClient();
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

            final Scene scene = new Scene();
            mMainScene=scene;
            mMainScene.setBackground(CargaTexturas.texturaFondo);
           // mMainScene.setBackground(colorFondo);
            
            //Atrezzo
            Casa casa= new Casa(250,10);            
            Fountain fuente=new Fountain(300, 150);
            Tree tree = new Tree(21, 20);
            scene.attachChild(casa.getSprite());
            scene.attachChild(fuente.getSprite());
            scene.attachChild(tree.getSprite());
            //Fin atrezzo
            
            world=new World(mMainScene,0,0); 
            final ThisPlayer player=new ThisPlayer(CAMERA_WIDTH,CAMERA_HEIGHT,0,0,login);
            scene.attachChild(player.getSprite());
            world.firstUpdate(player,cliente);

    		this.mDigitalOnScreenControl = new Stick(mCamera, player,world,cliente);
    		mMainScene.setChildScene(this.mDigitalOnScreenControl);
    		
    		this.mMainScene.registerUpdateHandler(new TimerHandler(1,true,new ITimerCallback() {
    			@Override
    			public void onTimePassed(TimerHandler pTimerHandler) {
    				if((int)mMainScene.getSecondsElapsedTotal()>= 1){

	    				world.actualizaData();	    
    				}
    			}
    		}));

            return scene;
    }

    @Override
    public void onLoadComplete() {

    }


	 
}
