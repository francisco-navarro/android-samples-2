package com.pakonat.andengine.minimal.objetos.escenario.utils;

import java.util.HashMap;
import java.util.Random;

import com.pakonat.andengine.minimal.PrincipalActivity;
import com.pakonat.andengine.minimal.objetos.escenario.EscenarioInterface;
import com.pakonat.andengine.minimal.objetos.escenario.EscenarioSimple;
import com.pakonat.andengine.minimal.utils.CargaTexturas;

public class CrearEscenarios {

	
	private static Random rand;
	private static final int tam_buffer=30;
	

	
	public static EscenarioInterface[] generaTopEscenario(){
		
		
		EscenarioInterface[] escenario = new EscenarioInterface[tam_buffer];
		
		
		for(int i=0;i<tam_buffer;i++){
			if(i%5==0){
				escenario[i]=new EscenarioSimple(-100,-100,  getI()%2+2);			
			}else{
				escenario[i]=new EscenarioSimple(-100,-100,  getI()%3);	
			}
		}
		
		//Indicamos a cada objeto su posicion en el array para tener acceso rapido a los elementos
		for(int i=0;i<escenario.length;i++)
			escenario[i].setPosicion(i);
		
		//Los pongo todos bocaabajo
		for(EscenarioInterface scn:escenario)
			scn.setRotation(180f);
		
		//Ya tenemos a todos en la matriz, ahora hay que colocarlos en posicion		
		escenario[0].setPosition(200,0);//pX es el horizontal y pY el vertical		
		for(int i=1;i<tam_buffer;i++)
			escenario[i].setPosition(escenario[i-1].getX()+escenario[i-1].getAncho()+1 //La horizontal es el ancho del objeto anterior +su posicion
									,0);//En la vertical siempre es 0 al estar pegado al techo
		
		return escenario;
	}
	
	public static EscenarioInterface[] generaBottomEscenario(){
		
		
		EscenarioInterface[] escenario = new EscenarioInterface[tam_buffer];
		
		
		for(int i=0;i<tam_buffer;i++){
			if(i%5==2){
				escenario[i]=new EscenarioSimple(-100,-100,  getI()%2+2);			
			}else{
				escenario[i]=new EscenarioSimple(-100,-100,  getI()%3);	
			}
		}
		
		//Indicamos a cada objeto su posicion en el array para tener acceso rapido a los elementos
		for(int i=0;i<escenario.length;i++)
			escenario[i].setPosicion(i);
		
		
		//Ya tenemos a todos en la matriz, ahora hay que colocarlos en posicion		
		escenario[0].setPosition(200,PrincipalActivity.CAMERA_HEIGHT-escenario[0].getAlto());//pX es el horizontal y pY el vertical		
		for(int i=1;i<tam_buffer;i++)
			escenario[i].setPosition(escenario[i-1].getX()+escenario[i-1].getAncho()+1 //La horizontal es el ancho del objeto anterior +su posicion
									,PrincipalActivity.CAMERA_HEIGHT-escenario[i].getAlto());//En la vertical siempre es el maximo menos el alto para que este pegado al suelo
		
		return escenario;
	}
	
	private static int getI(){
		return Math.abs(rand.nextInt())%(CargaTexturas.countTrozosTexturaScn);
	}
	
	static{
		rand= new Random(System.currentTimeMillis());
		
		
	}
}
